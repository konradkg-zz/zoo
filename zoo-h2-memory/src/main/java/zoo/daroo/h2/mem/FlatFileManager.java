package zoo.daroo.h2.mem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.FileSystemResource;

import zoo.daroo.h2.mem.FlatFileWatchDog.FileChangeEventListener;
import zoo.daroo.h2.mem.bo.PexOnline;
import zoo.daroo.h2.mem.dao.PexOnlineDao;

public class FlatFileManager implements FileChangeEventListener, DisposableBean {

	public final static String BEAN_ID = "FlatFileManager";

	private final Log Logger = LogFactory.getLog(getClass());

	@Inject
	@Named(PexOnlineDao.BEAN_ID)
	private PexOnlineDao pexOnlineDao;
	
	@Inject
	@Named(InternalDbManager.BEAN_ID)
	private InternalDbManager internalDbManager;

	private FlatFileWatchDog watchDog;
	private Path workDir;
	
	private String baseDir;
	private String fileToLoad;
	private int maxSkipCount;
	private char delimiter;

	public void init() throws Exception {
		final Path workDirPath = Paths.get(baseDir, "work").toAbsolutePath();
		if (Files.exists(workDirPath)) {
			this.workDir = workDirPath;
		} else {
			this.workDir = Files.createDirectory(workDirPath);
		}

		Path localFile = null;
		try {
			localFile = FileUtils.copy(Paths.get(fileToLoad), workDir);
		} catch (NoSuchFileException e) {
			Logger.warn(e);
		}
		if (localFile != null) {
			loadFile(localFile.toFile());
		}

		watchDog = new FlatFileWatchDog(Paths.get(fileToLoad), this);
	}

	public void startWatch() throws IOException {
		watchDog.start();
	}

	@Override
	public void destroy() throws Exception {
		if (watchDog != null) {
			watchDog.stop();
		}
	}

	private void loadFile(File file) throws Exception {
		final long start = System.nanoTime();

		final FlatFileItemReader<PexOnline> itemReader = new FlatFileItemReader<PexOnline>();
		itemReader.setEncoding("UTF-8");

		final FileSystemResource resource = new FileSystemResource(file);
		logFileAttributes(resource);
		itemReader.setResource(resource);

		final DefaultLineMapper<PexOnline> lineMapper = new DefaultLineMapper<PexOnline>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer(delimiter));
		lineMapper.setFieldSetMapper(new PexOnline.PexOnlineFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		itemReader.open(new ExecutionContext());
		
		int skipCount = 0;
		long line = 0;

		try {
			for (;;) {
				try {
				    	final PexOnline pex = itemReader.read();
					if (pex == null)
						break;

					line++;
					if (line % 100000 == 0)
						Logger.info("Already loaded lines: " + line);

					if (pex.getPexId() == null) {
						Logger.warn("Skip due to lack of pexId");
						skipCount++;

						continue;
					}
					pexOnlineDao.insert(pex);
				} catch (Exception e) {
					if (e instanceof ParseException) {
						Logger.error("Skipped due to: " + e.getMessage());
						skipCount++;
						continue;
					}
					if (skipCount > maxSkipCount) {
						Logger.error("Skip count: " + skipCount);
						throw e;
					}
					throw e;
				}
			}
		} finally {
			itemReader.close();
		}
		Logger.info("Total lines in file: " + line + " skipped: " + skipCount);
		long stop = System.nanoTime();
		Logger.info("Load time: " + TimeUnit.SECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[s], "
				+ TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[ms].");
	}

	private BasicFileAttributes logFileAttributes(FileSystemResource resource) {
		BasicFileAttributes attr = null;
		try {
			final Path path = Paths.get(resource.getURI()).toAbsolutePath();
			attr = FileUtils.getFileAttributes(path);
			final FileTime lastModifiedTime = attr.lastModifiedTime();
			Logger.info("Loading file. File path=" + path + ", last modified time=" + lastModifiedTime + " (millis="
					+ lastModifiedTime.toMillis() + ")"
					+ ", size=" + attr.size());
			return attr;
		} catch (IOException e) {
			Logger.error("Cannot obtain file attributes", e);
		}
		return null;
	}

	@Override
	public void onModify(Path path) {
		Logger.info("File new or modified " + path);
		Path localFile = null;
		try {
			localFile = FileUtils.tryCopy(path, workDir, 10);
		} catch (IOException e) {
			Logger.error("Cannot copy file.", e);
		}

		
		if (localFile != null) {
			try {
				internalDbManager.createPexTempTable();
				loadFile(localFile.toFile());
				internalDbManager.switchTables();
			} catch (Exception e) {
				Logger.error("Cannot referesh data from local file.", e);
				
				//TODO: drop pex_temp if exists
			}
		}

	}

	public void setBaseDir(String baseDir) {
	    this.baseDir = baseDir;
	}

	public void setFileToLoad(String fileToLoad) {
	    this.fileToLoad = fileToLoad;
	}

	public void setMaxSkipCount(int maxSkipCount) {
	    this.maxSkipCount = maxSkipCount;
	}

	public void setDelimiter(char delimiter) {
	    this.delimiter = delimiter;
	}
	
}
