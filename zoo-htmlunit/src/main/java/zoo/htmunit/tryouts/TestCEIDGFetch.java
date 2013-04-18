package zoo.htmunit.tryouts;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpWebConnection;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCEIDGFetch {
	// static String url =
	// "https://prod.ceidg.gov.pl/CEIDG/ceidg.public.ui/SearchDetails.aspx?Id=8fe3574e-b03d-4f96-b6f9-14e2005291e5";
	// static String url =
	// "https://213.17.182.85/CEIDG/ceidg.public.ui/SearchDetails.aspx?Id=cb0e5201-01b6-4559-b7d8-d721f7b39a1b";
	// static String url = "https://www.mbank.com.pl/";
	static String url = "https://www.bph.pl/pi/do/Login";

	// static String url = "http://www.onet.pl";

	public static void main(String[] args) throws Exception {
		jsEnabled();
		// jsDisabled();
		//
		// jsEnabled();
		// jsDisabled();

	}

	public static void jsEnabled() throws Exception {
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

			webClient.getOptions().setProxyConfig(new ProxyConfig("10.48.0.180", 3128));
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			webClient.setWebConnection(new MyWebConnection(webClient));

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
			// webClient.setAjaxController(new
			// NicelyResynchronizingAjaxController());

			final HtmlPage page = webClient.getPage(url);

			page.getBody();

			String text = page.asText();

			webClient.closeAllWindows();

			System.out.println("jsDisabled() (" + i + ") "
					+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + "[ms]");
		}
	}

	static class MyWebConnection extends HttpWebConnection {

		public MyWebConnection(WebClient webClient) {
			super(webClient);
		}

		protected AbstractHttpClient createHttpClient() {
			AbstractHttpClient httpClient = super.createHttpClient();
			//HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), false);
			//workAroundReverseDnsBugInHoneycombAndEarlier(httpClient);
			return httpClient;
		}

		private void workAroundReverseDnsBugInHoneycombAndEarlier(HttpClient client) {
			// Android had a bug where HTTPS made reverse DNS lookups (fixed in
			// Ice Cream Sandwich)
			// http://code.google.com/p/android/issues/detail?id=13117
			SocketFactory socketFactory = new LayeredSocketFactory() {
				SSLSocketFactory delegate = SSLSocketFactory.getSocketFactory();

				@Override
				public Socket createSocket() throws IOException {
					return delegate.createSocket();
				}

				@Override
				public Socket connectSocket(Socket sock, String host, int port,
						InetAddress localAddress, int localPort, HttpParams params) throws IOException {
					return delegate.connectSocket(sock, host, port, localAddress, localPort, params);
				}

				@Override
				public boolean isSecure(Socket sock) throws IllegalArgumentException {
					return delegate.isSecure(sock);
				}

				@Override
				public Socket createSocket(Socket socket, String host, int port,
						boolean autoClose) throws IOException {
					injectHostname(socket, host);
					return delegate.createSocket(socket, host, port, autoClose);
				}

				private void injectHostname(Socket socket, String host) {
					try {
						Field field = InetAddress.class.getDeclaredField("hostName");
						field.setAccessible(true);
						field.set(socket.getInetAddress(), host);
					} catch (Exception ignored) {
					}
				}
			};
			client.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", socketFactory, 443));
		}

	}
}
