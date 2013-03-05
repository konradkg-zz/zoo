package zoo.htmunit.tryouts;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;

public class TestRpwdl {
	public static void main(String[] args) throws Exception {

		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setHomePage("http://rpwdl.csioz.gov.pl/");
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
			
			@Override
			public void timeoutError(HtmlPage htmlPage, long allowedTime, long executionTime) {
				// TODO Auto-generated method stub
				System.out.println(htmlPage.asText());
			}
			
			@Override
			public void scriptException(HtmlPage htmlPage, ScriptException scriptException) {
				// TODO Auto-generated method stub
				System.out.println(htmlPage.asText());
			}
			
			@Override
			public void malformedScriptURL(HtmlPage htmlPage, String url, MalformedURLException malformedURLException) {
				// TODO Auto-generated method stub
				System.out.println(htmlPage.asText());
			}
			
			@Override
			public void loadScriptError(HtmlPage htmlPage, URL scriptUrl, Exception exception) {
				// TODO Auto-generated method stub
				System.out.println(htmlPage.asText());
			}
		});
		
		
		final HtmlPage page = webClient.getPage("http://rpwdl.csioz.gov.pl/rpm/public/filtrKsiag.jsf");
		
		//System.out.println(page.asXml());
		
		HtmlSelect organSelect = page.getHtmlElementById("f_organ");
		
		System.out.println(organSelect);
		organSelect.getOptions();
		organSelect.setSelectedAttribute(organSelect.getOptions().get(1), true);
		
		
		HtmlSubmitInput btnSzukajKsiegi = page.getHtmlElementById("btnSzukajKsiegi");
		
		HtmlPage result = btnSzukajKsiegi.click();
		//System.out.println(result.asXml());
		//final List<HtmlForm> forms = page.getForms();

		List<HtmlAnchor> htmlAnchorList = result.getAnchors();
		for(HtmlAnchor ha : htmlAnchorList) {
			//System.out.println(ha.getTextContent());
			if(ha.getTextContent().equalsIgnoreCase("Drukuj")) {
				Page haPage = ha.openLinkInNewWindow();
				System.out.println(haPage.getWebResponse().getContentAsString());
			}
			//ha.openLinkInNewWindow();
		}
		//forms.toString();
	}
}
