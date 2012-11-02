package zoo.daroo.csv;

import java.io.FileReader;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

public class SuperCSV {

	public static void main(String[] args) throws Exception {
		ICsvBeanReader beanReader = null;
		try {
			CsvPreference pref = new CsvPreference.Builder('"', ',', "\r\n").build();
			beanReader = new CsvBeanReader(new FileReader("p:/Temp/h2_data/pex_exchange.dat"), pref);

		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}

	}

}
