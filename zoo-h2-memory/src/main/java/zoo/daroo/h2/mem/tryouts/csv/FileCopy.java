package zoo.daroo.h2.mem.tryouts.csv;

import java.nio.file.Paths;

import zoo.daroo.h2.mem.FileUtils;

public class FileCopy {
	
	public static void main(String[] args) throws Exception {
		FileUtils.copy(Paths.get("p:/Temp/h2_data/dump_lite2.csv"), Paths.get("//htpc/share/h2"));
	}

}
