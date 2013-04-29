package zoo.htmunit.tryouts;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class TestREGONCaptcha {

    // IM: convert captcha/captcha.jpg -threshold 15% captcha/captcha_dest.jpg
    // TESS: d:\Temp\captcha\ImageMagick-6.8.3-9\captcha\captcha_dest.jpg
    // d:\Temp\captcha\ImageMagick-6.8.3-9\captcha\captcha_dest -l reg -psm 7
    // REGON

    private final static String REGON_URL = "http://www.stat.gov.pl/regon/";

    public static void main(String[] args) throws Exception {
	testAccuracy();
    }

    public static void testAccuracy() throws Exception {
	int loop = 5;
	int found = 0;
	int replaced = 0;
	int aaaaa = 0;
	long start = System.nanoTime();
	final AtomicInteger loopFinal = new AtomicInteger(0);

	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
	webClient.getOptions().setProxyConfig(new ProxyConfig("10.48.0.180", 3128));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	
	for (int i = 1; i <= loop; i++) {
	    final AtomicInteger stepFinal = new AtomicInteger(1);
	    loopFinal.incrementAndGet();
	    
	    final HtmlPage page = webClient.getPage(REGON_URL);
	    
	    //NIP
	    HtmlRadioButtonInput nipRadio = page.getHtmlElementById("by_1nip_RB");
	    nipRadio.click();
	    //captchaImg
	    HtmlSpan captchaSpan = page.getHtmlElementById("captchaImg");
	    List<HtmlImage> spanDescendants = captchaSpan.getHtmlElementsByTagName("img");
	    if(spanDescendants.isEmpty()) {
		System.err.println("Captcha img not found");
		continue;
	    }
	    
	    HtmlImage image = spanDescendants.get(0);
	    
//	    HtmlImage image = page.getHtmlElement("MainContent_ctrlCaptcha");
	    String sourceUrl = image.getSrcAttribute();
	    URL sourceUrlFull = page.getFullyQualifiedUrl(sourceUrl);
	    
	    
	}

	
	webClient.closeAllWindows();
	
	long stop = System.nanoTime();
	
	System.out.println("found: " + found + " loop: " + loop + " replaced: " + replaced + " aaaaa:" + aaaaa);
	System.out.println("Done in: " + TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "ms");
    }
}
