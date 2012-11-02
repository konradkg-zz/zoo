package zoo.daroo.csv.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleCsvReader<T> {

	private String encoding = System.getProperty("file.encoding");
	private char rowDelimiter = '\n';
	private char columnDelimiter = ';';
	private FieldSetMapper<T> fieldSetMapper;

	private static int KB = 1024;
	private static int MB = 1024 * KB;

	private static int DefaultTokenSize = 255;

	public void read(Path file, FieldResultHandler<T> resultHandler) throws IOException {
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
					//TODO: fix this
					if(c == '\r')
						continue;
					if (c == rowDelimiter) {
						tokenBuffer.flip();
						tokens.add(tokenBuffer.toString());
						tokenBuffer.clear();
						final T result = fieldSetMapper.mapFieldSet(new FieldSet(tokens)); 
						if(!resultHandler.onResult(result))
							break;
						
						tokens.clear();
					} else if (c == columnDelimiter) {
						tokenBuffer.flip();
						tokens.add(tokenBuffer.toString());
						tokenBuffer.clear();

					} else {
						if (!tokenBuffer.hasRemaining()) {
							final CharBuffer newTokenBuffer = CharBuffer.allocate(tokenBuffer.capacity() + DefaultTokenSize);
							tokenBuffer.flip();
							tokenBuffer = newTokenBuffer.put(tokenBuffer);
						}
						tokenBuffer.put(c);
					}
				}
				//buffer.compact();
				buffer.clear();
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
	
	public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
		this.fieldSetMapper = fieldSetMapper;
	}

	//TEMP
	public static void println(List<String> tokens) {
		System.out.println("size: " + tokens.size() + " " + tokens.toString());
	}
	
	public static void main(String[] args) throws Exception {
		SimpleCsvReader csvReader = new SimpleCsvReader();
		csvReader.setEncoding("UTF-8");
		long start = System.nanoTime();
		csvReader.read(Paths.get("p:/Temp/h2_data/dump_lite2.csv_big"));
		System.out.println("Exec time: " + TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + " [ms].");
	}
}
