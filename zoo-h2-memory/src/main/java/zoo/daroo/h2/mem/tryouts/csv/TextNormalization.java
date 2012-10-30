package zoo.daroo.h2.mem.tryouts.csv;

import java.text.Normalizer;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.ASCIIFoldingFilter;

public class TextNormalization {
    
    public static void main(String[] args) {
	String text = "Ącki Żory \"Kraków\" & Spółka;";
	System.out.println(text);
	
	text = text.replace(' ', '+');
	System.out.println(text);
	
	System.out.println(Normalizer.normalize(text, Normalizer.Form.NFKD));
	
	System.out.println("isAsciiPrintable: " + StringUtils.isAsciiPrintable(text));
	
	char[] input = text.toCharArray();
	char[] output = new char[input.length];
	ASCIIFoldingFilter.foldToASCII(input, 0, output, 0, input.length);
	
	text = String.valueOf(output);
	System.out.println(text);
	
	text = StringEscapeUtils.escapeXml(text);
	System.out.println(text);
	
    }

}
