package zoo.htmunit.tryouts;

import java.util.concurrent.TimeUnit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCEIDGFetch {
	static String url = "https://prod.ceidg.gov.pl/CEIDG/ceidg.public.ui/SearchDetails.aspx?Id=8fe3574e-b03d-4f96-b6f9-14e2005291e5";
	
	public static void main(String[] args) throws Exception{
//		jsEnabled();
//		jsDisabled();
//		
//		jsEnabled();
		jsDisabled();
		
		
	}
	
	public static void jsEnabled() throws Exception {
		for (int i = 0; i < 5; i++) {
			long start = System.nanoTime();
			final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			final HtmlPage page = webClient.getPage(url);
			
			page.getBody();

			webClient.closeAllWindows();

			System.out.println("jsEnabled() (" + i + ") "
					+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + "[ms]");
		}
	}
	
	public static void jsDisabled() throws Exception {
		for (int i = 0; i < 5; i++) {
			long start = System.nanoTime();
			final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			//webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			final HtmlPage page = webClient.getPage(url);
			
			page.getBody();

			String text = page.asText();
			
			webClient.closeAllWindows();

			System.out.println("jsDisabled() (" + i + ") "
					+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + "[ms]");
		}
	}

}
