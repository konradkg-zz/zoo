package zoo.htmunit.tryouts;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCEIDGPool {

	static String KEY = "CEIDG";
	static GenericKeyedObjectPool<String, WebClient> pool;
	public static void main(String[] args) throws Exception {
		pool = new GenericKeyedObjectPool<String, WebClient>(new Factory(), 2);
		//pool.setConfig(conf)
		noPool();
		pool();
		
		noPool();
		pool();
		
		
//		WebClient c1 = pool.borrowObject(KEY);
//		System.out.println(System.identityHashCode(c1));
//		WebClient c2 = pool.borrowObject(KEY);
//		System.out.println(System.identityHashCode(c2));
//		WebClient cc1 = pool.borrowObject(KEY + 1);
//		System.out.println(System.identityHashCode(cc1));
//		
//		pool.returnObject(KEY, c1);
//		WebClient c4 = pool.borrowObject(KEY);
//		System.out.println(System.identityHashCode(c4));
//		pool.returnObject(KEY, c4);
//		
//		pool.returnObject(KEY, c2);
//		pool.returnObject(KEY, cc1);
		
		
		pool.close();
	}

	public static void noPool() throws Exception {
		for (int i = 0; i < 5; i++) {
			long start = System.nanoTime();
			final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			final HtmlPage page = webClient.getPage("https://prod.ceidg.gov.pl/CEIDG/CEIDG.Public.UI/Search.aspx");
			download(webClient);
			download(webClient);
			download(webClient);
			
			page.getBody();

			webClient.closeAllWindows();

			System.out.println("noPool() (" + i + ") "
					+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + "[ms]");
		}
	}

	public static void pool() throws Exception {
		
		for (int i = 0; i < 5; i++) {
			long start = System.nanoTime();
			WebClient webClient = pool.borrowObject(KEY);
			try {
				System.out.println("(" + i + ") Cache size=" + webClient.getCache().getSize());
				final HtmlPage page = webClient.getPage("https://prod.ceidg.gov.pl/CEIDG/CEIDG.Public.UI/Search.aspx");
				//webClient.getStorageHolder().getStore(storageType, page)
				
				System.out.println("(" + i + ") Cache size=" + webClient.getCache().getSize());
				download(webClient);
				System.out.println("(" + i + ") Cache size=" + webClient.getCache().getSize());
				download(webClient);
				System.out.println("(" + i + ") Cache size=" + webClient.getCache().getSize());
				download(webClient);
				
				page.getBody();
			} catch (Exception e) {
				pool.invalidateObject(KEY, webClient);
				webClient = null;
			}
			//webClient.getCache().clear();
//			System.out.println("Cache size=" + webClient.getCache().getSize());
//			webClient.getCookieManager().clearCookies();
			
			if(webClient != null) {
				pool.returnObject(KEY, webClient);
			}
			System.out.println("  pool() (" + i + ") "
					+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + "[ms]");
		}

		
	}
	
	static WebResponse download(WebClient webClient) throws Exception {
		URL url = new URL("https://prod.ceidg.gov.pl/CEIDG/CEIDG.Public.UI/captcha.ashx?id=e3f04e28-16d5-4ed1-be36-53eb67c80b5e");
		WebResponse response = webClient.getWebConnection().getResponse(
				    new WebRequest(url, HttpMethod.GET));
		return response;
	}
	
	public static class Factory implements KeyedPoolableObjectFactory<String, WebClient> {

		@Override
		public WebClient makeObject(String key) throws Exception {
			final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			
			return webClient;
		}

		@Override
		public void destroyObject(String key, WebClient obj) throws Exception {
			obj.closeAllWindows();
		}

		@Override
		public boolean validateObject(String key, WebClient obj) {
			// TODO Test TTL
			return true;
		}

		@Override
		public void activateObject(String key, WebClient obj) throws Exception {
		}

		@Override
		public void passivateObject(String key, WebClient obj) throws Exception {
			obj.getCookieManager().clearCookies();
		}
		
	}

}
