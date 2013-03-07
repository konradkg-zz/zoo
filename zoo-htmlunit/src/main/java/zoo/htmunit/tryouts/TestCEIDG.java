package zoo.htmunit.tryouts;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCEIDG {
    
    public static void main(String[] args) throws Exception {
final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
	
	//privoxy
	webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8118));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	
	final HtmlPage page = webClient.getPage("https://prod.ceidg.gov.pl/CEIDG/CEIDG.Public.UI/Search.aspx");
	
	System.out.println(page.asXml());
	
	
	
	
	webClient.closeAllWindows();
    }

}
