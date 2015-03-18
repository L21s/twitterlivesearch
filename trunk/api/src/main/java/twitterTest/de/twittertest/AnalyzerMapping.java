package twitterTest.de.twittertest;

import java.util.HashMap;
import java.util.Map;

import me.champeau.ld.UberLanguageDetector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;

public class AnalyzerMapping {
	private static Map<String, Class<? extends Analyzer>> mapping = new HashMap<>();

	static {
		mapping.put("de", GermanAnalyzer.class);
		mapping.put("en", EnglishAnalyzer.class);
		mapping.put("fr", FrenchAnalyzer.class);
	}

	private static Map<String, Analyzer> cache = new HashMap<>();

	public static Analyzer getAnalyzerForLanguage(String languageCode) {
		Analyzer analyzer = null;
		if (cache.get(languageCode) == null) {
			try {
				if (mapping.get(languageCode) == null) {
					return (Analyzer) Class.forName(
							GermanAnalyzer.class.getName()).newInstance();
				}
				analyzer = (Analyzer) Class.forName(
						mapping.get(languageCode).getName()).newInstance();
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