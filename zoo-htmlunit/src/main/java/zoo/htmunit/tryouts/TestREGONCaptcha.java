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
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class TestREGONCaptcha {
    private final static String REGON_URL = "http://www.stat.gov.pl/regon/";

    private final static String IM_EXEC = "d:/Temp/captcha/ImageMagick-6.8.3-9/convert.exe";
    private final static String IM_OPTS = "-threshold 15%%";

    private final static String TESS_EXEC = "d:/Temp/Tesseract-OCR/tesseract.exe";
    private final static String TESS_OPTS = "-l reg -psm 7 REGON";

    private final static String TEST_NIP = "8131273211";
    private final static String TEST_REGON = "140745674";

    public final static String TEST_ASSERT_STR = "Numer identyfikacyjny REGON	" + TEST_REGON;

    public static void main(String[] args) throws Exception {
	testAccuracy();
    }

    public static void testAccuracy() throws Exception {
	int loop = 15;
	int found = 0;
	int error = 0;
	long start = System.nanoTime();
	// final AtomicInteger loopFinal = new AtomicInteger(0);

	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
	webClient.getOptions().setProxyConfig(new ProxyConfig("10.48.0.180", 3128));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.setAjaxController(new NicelyResynchronizingAjaxController());

	for (int i = 1; i <= loop; i++) {
	    // final AtomicInteger stepFinal = new AtomicInteger(1);
	    // loopFinal.incrementAndGet();

	    try {
		final HtmlPage page = webClient.getPage(REGON_URL);

		// NIP
		HtmlRadioButtonInput nipRadio = page.getHtmlElementById("by_1nip_RB");
		nipRadio.click();
		// captchaImg
		HtmlSpan captchaSpan = page.getHtmlElementById("captchaImg");
		List<HtmlImage> spanDescendants = captchaSpan.getHtmlElementsByTagName("img");
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

		HtmlInput nipInput = page.getHtmlElementById("criterion1TF");
		nipInput.setValueAttribute(TEST_NIP);

		HtmlInput captchaInput = page.getHtmlElementById("verifCodeTF");
		captchaInput.setValueAttribute(bestMatch);

		HtmlInput searchInput = page.getHtmlElementById("sendQueryB");
		HtmlPage result = searchInput.click();

		String resultText = result.asText();
		// String xml = result.asXml();

		if (resultText.contains(TEST_ASSERT_STR)) {
		    found++;
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

    public static List<File> downoladFiles1(URL sourceUrlFull, String sourceUrl, WebClient webClientParent)
	    throws Exception {
	List<File> result = new ArrayList<>();
	final WebClient webClient = webClientParent;

	WebResponse resp = webClient.getWebConnection().getResponse(
	        new WebRequest(sourceUrlFull, HttpMethod.GET));
	int qMark = sourceUrl.indexOf('?');
	File f = new File("d:/Temp/captcha/REGON/Captcha-"
	        + sourceUrl.substring(qMark + 1, sourceUrl.length()) + ".jpg");
	IOUtils.copy(resp.getContentAsStream(), new FileOutputStream(f));
	result.add(f);

	if (webClientParent == null) {
	    webClient.closeAllWindows();
	}
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
