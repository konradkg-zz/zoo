package zoo.daroo.h2.mem.tryouts.csv;

import java.io.Serializable;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.FileSystemResource;

public class SpringBatchTry {

	public static void main(String[] args) throws Exception {
		FlatFileItemReader<Record> itemReader = new FlatFileItemReader<Record>();
		itemReader.setResource(new FileSystemResource("p:/Temp/h2_data/dump_lite.csv"));
		// DelimitedLineTokenizer defaults to comma as its delimiter
		DefaultLineMapper<Record> lineMapper = new DefaultLineMapper<Record>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
		lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		itemReader.open(new ExecutionContext());
		Record player = itemReader.read();
	}
	
	public static class Record implements Serializable {
		
	}
	
	protected static class RecordFieldSetMapper implements FieldSetMapper<Record> {
	    public Record mapFieldSet(FieldSet fieldSet) {
	    	Record player = new Record();

	        //player.setID(fieldSet.readString(0));
//	        player.setLastName(fieldSet.readString(1));
//	        player.setFirstName(fieldSet.readString(2)); 
//	        player.setPosition(fieldSet.readString(3));
//	        player.setBirthYear(fieldSet.readInt(4));
//	        player.setDebutYear(fieldSet.readInt(5));

	        return player;
	    }
	}   

}
