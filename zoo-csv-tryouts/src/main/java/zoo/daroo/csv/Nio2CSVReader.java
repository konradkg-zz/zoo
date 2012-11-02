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
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class Nio2CSVReader {
	
	static char rowDelimiter = (char) 0x86;
	static char columnDelimiter = (char) 0x87;
	
	public static void main(String[] args) throws Exception{
		final Path path = Paths.get("p:/Temp/h2_data/pex_exchange.dat");
		try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path,
				EnumSet.of(StandardOpenOption.READ))) {
			
			final Charset charset = Charset.forName("ISO-8859-1");
			final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			int read = 0;
			StringBuilder sb = new StringBuilder();
			List<String> tokens = new ArrayList<>();
			while((read = seekableByteChannel.read(buffer)) > 0) {
				buffer.flip();
				final CharBuffer charBuffer = charset.decode(buffer);
				
				
				//charBuffer.flip();
				while(charBuffer.remaining() > 0) {
					char c = charBuffer.get();
					if(c == rowDelimiter) {
						println(tokens);
						tokens.clear();
					} else if (c == columnDelimiter) {
						tokens.add(sb.toString());
						sb = new StringBuilder();						
					} else {
						sb.append(c);
					}
				}
				buffer.compact();
				//charBuffer.clear();
			}
		}
	}
	
	public static void println(List<String> tokens) {
		System.out.println(tokens.toString());
	}

}
