package zoo.htmunit.tryouts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.jetty.util.ajax.JSON;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;

public class TestCEIDGLocations {

	// private final static String CEIDG_URL =
	// "https://prod.ceidg.gov.pl/CEIDG/ceidg.public.ui/";

//	private final static String[] PROVINCES = {
//		"åL•SKIE",
//		"DOLNOåL•SKIE",
//		"KUJAWSKO-POMORSKIE",
//		"LUBELSKIE",
//		"LUBUSKIE",
//		"MA£OPOLSKIE",
//		"OPOLSKIE",
//		"PODLASKIE",
//		"WARMI—SKO-MAZURSKIE",
//		"WIELKOPOLSKIE",
//		"ZACHODNIOPOMORSKIE",
//		"MAZOWIECKIE",
//		"PODKARPACKIE",
//		"£”DZKIE",
//		"POMORSKIE",
//		"åWI TOKRZYSKIE"
//};

	
	private final static String[] PROVINCES = {
			"\u015aL\u0104SKIE",
			"DOLNO\u015aL\u0104SKIE",
			"KUJAWSKO-POMORSKIE",
			"LUBELSKIE",
			"LUBUSKIE",
			"MA\u0141OPOLSKIE",
			"OPOLSKIE",
			"PODLASKIE",
			"WARMI\u0143SKO-MAZURSKIE",
			"WIELKOPOLSKIE",
			"ZACHODNIOPOMORSKIE",
			"MAZOWIECKIE",
			"PODKARPACKIE",
			"\u0141”DZKIE",
			"POMORSKIE",
			"\u015aWI\u0118TOKRZYSKIE"
	};
//	private final static String[] LETTERS = { "A", "\u0104", "B", "C", "\u0106", "D", "E", "\u0118", "F", "G", "H", "I",
//			"J", "K", "L", "\u0141", "M", "N", "\u0143", "O", "”", "P", "R", "S", "\u015a", "T", "U", "W", "Y", "Z",
//			"è ", "Ø" };
//	
	private final static String[] LETTERS = { "A"};


	public static void main(String[] args) throws Exception {
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
		// webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1",
		// 8118));
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		// final UnexpectedPage jsonPage = webClient
		// .getPage("https://prod.ceidg.gov.pl/CEIDG.Dictionary.Service/TerytHandler.ashx?mode=xfautocomplete&name=city&count=10&parentkey=MAZOWIECKIE;&term=a");

		for (String province : PROVINCES) {
			for (String term : LETTERS) {
				String content = download(encodeQuietly(province), term);
				
				if (content == null || "".equals(content)) {
					System.out.println("empty resp for: " + province + ", " + term);
					continue;
				}

				Object json = JSON.parse(content);
				if (json == null) {
					System.out.println("null json for: " + province + ", " + term);
					continue;
				}
				if ((json instanceof Object[]) == false) {
					System.out.println("json not an array for: " + province + ", " + term);
					continue;
				}
				Object[] array = (Object[]) json;
				if (array.length == 0) {
					System.out.println("empty json array for: " + province + ", " + term);
					continue;
				}
				
				content = StringEscapeUtils.unescapeJava(content);
				saveToFile(new File("p:/" + province + "-" + array.length + ".json"), content);
			}

		}
	}

	public static String encodeQuietly(String s ) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}
	
//	public static String decodeQuietly(String s ) {
//		try {
//			return URLDecoder.decode(s, "UTF-8");
//		} catch (Exception e) {
//			return "";
//		}
//	}
	
	
	public static String download(String province, String term) throws Exception {
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		final UnexpectedPage jsonPage = webClient
				.getPage("https://prod.ceidg.gov.pl/CEIDG.Dictionary.Service/TerytHandler.ashx?mode=xfautocomplete&name=city&count=2000000&parentkey="
						+ province + ";"); 
						//+ ";&term=" + term);
		return IOUtils.toString(jsonPage.getInputStream(), "UTF-8");

	}

	public static void saveToFile(File f, String content) throws IOException {
		IOUtils.write(content, new FileOutputStream(f), Charset.forName("UTF-8"));
	}

	// Object json = JSON.parse(new InputStreamReader(jsonPage.getInputStream(),
	// "UTF-8"));

	// final AtomicBoolean processNext = new AtomicBoolean(false);
	// final AtomicInteger id = new AtomicInteger(0);
	//
	// webClient.setAjaxController(new AjaxController() {
	// private static final long serialVersionUID = 1L;
	//
	// //https://prod.ceidg.gov.pl/CEIDG.Dictionary.Service/TerytHandler.ashx?mode=xfautocomplete&name=province&count=10&parentkey=&term=MAZOWIECKIE
	// //https://prod.ceidg.gov.pl/CEIDG.Dictionary.Service/TerytHandler.ashx?mode=xfautocomplete&name=city&count=10&parentkey=MAZOWIECKIE;&term=a
	// public boolean processSynchron(final HtmlPage page, final WebRequest
	// request, final boolean async) {
	//
	// String xml = page.asXml();
	// try {
	// IOUtils.write(xml, new FileOutputStream(new File("p:/out-" +
	// id.incrementAndGet() +".html")), Charset.forName("UTF-8"));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// String body = request.getRequestBody();
	//
	//
	// processNext.set(true);
	// return true;
	// }
	// });
	//
	// final HtmlPage page = webClient.getPage(CEIDG_URL + "Search.aspx");
	//
	// HtmlInput province = page.getHtmlElementById("MainContent_txtProvince");
	// province.focus();
	// province.setValueAttribute("MAZOWIECKIE");
	// province.fireEvent(Event.TYPE_KEY_DOWN);
	//
	// HtmlInput city = page.getHtmlElementById("MainContent_txtCity");
	// city.focus();
	// city.setValueAttribute("a");
	// city.fireEvent(Event.TYPE_KEY_DOWN);
	// // city.fireEvent(Event.TYPE_KEY_PRESS);
	// // city.fireEvent(Event.TYPE_KEY_UP);
	// // city.fireEvent(Event.TYPE_BLUR);
	// city.fireEvent(Event.TYPE_CHANGE);
	//
	// //MainContent_txtProvince
	// //MainContent_txtCity
	//
	// TimeUnit.MILLISECONDS.sleep(2000);
	// webClient.closeAllWindows();
	// }

}
