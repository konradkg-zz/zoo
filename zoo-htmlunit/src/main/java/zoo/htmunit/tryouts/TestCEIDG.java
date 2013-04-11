package zoo.htmunit.tryouts;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCEIDG {

    public final static String CAPTCHA = "AAAAA";
    
    public final static String TEST_REGON = "350279436";
    
    public final static String TEST_ASSERT_STR = "SIEPRAWSKA";
    
    //CONST link, no captcha, no session
    //https://prod.ceidg.gov.pl/CEIDG/ceidg.public.ui/SearchDetails.aspx?Id=fdb9d537-89c3-43bd-878b-2a8cbf70d39d
    
    
    public static void main(String[] args) throws Exception {
	
	String result = null;
	int attempt = 0;
	while(result == null) {
	    result = search();
	    attempt++;
	}
	
	System.out.println(result);
	System.out.println("Attempts: " + attempt);
    }
    
    public static String search() throws Exception {
	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
	
	//privoxy
	//webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8118));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	
	final HtmlPage page = webClient.getPage("https://prod.ceidg.gov.pl/CEIDG/CEIDG.Public.UI/Search.aspx");
	
	
	//System.out.println(page.asXml());
	
	HtmlInput regonInput = page.getHtmlElementById("MainContent_txtRegon");
	regonInput.setValueAttribute(TEST_REGON);
	
	HtmlInput captchaInput = page.getHtmlElementById("MainContent_tbCaptcha");
	captchaInput.setValueAttribute(CAPTCHA);
	
	HtmlInput searchInput = page.getHtmlElementById("MainContent_btnInputSearch");
	HtmlPage result = searchInput.click();
	
	String resultText = result.asText();
	boolean goToDetails = false;
	if(resultText.contains(TEST_ASSERT_STR)) {
	    goToDetails = true;
	}
	if(!goToDetails) {
	    webClient.closeAllWindows();
	    return null;
	}
	
	HtmlAnchor anchor = result.getHtmlElementById("MainContent_DataListEntities_hrefDetails_0");
	HtmlPage resultDetails = anchor.click();
	
	return resultDetails.asText();
    }

}
