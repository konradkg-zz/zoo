package org.h2.fulltext;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.ASCIIFoldingFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.pl.PolishAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;


public class CustomPolishAnalyzer1 extends StopwordAnalyzerBase {

	/** Default maximum allowed token length */
	public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;

	private int maxTokenLength = DEFAULT_MAX_TOKEN_LENGTH;

	public final static String DEFAULT_STOPWORD_FILE = "stopwords.txt";
	/**
	 * Specifies whether deprecated acronyms should be replaced with HOST type.
	 * See {@linkplain "https://issues.apache.org/jira/browse/LUCENE-1068"}
	 */
	private final boolean replaceInvalidAcronym;

	public CustomPolishAnalyzer1(Version matchVersion, Set<?> stopWords) {
		super(matchVersion, stopWords);
		replaceInvalidAcronym = matchVersion.onOrAfter(Version.LUCENE_24);
	}

	public CustomPolishAnalyzer1(Version matchVersion) {
		this(matchVersion, DefaultsHolder.DEFAULT_STOP_SET);
	}

	/**
	 * Set maximum allowed token length. If a token is seen that exceeds this
	 * length then it is discarded. This setting only takes effect the next time
	 * tokenStream or reusableTokenStream is called.
	 */
	public void setMaxTokenLength(int length) {
		maxTokenLength = length;
	}

	/**
	 * @see #setMaxTokenLength
	 */
	public int getMaxTokenLength() {
		return maxTokenLength;
	}

	@Override
	protected TokenStreamComponents createComponents(final String fieldName, final Reader reader) {
		final StandardTokenizer src = new StandardTokenizer(matchVersion, reader);
		src.setMaxTokenLength(maxTokenLength);
		src.setReplaceInvalidAcronym(replaceInvalidAcronym);
		TokenStream tok = new StandardFilter(matchVersion, src);
		tok = new LowerCaseFilter(matchVersion, tok);
		tok = new StopFilter(matchVersion, tok, stopwords);
		tok = new ASCIIFoldingFilter(tok);
		return new TokenStreamComponents(src, tok) {
			@Override
			protected boolean reset(final Reader reader) throws IOException {
				src.setMaxTokenLength(CustomPolishAnalyzer1.this.maxTokenLength);
				return super.reset(reader);
			}
		};
	}
	
	private static class DefaultsHolder {
		static final Set<?> DEFAULT_STOP_SET;

		static {
		    try {
			DEFAULT_STOP_SET = WordlistLoader.getWordSet(IOUtils.getDecodingReader(PolishAnalyzer.class,
			        DEFAULT_STOPWORD_FILE, IOUtils.CHARSET_UTF_8), "#", Version.LUCENE_CURRENT);
		    } catch (IOException ex) {
			// default set should always be present as it is part of the
			// distribution (JAR)
			throw new RuntimeException("Unable to load default stopword set", ex);
		    }
		}
	    }
	
}