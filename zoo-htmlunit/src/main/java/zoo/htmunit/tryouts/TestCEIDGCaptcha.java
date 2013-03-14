package zoo.htmunit.tryouts;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

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

    public static void main(String[] args) throws Exception {
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
	
	for(int i = 0; i < 10; i++) {
	    WebResponse resp = webClient.getWebConnection().getResponse(new WebRequest(sourceUrlFull, HttpMethod.GET));
	    File f = new File("d:/Temp/captcha/CEIDG/" + sourceUrl.substring(16, 52) + "-" + i +".jpg");
	    IOUtils.copy(resp.getContentAsStream(), new FileOutputStream(f));
	}
	//HtmlInput captchaInput = page.getHtmlElementById("MainContent_tbCaptcha");
	
	 webClient.closeAllWindows();
    }

}
