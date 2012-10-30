package zoo.daroo.h2.mem.tryouts.csv;

import java.beans.XMLEncoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.bm.BeiderMorseEncoder;
import org.apache.commons.lang3.StringUtils;

public class TextNormalization {
    
    public static void main(String[] args) {
	String text = "Ącki Żory \"Kraków\"";
	System.out.println(text);
	
	text = text.replace(' ', '+');
	System.out.println(text);
	
	System.out.println(Normalizer.normalize(text, Normalizer.Form.NFKD));
	
	System.out.println("isAsciiPrintable: " + StringUtils.isAsciiPrintable(text));
	
	
	
//	try {
//	    text = URLEncoder.encode(text, "UTF-8");
//	    System.out.println(text);
//        } catch (UnsupportedEncodingException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//        }
	//StringUtils.
    }

}
