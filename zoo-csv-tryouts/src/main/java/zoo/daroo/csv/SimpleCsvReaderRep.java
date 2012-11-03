package zoo.daroo.csv;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import zoo.daroo.csv.nio.FieldResultHandler;
import zoo.daroo.csv.nio.FieldSet;
import zoo.daroo.csv.nio.FieldSetMapper;
import zoo.daroo.csv.nio.SimpleCsvReader;

public class SimpleCsvReaderRep {
	
	public static void println(List<String> tokens) {
		System.out.println("size: " + tokens.size() + " " + tokens.toString());
	}
	
	public static void main(String[] args) throws Exception {
		SimpleCsvReader<List<String>> csvReader = new SimpleCsvReader<>();
		csvReader.setEncoding("Cp1250");
		csvReader.setRowDelimiter("\u2020");
		csvReader.setColumnDelimiter('\u2021');
		csvReader.setColumns(96);
		csvReader.setFieldSetMapper(new FieldSetMapper<List<String>>() {
			@Override
			public List<String> mapFieldSet(FieldSet fieldSet) {
				final List<String> result = new ArrayList<>();
				for(int i = 0; i < fieldSet.size(); i++) {
					result.add(fieldSet.readString(i));
				}
				return result;
			}
		});
		
		long start = System.nanoTime();
		csvReader.read(Paths.get("p:/Temp/h2_data/pex_exchange.dat"), new FieldResultHandler<List<String>>() {
			@Override
			public boolean onResult(List<String> result) {
				println(result);
				return true;
			}
		});
		System.out.println("Exec time: " + TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + " [ms].");
	}

}
