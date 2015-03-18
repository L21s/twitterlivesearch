package de.twitter4serioussearch;

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
 * Hilfsklasse, um den passenden language specific
 * {@link org.apache.lucene.analysis.Analyzer Analyzer} zu ermitteln
 *
 * @author tobiaslarscheid
 *
 */
public class AnalyzerMapping {
	private final static Map<String, Class<? extends Analyzer>> mapping = new HashMap<String, Class<? extends Analyzer>>();
	public final static String TOKEN_DELIMITER = " ";
	public final static Analyzer ANALYZER_FOR_DELIMITER = new WhitespaceAnalyzer();
	private final static Map<String, Analyzer> cache = new HashMap<String, Analyzer>();
	private static Logger log = LogManager.getLogger();
	
	static {
		try {
			mapping.put("de", GermanAnalyzer.class);
			mapping.put("en", EnglishAnalyzer.class);
			mapping.put("fr", FrenchAnalyzer.class);
		} catch(Exception e) {
			log.error("Error in initializer", e);
		}
		
	}
	

	public static Analyzer getAnalyzerForLanguage(String languageCode) {
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

	public static Analyzer getAnalyzerForText(String textToAnalyze) {
		UberLanguageDetector detector = UberLanguageDetector.getInstance();
		return getAnalyzerForLanguage(detector.detectLang(textToAnalyze));
	}
}
