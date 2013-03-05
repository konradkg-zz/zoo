package zoo.htmunit.tryouts;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SimpleExe {
	
	public static void main(String[] args) throws Exception {
		
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
	    final HtmlPage page = webClient.getPage("http://www.gazeta.pl/0,0.html");
	    
	    final List<HtmlForm> forms = page.getForms();
		
	    forms.toString();
	}

}
