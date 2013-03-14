package zoo.htmunit.tryouts;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCEIDGCaptcha {
    
    private final static String CEIDG_URL = "https://prod.ceidg.gov.pl/CEIDG/ceidg.public.ui/";
    
    private final static String IM_EXEC = "d:/Temp/captcha/ImageMagick-6.8.3-9/convert.exe";
    private final static String IM_OPTS = "-background white -contrast-stretch 500 -resize 75% -resize 300%  -fuzz 10% -fill white -opaque grey " +
    		"-opaque grey49 -opaque grey55 -resize 50% -resize 300% -colors 6 -resize 30%";
    
    
    public static void main(String[] args) throws Exception {
	List<File> files = downoladFiles();
	doImageMagic(files);
    }
    
    private static List<File> downoladFiles() throws Exception{
	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

	// privoxy
	webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8118));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.setAjaxController(new NicelyResynchronizingAjaxController());

	final HtmlPage page = webClient
	        .getPage(CEIDG_URL + "Search.aspx");

	// System.out.println(page.asXml());


	HtmlImage image = page.getHtmlElementById("MainContent_ctrlCaptcha");
	String sourceUrl = image.getSrcAttribute();
	URL sourceUrlFull = page.getFullyQualifiedUrl(sourceUrl);
	List<File> result = new ArrayList<>();
	for(int i = 0; i < 10; i++) {
	    WebResponse resp = webClient.getWebConnection().getResponse(new WebRequest(sourceUrlFull, HttpMethod.GET));
	    File f = new File("d:/Temp/captcha/CEIDG/" + sourceUrl.substring(16, 52) + "-" + i +".jpg");
	    IOUtils.copy(resp.getContentAsStream(), new FileOutputStream(f));
	    result.add(f);
	}
	
	webClient.closeAllWindows();
	
	return result;
    }
    
    private static List<File> doImageMagic(List<File> src) throws Exception{
	
	List<File> result = new ArrayList<>();
	for(File in : src) {
	    File out = new File(in.getAbsolutePath().replace(".jpg", "") + "_dest.jpg");
	    Process imProc = Runtime.getRuntime().exec(IM_EXEC + " " + in.getAbsolutePath() + " " + IM_OPTS + " " + out.getAbsolutePath());
	    imProc.waitFor();
	    result.add(out);
	}
	
	return result;
    }
 
}
