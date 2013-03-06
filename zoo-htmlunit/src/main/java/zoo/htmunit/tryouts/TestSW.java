package zoo.htmunit.tryouts;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;

public class TestSW {
    
    public static void main(String[] args) throws Exception {
	
	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
	
	//privoxy
	webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8118));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	
	
	
	
	webClient.closeAllWindows();
    }

}
