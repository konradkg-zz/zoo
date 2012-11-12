package zoo.daroo.h2.mem.file.lock;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SampleWriter {
	
	//public static Path file = Paths.get("p:/temp/sample.file");
	public static Path file = Paths.get("//htpc/Share/sample.file");
	
	public static void main(String[] args) throws Exception {
		
		FileWriter fw = new FileWriter(file.toFile(), false);
		
		for(int i = 0; i < 100000; i++) {
			fw.write("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n");
			fw.flush();
		}
		
		fw.close();
	}

}
