package zoo.htmunit.tryouts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCEIDGCaptcha {

    private final static String CEIDG_URL = "https://prod.ceidg.gov.pl/CEIDG/ceidg.public.ui/";

    private final static String IM_EXEC = "d:/Temp/captcha/ImageMagick-6.8.3-9/convert.exe";
    // private final static String IM_OPTS =
    // "-background white -contrast-stretch 500 -resize 75%% -resize 300%% -fuzz 10%% "
    // +
    // "-fill white -opaque grey -opaque grey49 -opaque grey55 -resize 50%% -resize 300%% -colors 7 -sharpen 1x1  -resize 30%%";
    //
    private final static String IM_OPTS = "-morphology Open Corners -fuzz 10%% -fill white -opaque grey -opaque grey90";

    private final static String TESS_EXEC = "d:/Temp/Tesseract-OCR/tesseract.exe";
    private final static String TESS_OPTS = "-psm 7 CEIDG";

    public final static String CAPTCHA = "AAAAA";

    public final static String TEST_REGON = "350279436";

    public final static String TEST_ASSERT_STR = "SIEPRAWSKA";

    public static void main(String[] args) throws Exception {

	// List<File> files = downoladFiles();
	// List<File> postFiles = doImageMagic(files);
	// List<File> ocrResultFiles = doOCR(postFiles);
	// printResults(ocrResultFiles);
	// List<Map<Character, AtomicInteger>> stats =
	// getOcrResults(ocrResultFiles);
	//
	// String bestMatch = findBestMatch(stats);
	// System.out.println("\n\nBestMatch: " + bestMatch);

	testAccuracy();
    }

    public static void testAccuracy() throws Exception {
	int loop = 50;
	int found = 0;
	int replaced = 0;
	int aaaaa = 0;
	long start = System.nanoTime();
	for (int i = 1; i <= loop; i++) {
	    final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
	    webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8118));
	    webClient.getOptions().setCssEnabled(false);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());

	    final HtmlPage page = webClient.getPage(CEIDG_URL + "Search.aspx");

	    HtmlImage image = page.getHtmlElementById("MainContent_ctrlCaptcha");
	    String sourceUrl = image.getSrcAttribute();
	    URL sourceUrlFull = page.getFullyQualifiedUrl(sourceUrl);

	    List<File> files = downoladFiles1(sourceUrlFull, sourceUrl);
	    List<File> postFiles = doImageMagic(files);
	    List<File> ocrResultFiles = doOCR(postFiles);
	    List<Map<Character, AtomicInteger>> stats = getOcrResults(ocrResultFiles);
	    String bestMatch = findBestMatch(stats);

	    System.out.println("\n\nBestMatch (" + i  +"): " + bestMatch + " for file: " + sourceUrl);

	    if(bestMatch != null && CAPTCHA.equals(bestMatch)) {
		aaaaa++;
	    }
	    
	    if(bestMatch == null || "".equals(bestMatch)) {
		bestMatch = CAPTCHA;
		replaced++;
	    }
	    
	    
		
	    HtmlInput regonInput = page.getHtmlElementById("MainContent_txtRegon");
	    regonInput.setValueAttribute(TEST_REGON);

	    HtmlInput captchaInput = page.getHtmlElementById("MainContent_tbCaptcha");
	    captchaInput.setValueAttribute(bestMatch);

	    HtmlInput searchInput = page.getHtmlElementById("MainContent_btnInputSearch");
	    HtmlPage result = searchInput.click();

	    String resultText = result.asText();
	    if (resultText.contains(TEST_ASSERT_STR)) {
		found++;
	    }

	    webClient.closeAllWindows();
	}
	
	long stop = System.nanoTime();
	
	System.out.println("found: " + found + " loop: " + loop + " replaced: " + replaced + " aaaaa:" + aaaaa);
	System.out.println("Done in: " + TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS) + "ms");
	
    }

    static List<File> downoladFiles() throws Exception {
	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

	// privoxy
	webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8118));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.setAjaxController(new NicelyResynchronizingAjaxController());

	final HtmlPage page = webClient.getPage(CEIDG_URL + "Search.aspx");

	// System.out.println(page.asXml());

	HtmlImage image = page.getHtmlElementById("MainContent_ctrlCaptcha");
	String sourceUrl = image.getSrcAttribute();
	URL sourceUrlFull = page.getFullyQualifiedUrl(sourceUrl);
	// List<File> result = new ArrayList<>();
	// for(int i = 0; i < 15; i++) {
	// WebResponse resp = webClient.getWebConnection().getResponse(new
	// WebRequest(sourceUrlFull, HttpMethod.GET));
	// File f = new File("d:/Temp/captcha/CEIDG/" + sourceUrl.substring(16,
	// 52) + "-" + i +".jpg");
	// IOUtils.copy(resp.getContentAsStream(), new FileOutputStream(f));
	// result.add(f);
	// }

	webClient.closeAllWindows();

	return downoladFiles1(sourceUrlFull, sourceUrl);
    }

    public static List<File> downoladFiles1(URL sourceUrlFull, String sourceUrl) throws Exception {
	List<File> result = new ArrayList<>();
	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);

	// privoxy
	webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8118));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.getOptions().setJavaScriptEnabled(false);
	// webClient.setAjaxController(new
	// NicelyResynchronizingAjaxController());

	for (int i = 0; i < 15; i++) {
	    WebResponse resp = webClient.getWebConnection().getResponse(
		    new WebRequest(sourceUrlFull, HttpMethod.GET));
	    File f = new File("d:/Temp/captcha/CEIDG/" + sourceUrl.substring(16, 52) + "-" + i + ".jpg");
	    IOUtils.copy(resp.getContentAsStream(), new FileOutputStream(f));
	    result.add(f);
	}

	webClient.closeAllWindows();
	return result;
    }

    private static List<File> doImageMagic(List<File> src) throws Exception {

	List<File> result = new ArrayList<>();
	for (File in : src) {
	    File out = new File(in.getAbsolutePath().replace(".jpg", "") + "_dest.jpg");
	    Process imProc = Runtime.getRuntime().exec(
		    IM_EXEC + " " + in.getAbsolutePath() + " " + IM_OPTS + " " + out.getAbsolutePath());
	    imProc.waitFor();
	    result.add(out);
	}

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

    private static void printResults(List<File> src) throws Exception {
	for (File in : src) {
	    List<String> lines = IOUtils.readLines(new FileInputStream(in));
	    if (lines != null && lines.size() > 0) {
		System.out.println(in.getAbsolutePath() + ": " + lines.get(0));
	    } else {
		System.out.println(in.getAbsolutePath() + ": " + "<empty>");
	    }
	}
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
