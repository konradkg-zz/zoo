package zoo.daroo.h2.mem.file.lock;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

public class SampleReader {

	public static void main(String[] args) throws Exception {

		try (FileChannel channel = FileChannel.open(SampleWriter.file, StandardOpenOption.READ);
				FileLock lock = channel.tryLock(0L, Long.MAX_VALUE, true)) {
			if (lock != null) {
				System.out.println("got lock: " + lock);
			}
		}
	}

}
