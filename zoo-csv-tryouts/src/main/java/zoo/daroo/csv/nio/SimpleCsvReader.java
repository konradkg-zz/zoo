package zoo.daroo.csv.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SimpleCsvReader {

	private String encoding = System.getProperty("file.encoding");
	private char rowDelimiter = '\n';
	private char columnDelimiter = ';';

	private static int KB = 1024;
	private static int MB = 1024 * KB;

	private static int DefaultTokenSize = 255;

	public void load(Path file) throws IOException {
		final Path path = file.toRealPath(LinkOption.NOFOLLOW_LINKS);
		final BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
		final int directBufferSize = calculateBufferSize(fileAttributes);
		final Charset charset = Charset.forName(encoding);
		final ByteBuffer buffer = ByteBuffer.allocateDirect(directBufferSize);
		final List<String> tokens = new ArrayList<>();

		CharBuffer tokenBuffer = CharBuffer.allocate(DefaultTokenSize);

		char c;
		try (SeekableByteChannel byteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ))) {
			while (byteChannel.read(buffer) > 0) {
				buffer.flip();
				final CharBuffer charBuffer = charset.decode(buffer);

				while (charBuffer.hasRemaining()) {
					c = charBuffer.get();
					if (c == rowDelimiter) {
						tokenBuffer.flip();
						tokens.add(tokenBuffer.toString());
						tokenBuffer.clear();
						// println(tokens);
						tokens.clear();
					} else if (c == columnDelimiter) {
						tokenBuffer.flip();
						tokens.add(tokenBuffer.toString());
						tokenBuffer.clear();

					} else {
						if (!tokenBuffer.hasRemaining()) {
							final CharBuffer newTokenBuffer = CharBuffer.allocate(tokenBuffer.capacity() + 2);
							tokenBuffer.flip();
							tokenBuffer = newTokenBuffer.put(tokenBuffer);
						}
						tokenBuffer.put(c);
					}
				}
				buffer.compact();
			}
		}
	}

	private int calculateBufferSize(BasicFileAttributes fileAttributes) {
		final long fileSize = fileAttributes.size();

		if (fileSize < KB)
			return KB;

		if (fileSize < MB)
			return (int) fileSize;

		return MB;

	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
