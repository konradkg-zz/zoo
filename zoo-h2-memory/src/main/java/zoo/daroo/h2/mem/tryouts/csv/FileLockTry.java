package zoo.daroo.h2.mem.tryouts.csv;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLockTry {
	public static void main(String[] args) throws Exception {
		Path path = Paths.get("//htpc/Share/h2/dump_lite2.csv");
		//shared read lock
		try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
				FileLock lock = channel.tryLock(0L, Long.MAX_VALUE, true)) {
			if (lock != null) {
				lock.toString();
			}
		}

		//exclusive rw lock
		try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
				FileLock lock = channel.tryLock()) {
			if (lock != null) {
				lock.toString();
			}
		}
	}
}
