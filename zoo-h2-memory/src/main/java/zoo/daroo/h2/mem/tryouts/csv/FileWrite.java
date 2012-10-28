package zoo.daroo.h2.mem.tryouts.csv;

import java.io.BufferedWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.FileHandler;

public class FileWrite {
	
	public static void main(String[] args) throws Exception {
		Path path = Paths.get("//htpc/Share/h2/dump_lite2.csv");
		
		FileChannel fh = path.getFileSystem().provider().newFileChannel(path, new HashSet<>(Arrays.asList(StandardOpenOption.WRITE)));
		fh.lock().acquiredBy();
		
		fh.close();
//		try (final BufferedWriter wr = Files.newBufferedWriter(path, Charset.forName("UTF-8"), StandardOpenOption.WRITE)) {
//			
//			wr.toString();
//		}
//		
		
	}

}
