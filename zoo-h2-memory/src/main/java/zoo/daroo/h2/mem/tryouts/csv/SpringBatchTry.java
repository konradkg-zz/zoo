package zoo.daroo.h2.mem.tryouts.csv;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import zoo.daroo.h2.mem.bo.PexOnlineBO;

public class SpringBatchTry {

	public static void main(String[] args) throws Exception {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.parse("-10999753");
		
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
		tokenizer.setQuoteCharacter('^');
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
		while((player = itemReader.read()) != null) {
			player.toString();
		}
		
		itemReader.close();
		
		long stop = System.nanoTime();
		System.out.println("Time: " + TimeUnit.SECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[s], " 
				+ TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "[ms]." );
		
	}
	
}