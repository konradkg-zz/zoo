package zoo.daroo.csv;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Nio2CSVReader {
	
//	static char rowDelimiter = (char) 0x86;
//	static char columnDelimiter = (char) 0x87;
	
	static char rowDelimiter = '\u2020';
	static char columnDelimiter = '\u2021';
	
	public static void main(String[] args) throws Exception{
		
		final Path path = Paths.get("p:/Temp/h2_data/pex_exchange.dat");
		try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path,
				EnumSet.of(StandardOpenOption.READ))) {
			
			//final Charset charset = Charset.forName("ISO-8859-1");
			final Charset charset = Charset.forName("Cp1250");
			
			final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			
			final List<String> tokens = new ArrayList<>();
			CharBuffer tokenBuffer = CharBuffer.allocate(255);
			char c;
			while(seekableByteChannel.read(buffer) > 0) {
				buffer.flip();
				final CharBuffer charBuffer = charset.decode(buffer);
			
				while(charBuffer.hasRemaining()) {
					c = charBuffer.get();
					if(c == rowDelimiter) {
						tokenBuffer.flip();
						tokens.add(tokenBuffer.toString());
						tokenBuffer.clear();
						println(tokens);
						tokens.clear();
					} else if (c == columnDelimiter) {
						tokenBuffer.flip();
						tokens.add(tokenBuffer.toString());
						tokenBuffer.clear();

					} else {
						if(!tokenBuffer.hasRemaining()) {
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
	
	public static void println(List<String> tokens) {
		System.out.println("size: " + tokens.size() + " " + tokens.toString());
	}

}
