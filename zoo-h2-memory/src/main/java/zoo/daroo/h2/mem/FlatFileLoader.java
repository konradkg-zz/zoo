package zoo.daroo.h2.mem;

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
		itemReader.setResource(new FileSystemResource("p:/Temp/h2_data/dump_lite2.csv"));
		
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
		Logger.info("Time: " + TimeUnit.SECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[s], "
				+ TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[ms].");

	}

}
