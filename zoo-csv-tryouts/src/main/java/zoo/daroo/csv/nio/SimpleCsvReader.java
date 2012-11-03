package zoo.daroo.csv.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SimpleCsvReader<T> {

	private String encoding = System.getProperty("file.encoding");
	private char columnDelimiter = ';';
	private FieldSetMapper<T> fieldSetMapper = new NullFieldSetMapper();

	private char[] rowDelimiter = (System.getProperty("line.separator") != null)
			? System.getProperty("line.separator").toCharArray() : "\r\n".toCharArray();

	private int columns = -1;
	private int skipped = 0;

	private static int KB = 1024;
	private static int MB = 1024 * KB;
	private static int MaxBufferSize = 50 * MB;

	private static int DefaultTokenSize = 255;

	public void read(Path file, FieldResultHandler<T> resultHandler) throws IOException {
		skipped = 0;
		final Path path = file.toRealPath(LinkOption.NOFOLLOW_LINKS);
		final BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
		final int directBufferSize = calculateBufferSize(fileAttributes);
		final Charset charset = Charset.forName(encoding);
		final ByteBuffer buffer = ByteBuffer.allocateDirect(directBufferSize);
		final List<String> tokens = new ArrayList<>();

		CharBuffer tokenBuffer = CharBuffer.allocate(DefaultTokenSize);

		char c;
		final CharsetDecoder charsetDecoder = charset.newDecoder()
				.onMalformedInput(CodingErrorAction.REPLACE)
				.onUnmappableCharacter(CodingErrorAction.REPLACE);
		final CharactersInterpreter interpreter = new DefaultCharactersInterpreter();
		CharacterType type;
		try (SeekableByteChannel byteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ))) {
			while (byteChannel.read(buffer) > 0) {
				buffer.flip();
				final CharBuffer charBuffer = charsetDecoder.decode(buffer);

				while (charBuffer.hasRemaining()) {
					c = charBuffer.get();

					type = interpreter.examine(tokenBuffer, c);
					if (type.equals(CharacterType.NEW_ROW)) {
						tokenBuffer.flip();
						tokens.add(tokenBuffer.toString());
						tokenBuffer.clear();
						if (handleTokens(tokens, resultHandler))
							break;

						tokens.clear();
					} else if (type.equals(CharacterType.NEW_COLUMN)) {
						tokenBuffer.flip();
						tokens.add(tokenBuffer.toString());
						tokenBuffer.clear();

					} else {
						if (!tokenBuffer.hasRemaining()) {
							final CharBuffer newTokenBuffer = CharBuffer.allocate(tokenBuffer.capacity()
									+ DefaultTokenSize);
							tokenBuffer.flip();
							tokenBuffer = newTokenBuffer.put(tokenBuffer);
						}
						tokenBuffer.put(c);
					}
				}
				// buffer.compact();
				buffer.clear();
			}
			if (tokens.size() > 0) {
				handleTokens(tokens, resultHandler);
				tokens.clear();
			}
		}
	}

	private boolean handleTokens(List<String> tokens, FieldResultHandler<T> resultHandler) {
		if (columns == -1 || tokens.size() == columns) {
			try {
				final T result = fieldSetMapper.mapFieldSet(new FieldSet(tokens));
				return !resultHandler.onResult(result);
			} catch (Exception e) {
				System.err.println("Failed to process row: " + tokens.toString() + ". Error: "
						+ e.getMessage());
				skipped++;
			}
		} else {
			System.err.println("Failed to process row: " + tokens.toString()
					+ ". Invalid columns count [is=" + tokens.size() + ", should be=" + columns + "].");
			skipped++;
		}
		return false;
	}

	private int calculateBufferSize(BasicFileAttributes fileAttributes) {
		final long fileSize = fileAttributes.size();

		if (fileSize < KB)
			return KB;

		if (fileSize < MaxBufferSize)
			return (int) fileSize;

		return MB;

	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
		this.fieldSetMapper = fieldSetMapper;
	}

	public int getSkipped() {
		return skipped;
	}

	public void setColumnDelimiter(char columnDelimiter) {
		this.columnDelimiter = columnDelimiter;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setRowDelimiter(String rowDelimiter) {
		this.rowDelimiter = rowDelimiter.toCharArray();
	}

	private enum CharacterType {
		NEW_COLUMN, NEW_ROW, NORMAL;
	}

	private static interface CharactersInterpreter {
		CharacterType examine(CharBuffer tokenBuffer, char c);
	}

	private class DefaultCharactersInterpreter implements CharactersInterpreter {
		private int index = 0;

		@Override
		public CharacterType examine(CharBuffer tokenBuffer, char c) {
			if (c == columnDelimiter) {
				index = 0;
				return CharacterType.NEW_COLUMN;
			}

			if (c == rowDelimiter[index]) {
				if (index < rowDelimiter.length - 1) {
					index++;
					return CharacterType.NORMAL;
				} else {
					tokenBuffer.position(tokenBuffer.position() - index);
					index = 0;
					return CharacterType.NEW_ROW;
				}
			}

			index = 0;
			return CharacterType.NORMAL;
		}
	}

	private class NullFieldSetMapper implements FieldSetMapper<T> {
		@Override
		public T mapFieldSet(FieldSet fieldSet) {
			return null;
		}
	}
}
