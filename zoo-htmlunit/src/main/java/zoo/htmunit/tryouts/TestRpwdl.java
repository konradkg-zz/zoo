package zoo.htmunit.tryouts;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class TestRpwdl {
	public static void main(String[] args) throws Exception {

		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setHomePage("http://rpwdl.csioz.gov.pl/");
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		// webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {

		final HtmlPage page = webClient.getPage("http://rpwdl.csioz.gov.pl/rpm/public/filtrKsiag.jsf");

		// System.out.println(page.asXml());

		HtmlSelect organSelect = page.getHtmlElementById("f_organ");

		System.out.println(organSelect);
		List<HtmlOption> options = organSelect.getOptions();

		for (int i = 1; i < options.size(); i++) {
			organSelect.setSelectedAttribute(organSelect.getOptions().get(i), true);

			HtmlSubmitInput btnSzukajKsiegi = page.getHtmlElementById("btnSzukajKsiegi");

			HtmlPage result = btnSzukajKsiegi.click();
			// System.out.println(result.asXml());
			// final List<HtmlForm> forms = page.getForms();

			List<HtmlAnchor> htmlAnchorList = result.getAnchors();
			for (HtmlAnchor ha : htmlAnchorList) {
				// System.out.println(ha.getTextContent());
				if (ha.getTextContent().equalsIgnoreCase("Drukuj")) {
					Page haPage = ha.openLinkInNewWindow();
					// System.out.println(haPage.getWebResponse().getContentAsString());
					System.out.println(((HtmlPage) haPage).asText());

					break;
				}
			}
			
			//page.cleanUp();
			page.cleanUp();
			page.refresh();
		}
		
		// forms.toString();
	}

}
