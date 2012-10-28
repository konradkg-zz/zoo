package zoo.daroo.h2.mem;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class FileUtils {
	private final static Log Logger = LogFactory.getLog(FileUtils.class);

	public static Path copy(Path from, Path toDir) throws IOException {
		Logger.info("Copying file " + from + " to " + toDir);
		
		final long start = System.nanoTime();
		final Path to = toDir.resolve(from.getFileName());
		final Path resultFile = Files.copy(from, to, REPLACE_EXISTING, COPY_ATTRIBUTES);
		Logger.info("File " + from + " copied in " + TimeUnit.MILLISECONDS.convert(
				System.nanoTime() - start, TimeUnit.NANOSECONDS) + " [ms]");
		return resultFile;
	}

	public static Path tryCopy(Path from, Path toDir, int tryAttempts) throws IOException {
		IOException error = null;
		for (int i = 1; i <= tryAttempts; i++) {
			try {
				return copy(from, toDir);
			} catch (IOException e) {
				error = e;
				Logger.debug("File copy attempt: " + i, e);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}
		throw error;
	}

	public static BasicFileAttributes getFileAttributes(Path filePath) throws IOException {
		final Path path = filePath.toRealPath(LinkOption.NOFOLLOW_LINKS);
		return Files.readAttributes(path, BasicFileAttributes.class);
	}
}
