package zoo.daroo.h2.mem.file.lock;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

public class SampleReader {

	public static void main(String[] args) throws Exception {
		
		try (FileChannel channel = FileChannel.open(SampleWriter.file, StandardOpenOption.READ);
				FileLock lock = channel.tryLock(0L, Long.MAX_VALUE, true)) {
			if (lock != null && lock.isValid()) {
				System.out.println("got lock: " + lock);
			}
		}
		
		System.out.println("writable: " + Files.isWritable(SampleWriter.file));
		
		long start = System.nanoTime();
		//Almost ok, but writer will get: IOException The process cannot access the file because another process has locked a portion of the file
		Files.move(SampleWriter.file, SampleWriter.file, StandardCopyOption.ATOMIC_MOVE);
		System.out.println("Move took: " + TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS));
	}

}
