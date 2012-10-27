package zoo.daroo.h2.mem.tryouts.csv;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import zoo.daroo.h2.mem.bo.PexOnlineBO;

public class SpringBatchTry {

	public static void main(String[] args) throws Exception {
		long start = System.nanoTime();
		
		FlatFileItemReader<PexOnlineBO> itemReader = new FlatFileItemReader<PexOnlineBO>();
		itemReader.setEncoding("UTF-8");
		itemReader.setSkippedLinesCallback(new LineCallbackHandler() {
			@Override
			public void handleLine(String line) {
				line.toString();
			}
		});
		
		itemReader.setResource(new FileSystemResource("p:/Temp/h2_data/dump_lite2.csv"));
		// DelimitedLineTokenizer defaults to comma as its delimiter
		DefaultLineMapper<PexOnlineBO> lineMapper = new DefaultLineMapper<PexOnlineBO>();
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(';');
		//tokenizer.setQuoteCharacter('^');
		DefaultFieldSetFactory fieldSetFactory = new DefaultFieldSetFactory();
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		//numberFormat.setGroupingUsed(false);
		fieldSetFactory.setNumberFormat(numberFormat);
		fieldSetFactory.setDateFormat(SimpleDateFormat.getDateInstance());
		
		tokenizer.setFieldSetFactory(fieldSetFactory);
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new PexOnlineBO.PexOnlineBOFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		itemReader.open(new ExecutionContext());
		PexOnlineBO player = null;
		final int maxSkipCount = 300;
		int skipCount = 0;
		
		
		
		try {
			for(;;) {
				try {
					player = itemReader.read();
					if(player == null)
					break;
				} catch (Exception e) { 
					if( e instanceof ParseException) {
						System.err.println("Skipped due to: " + e.getMessage());
						skipCount++;
					} 
					if(skipCount > maxSkipCount) {
						System.err.println("Skip count: " + skipCount);
						throw e;
					}
					//e.printStackTrace();
				}
			}
		} finally {
			itemReader.close();
		}
		System.out.println("Skip count: " + skipCount);
		long stop = System.nanoTime();
		System.out.println("Time: " + TimeUnit.SECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[s], " 
				+ TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[ms]." );
		
	}
	
}
