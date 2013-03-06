package zoo.htmunit.tryouts;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestSW {

    public static void main(String[] args) throws Exception {
	
	String content = "okregowy-inspektorat-sluzby-wieziennej-katowice/zaklad-karny-zabrze/index,69.html"; 
	System.out.println(content.matches(".*index,[0-9]+\\.html"));
	
	content = "http://sw.gov.pl/pl/jednostki/index,6,0.html";
	System.out.println(content.matches(".*jednostki/index,[0-9]+,0\\.html"));
	
	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
	
	//privoxy
	webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8118));
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	
	final HtmlPage page = webClient.getPage("http://sw.gov.pl/pl/jednostki/");
	
	HtmlPage currentPage = page;
	AtomicInteger currentPageId = new AtomicInteger();
	while(currentPage != null) {
	    currentPage = drukuj(currentPage, currentPageId);
	}
	
	
	
	webClient.closeAllWindows();
    }

    public static HtmlPage drukuj(HtmlPage page, AtomicInteger currentPageId) throws Exception {
	List<HtmlAnchor> htmlAnchorList = page.getAnchors();

	boolean isFirst = true;
	for (HtmlAnchor ha : htmlAnchorList) {
	    // System.out.println(ha.getTextContent());
	    String content = ha.getTextContent();
	    String href = ha.getHrefAttribute();
	    Integer pageId = getAsDigit(content);
	    if (isFirst && href.matches(".*index,[0-9]+\\.html")) {
		Page haPage = ha.openLinkInNewWindow();
		System.out.println(((HtmlPage) haPage).asText());
		isFirst = false;
	    } else if (pageId != null && pageId > currentPageId.get()) {
		System.out.println(pageId);
		currentPageId.set(pageId);
		return ha.click();
	    }
	}

	return null;
    }

    public static Integer getAsDigit(String s) {
	try {
	    return Integer.valueOf(s);
	} catch (Exception e) {
	    return null;
	}
    }

}
