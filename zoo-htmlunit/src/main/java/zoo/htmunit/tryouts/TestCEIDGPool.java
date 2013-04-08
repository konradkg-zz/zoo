package zoo.htmunit.tryouts;

import java.util.concurrent.TimeUnit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCEIDGPool {

	public static void main(String[] args) throws Exception {
		//noPool();
		pool();
		
		//noPool();
		//pool();
		
	}

	public static void noPool() throws Exception {
		for (int i = 0; i < 5; i++) {
			long start = System.nanoTime();
			final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			final HtmlPage page = webClient.getPage("https://prod.ceidg.gov.pl/CEIDG/CEIDG.Public.UI/Search.aspx");
			page.getBody();

			webClient.closeAllWindows();

			System.out.println("noPool() (" + i + ") "
					+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + "[ms]");
		}
	}

	public static void pool() throws Exception {
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		for (int i = 0; i < 5; i++) {
			long start = System.nanoTime();
			final HtmlPage page = webClient.getPage("https://prod.ceidg.gov.pl/CEIDG/CEIDG.Public.UI/Search.aspx");
			page.getBody();
			//webClient.getCache().clear();
			System.out.println("Cache size=" + webClient.getCache().getSize());
			webClient.getCookieManager().clearCookies();
			System.out.println("  pool() (" + i + ") "
					+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + "[ms]");
		}

		webClient.closeAllWindows();
	}

}
