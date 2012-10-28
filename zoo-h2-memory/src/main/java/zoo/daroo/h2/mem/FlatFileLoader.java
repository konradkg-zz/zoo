package zoo.daroo.h2.mem;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
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
import org.springframework.core.io.FileSystemResource;

import zoo.daroo.h2.mem.bo.PexOnline;
import zoo.daroo.h2.mem.dao.PexOnlineDao;


/*
 * TODO: 
 * 1) rename to FlatFilemanager
 * 2) Switch alg:
 * 	a) check if new file is ready
 * 	b) wait random() number of seconds 0 - 300
 *  c) get file modified date and fileSize and store in DB
 *  	(Files.readAttributes(path, BasicFileAttributes.class); attr.lastAccessTime())
 *  d) copy file to local drive (Files.copy(Path source, Path target, CopyOption... options)) - overwrite
 *  e) load file
 * 3) if connection lost to remote file compare timestamp stored in DB and file size
 * 
 */
public class FlatFileLoader {
	
	public final static String BEAN_ID = "FlatFileLoader";
	
	private final Log Logger = LogFactory.getLog(getClass());
	
	@Inject
	@Named(PexOnlineDao.BEAN_ID)
	private PexOnlineDao pexOnlineDao;
	
	
	public void load() throws Exception{
		long start = System.nanoTime();

		FlatFileItemReader<PexOnline> itemReader = new FlatFileItemReader<PexOnline>();
		itemReader.setEncoding("UTF-8");
		
		final FileSystemResource resource = new FileSystemResource("p:/Temp/h2_data/dump_lite2.csv");
		logFileAttributes(resource);
		itemReader.setResource(resource);
		
		DefaultLineMapper<PexOnline> lineMapper = new DefaultLineMapper<PexOnline>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer(';'));
		lineMapper.setFieldSetMapper(new PexOnline.PexOnlineFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		itemReader.open(new ExecutionContext());
		PexOnline pex = null;
		final int maxSkipCount = 300;
		int skipCount = 0;
		
		long line = 0;
		
		try {
			for (;;) {
				try {
					pex = itemReader.read();
					if (pex == null)
						break;
					
					line++;
					if(line % 100000 == 0)
						Logger.info("Already loaded lines: " +line);
					
					if(pex.getPexId() == null) {
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
		Logger.info("Skip count: " + skipCount);
		long stop = System.nanoTime();
		Logger.info("Load time: " + TimeUnit.SECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[s], "
				+ TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[ms].");
	}

	private BasicFileAttributes logFileAttributes(FileSystemResource resource) {
		BasicFileAttributes attr = null;
		try {
			final Path path = Paths.get(resource.getURI()).toAbsolutePath();
			attr = Files.readAttributes(path, BasicFileAttributes.class);
			final FileTime lastModifiedTime = attr.lastModifiedTime();
			Logger.info("File path=" + path + ", last modified time=" + lastModifiedTime + " (millis=" + lastModifiedTime.toMillis() + ")" 
					+ ", size=" + attr.size());
			return attr;
		} catch (IOException e) {
			Logger.error("Cannot obtain file attributes", e);
		}
		return null;
	}
	
	private Path copy(Path from, Path toDir) throws IOException {
		final Path to = toDir.resolve(from.getFileName());
		return Files.copy(from, to, REPLACE_EXISTING, COPY_ATTRIBUTES);
	}
}
