package de.twitter4serioussearch.analysis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.champeau.ld.UberLanguageDetector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;

/**
 * Helper class for determining the appropriate language specific
 * {@link org.apache.lucene.analysis.Analyzer Analyzer}
 *
 * @author tobiaslarscheid
 *
 */
public class AnalyzerMapping {
	private static class Holder {
		static final AnalyzerMapping instance = new AnalyzerMapping();
	}

	/**
	 * mapping from language code to class of
	 * {@link org.apache.lucene.analysis.Analyzer Analyzer}
	 */
	private final Map<String, Class<? extends Analyzer>> mapping = Collections
			.synchronizedMap(new HashMap<String, Class<? extends Analyzer>>(3));
	/**
	 * mapping from language code to actual instance of
	 * {@link org.apache.lucene.analysis.Analyzer Analyzer}
	 */
	private final Map<String, Analyzer> cache = Collections
			.synchronizedMap(new HashMap<String, Analyzer>(3));
	/**
	 * Lucene {@link org.apache.lucene.index.IndexWriter IndexWriter} uses the
	 * same {@link org.apache.lucene.analysis.Analyzer Analyzer} for every
	 * {@link org.apache.lucene.document.Document Document}. We therefore
	 * externally tokenize the text we want to store and concatenate the tokens
	 * with this delimiter. The
	 * {@link de.twitter4serioussearch.analysis.AnalyzerMapping#ANALYZER_FOR_DELIMITER
	 * ANALYZER_FOR_DELIMITER} is then used in the
	 * {@link org.apache.lucene.index.IndexWriter IndexWriter} to split the
	 * tokens.
	 */
	public final String TOKEN_DELIMITER = " ";
	/**
	 * see
	 * {@link de.twitter4serioussearch.analysis.AnalyzerMapping#TOKEN_DELIMITER
	 * TOKEN_DELIMITER}
	 */
	public final Analyzer ANALYZER_FOR_DELIMITER = new WhitespaceAnalyzer();
	private Logger log = LogManager.getLogger();

	/**
	 * on construct, we fill the
	 * {@link de.twitter4serioussearch.analysis.AnalyzerMapping#mapping mapping}
	 */
	private AnalyzerMapping() {
		mapping.put("de", GermanAnalyzer.class);
		mapping.put("en", EnglishAnalyzer.class);
		mapping.put("fr", FrenchAnalyzer.class);
	}

	public static AnalyzerMapping getInstance() {
		return Holder.instance;
	}

	/**
	 * This class needs to be closed, because the
	 * {@link org.apache.lucene.analysis.Analyzer Analyzers} need to be closed.
	 *
	 * @throws Throwable
	 */
	public void close() throws Throwable {
		finalize();
	}

	@Override
	protected void finalize() throws Throwable {
		log.info("Finalizing Analyzer Mapping");
		for (String key : cache.keySet()) {
			cache.get(key).close();
			log.trace("Closed Analyzer " + cache.get(key));
		}
		ANALYZER_FOR_DELIMITER.close();
		log.trace("Closed ANALYZER_FOR_DELIMITER " + ANALYZER_FOR_DELIMITER);
		super.finalize();
	}

	/**
	 * @param languageCode
	 *            two character language code
	 * @return The appropriate {@link org.apache.lucene.analysis.Analyzer
	 *         Analyzer} as stated in
	 *         {@link de.twitter4serioussearch.analysis.AnalyzerMapping#mapping
	 *         mapping}. If the language code is not available in
	 *         {@link de.twitter4serioussearch.analysis.AnalyzerMapping#mapping
	 *         mapping}, returns the
	 *         {@link org.apache.lucene.analysis.de.GermanAnalyzer
	 *         GermanAnalyzer}.
	 */
	public Analyzer getAnalyzerForLanguage(String languageCode) {
		Analyzer analyzer = null;
		if (cache.get(languageCode) == null) {
			try {
				if (mapping.get(languageCode) == null) {
					if (cache.get("de") != null) {
						return cache.get("de");
					}
					analyzer = (Analyzer) Class.forName(
							GermanAnalyzer.class.getName()).newInstance();
					cache.put("de", analyzer);
				} else {
					analyzer = (Analyzer) Class.forName(
							mapping.get(languageCode).getName()).newInstance();
					cache.put(languageCode, analyzer);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return analyzer;
		} else {
			return cache.get(languageCode);
		}
	}

	/**
	 * @param textToAnalyze
	 *            The text you plan to analyze.
	 * @return The appropriate {@link org.apache.lucene.analysis.Analyzer
	 *         Analyzer} for the text you plan to analyze. The language code of
	 *         the textToAnalyze is determined by using the
	 *         {@link me.champeau.ld.UberLanguageDetector UberLanguageDetector}.
	 *         This language code is then used to call
	 *         {@link de.twitter4serioussearch.analysis.AnalyzerMapping#getAnalyzerForLanguage(String)
	 *         getAnalyzerForLanguage(languageCode)}.
	 */
	public Analyzer getAnalyzerForText(String textToAnalyze) {
		UberLanguageDetector detector = UberLanguageDetector.getInstance();
		return getAnalyzerForLanguage(detector.detectLang(textToAnalyze));
	}
}
