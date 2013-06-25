package zoo.htmunit.tryouts;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestEMS {
    // d:\Temp\captcha\ImageMagick-6.8.3-9\composite.exe 3713342_1.jpg
    // 3713342_2.jpg -compose Lighten 3713342_result.jpg
    // d:\Temp\captcha\ImageMagick-6.8.3-9\convert.exe 3713342_1.jpg
    // 3713342_2.jpg -compose Lighten -composite -threshold 15%
    // 3713342_result.jpg

    // 0000030211

    private final static String EMS_URL = "https://ems.ms.gov.pl/krs/wyszukiwaniepodmiotu";

    private final static String IM_EXEC = "d:/Temp/captcha/ImageMagick-6.8.3-9/convert.exe";
    //-crop 149x47+34+3
    //-crop 138x41+37+9 (bad)
    private final static String IM_OPTS = "-compose Lighten -composite -crop 149x46+34+3 -threshold 5%%";

    private final static String TESS_EXEC = "d:/Temp/Tesseract-OCR/tesseract.exe";
    //private final static String TESS_OPTS = "-psm 7 EMS";
    private final static String TESS_OPTS = "EMS";

    // PZU
    private final static String TEST_KRS = "0000030211";

    public final static String TEST_ASSERT_STR = "POWSZECHNY ZAK£AD UBEZPIECZEÑ NA ¯YCIE SPÓ£KA AKCYJNA";// +
										// TEST_REGON;
    
    //https://ems.ms.gov.pl/krs/wyszukiwaniepodmiotu.podmiotdaneszczegolowe/RP/0000030211
    
    

    public static void main(String[] args) throws Exception {
	testAccuracy();
    }

    public static void testAccuracy() throws Exception {
	int loop = 100;
	int found = 0;
	int error = 0;
	long start = System.nanoTime();
	// final AtomicInteger loopFinal = new AtomicInteger(0);

	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
	//webClient.getOptions().setProxyConfig(new ProxyConfig("10.48.0.180", 5555, true));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	//webClient.getCookieManager().setCookiesEnabled(true);
	webClient.setAjaxController(new NicelyResynchronizingAjaxController());

	// webClient.getOptions().setTimeout(20000);
	for (int i = 1; i <= loop; i++) {
	    // final AtomicInteger stepFinal = new AtomicInteger(1);
	    // loopFinal.incrementAndGet();

	    try {
		final HtmlPage page = webClient.getPage(EMS_URL);
		
		final String pageText = page.asXml();
		pageText.toString();

		// captchaImg
		HtmlDivision captchaDiv = page.getHtmlElementById("kaptchaZone");
		List<HtmlImage> spanDescendants = captchaDiv.getHtmlElementsByTagName("img");
		if (spanDescendants.isEmpty()) {
		    System.err.println("Captcha img not found");
		    continue;
		}

		HtmlImage image = spanDescendants.get(0);

		String sourceUrl = image.getSrcAttribute();
		URL sourceUrlFull = page.getFullyQualifiedUrl(sourceUrl);

		List<File> files = downoladFiles1(sourceUrlFull, sourceUrl, webClient);
		List<File> postFiles = doImageMagic(files);
		List<File> ocrResultFiles = doOCR(postFiles);
		List<Map<Character, AtomicInteger>> stats = getOcrResults(ocrResultFiles);
		String bestMatch = findBestMatch(stats);
		
		System.out.println("BestMatch (" + i + "): " + bestMatch + " for file: " + sourceUrl);

		if (bestMatch == null || "".equals(bestMatch)) {
		    System.err.println("Captcha not solved");
		    continue;
		}
		
		HtmlInput captchaInput = page.getHtmlElementById("kaptchafield");
		//kaptchafield
		captchaInput.setValueAttribute(bestMatch);
		
		
		HtmlCheckBoxInput przedsiebiorcyCheckbox = page.getHtmlElementById("rejestrPrzedsiebiorcy");
		przedsiebiorcyCheckbox.setChecked(true);

		HtmlCheckBoxInput stowarzyszeniaCheckbox = page.getHtmlElementById("rejestrStowarzyszenia");
		stowarzyszeniaCheckbox.setChecked(true);
		
		HtmlCheckBoxInput wUpadlosciCheckbox = page.getHtmlElementById("wUpadlosci");
		wUpadlosciCheckbox.setChecked(true);
		
		HtmlCheckBoxInput oppCheckbox = page.getHtmlElementById("opp");
		oppCheckbox.setChecked(true);
		

		HtmlInput krsInput = page.getHtmlElementById("krs");
		krsInput.setValueAttribute(TEST_KRS);

		//szukaj
		HtmlInput searchInput = page.getHtmlElementById("szukaj");
		HtmlPage result = searchInput.click();
		
		String resultText = result.asText();
		// String xml = result.asXml();

		if (resultText.contains(TEST_ASSERT_STR)) {
		    found++;
		}
		
		//details
		
		
		List<HtmlAnchor> anchors = result.getAnchors();
		HtmlAnchor detailsLink = null;
		for(HtmlAnchor ha : anchors) {
		    final String href = ha.getHrefAttribute();
		    if(href.contains("wyszukiwaniepodmiotu.podmiotdaneszczegolowe")) {
			detailsLink = ha;
			break;
		    }
		}
		
		boolean fetchPdf = false;
		
		if(fetchPdf && detailsLink != null) {
		    HtmlPage detailsPage = detailsLink.click();
		    HtmlInput printInput = detailsPage.getHtmlElementById("pobierzWydruk");
		    Page printPage = printInput.click();
		    
		    
		    InputStream is = printPage.getWebResponse().getContentAsStream();
		    OutputStream os = new BufferedOutputStream(new FileOutputStream(new File("d:/Temp/captcha/EMS/pdf/" + System.currentTimeMillis() + ".pdf"), false));
		    IOUtils.copy(is, os);
		    
		    is.close();
		    os.flush();
		    os.close();
		    
		}
		
		
	    } catch (Exception e) {
		System.err.println(e.getMessage());
		error++;
	    }
	}

	webClient.closeAllWindows();

	long stop = System.nanoTime();

	System.out.println("found: " + found + " loop: " + loop + " error: " + error);
	System.out.println("Done in: " + TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS)
	        + "ms");
    }

    public static List<File> downoladFiles1(final URL sourceUrlFull, String sourceUrl,
	    WebClient webClientParent) throws Exception {
	List<File> result = new ArrayList<>();
	final WebClient webClient = webClientParent;

	for (int i = 0; i < 2; i++) {
	    WebActionRetry<WebResponse> actionRetry = new WebActionRetry<WebResponse>(webClient) {
		@Override
		public WebResponse action(WebClient webClient) throws Exception {
		    return webClient.getWebConnection().getResponse(
			    new WebRequest(sourceUrlFull, HttpMethod.GET));
		}
	    };
	    WebResponse resp = actionRetry.call();

	    int qMark = sourceUrl.indexOf(":image/");
	    File f = new File("d:/Temp/captcha/EMS/Captcha-"
		    + sourceUrl.substring(qMark + 7, sourceUrl.length()) + "_" + i + ".jpg");
	    IOUtils.copy(resp.getContentAsStream(), new FileOutputStream(f));
	    result.add(f);
	}

	if (webClientParent == null) {
	    webClient.closeAllWindows();
	}
	return result;
    }

    private static abstract class WebActionRetry<T> implements Callable<T> {

	private final AtomicInteger retryCount = new AtomicInteger(0);
	private final WebClient webClient;

	public WebActionRetry(WebClient webClient) {
	    this.webClient = webClient;
	}

	protected abstract T action(WebClient webClient) throws Exception;

	@Override
	public T call() throws Exception {
	    final int orgTimeout = webClient.getOptions().getTimeout();
	    webClient.getOptions().setTimeout(5000);
	    try {
		while (retryCount.getAndIncrement() < 10) {

		    try {
			return action(webClient);
		    } catch (SocketTimeoutException e) {
			System.out.println("retry...");
		    } catch (FailingHttpStatusCodeException e) {
			int code = e.getStatusCode();
			if (code == 502 || code == 504) {
			    System.out.println("retry... code=" + code);
			    continue;
			}

			throw e;
		    }
		}
	    } finally {
		webClient.getOptions().setTimeout(orgTimeout);
	    }

	    return action(webClient);
	}

    }

    private static List<File> doImageMagic(List<File> src) throws Exception {

	List<File> result = new ArrayList<>();
	//for (File in : src) {
	    File out = new File(src.get(0).getAbsolutePath().replace("_0.jpg", "") + "_dest.jpg");
	    Process imProc = Runtime.getRuntime().exec(
		    IM_EXEC + " " + src.get(0).getAbsolutePath() + " " + src.get(1).getAbsolutePath() 
//		    + " " + src.get(2).getAbsolutePath() 
		    
//		    + " " + src.get(0).getAbsolutePath() 
//		    + " " + src.get(2).getAbsolutePath() 
//		    + " " + src.get(1).getAbsolutePath() 
		    
		    
//		    + " " + src.get(3).getAbsolutePath() + " " + src.get(4).getAbsolutePath() 
		    + " " + IM_OPTS + " " + out.getAbsolutePath());
	    imProc.waitFor();
	    result.add(out);
	//}

	return result;
    }

    private static List<File> doOCR(List<File> src) throws Exception {
	List<File> result = new ArrayList<>();
	for (File in : src) {
	    File out = new File(in.getAbsolutePath().replace(".jpg", ""));
	    Process imProc = Runtime.getRuntime().exec(
		    TESS_EXEC + " " + in.getAbsolutePath() + " " + out.getAbsolutePath() + " " + TESS_OPTS);
	    imProc.waitFor();
	    result.add(new File(out.getAbsolutePath() + ".txt"));
	}
	return result;
    }

    private static List<Map<Character, AtomicInteger>> getOcrResults(List<File> src) throws Exception {
	List<Map<Character, AtomicInteger>> stats = new ArrayList<>();

	for (File in : src) {
	    List<String> lines = IOUtils.readLines(new FileInputStream(in));
	    if (lines == null || lines.size() == 0)
		continue;

	    String cleanResult = StringUtils.deleteWhitespace(lines.get(0));
	    if (cleanResult == null || cleanResult.length() != 5)
		continue;

	    char[] characters = cleanResult.toCharArray();
	    for (int i = 0; i < characters.length; i++) {
		if (stats.size() < i + 1) {
		    stats.add(new HashMap<Character, AtomicInteger>());
		}
		Map<Character, AtomicInteger> statsSet = stats.get(i);
		AtomicInteger counter = statsSet.get(characters[i]);
		if (counter == null) {
		    counter = new AtomicInteger();
		    statsSet.put(characters[i], counter);
		}
		counter.incrementAndGet();
	    }
	}
	return stats;
    }

    private static String findBestMatch(List<Map<Character, AtomicInteger>> stats) {
	StringBuilder result = new StringBuilder();
	for (Map<Character, AtomicInteger> characterStat : stats) {
	    Set<Entry<Character, AtomicInteger>> entries = characterStat.entrySet();

	    Entry<Character, AtomicInteger> bestEntry = null;
	    for (Entry<Character, AtomicInteger> entry : entries) {
		if (bestEntry == null) {
		    bestEntry = entry;
		    continue;
		}
		if (bestEntry.getValue().get() < entry.getValue().get()) {
		    bestEntry = entry;
		}
	    }

	    if (bestEntry == null) {
		result.append("?");
	    } else {
		result.append(bestEntry.getKey());
	    }
	}

	return result.toString();
    }

}
