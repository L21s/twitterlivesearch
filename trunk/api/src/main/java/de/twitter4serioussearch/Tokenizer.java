package de.twitter4serioussearch;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Hilfsklasse um Strings zu tokenisieren
 * 
 * @author tobiaslarscheid
 */
public class Tokenizer {
	/**
	 * @param stringToAnalyze
	 *            String der tokenisiert werden soll
	 * @param analyzer
	 *            Analyzer, der zur analyse verwendet werden soll
	 * @return Liste von tokens
	 */
	public static List<String> getTokensForString(String stringToAnalyze,
			Analyzer analyzer) {
		List<String> tokens = new ArrayList<String>();
		try {
			TokenStream stream = analyzer.tokenStream(null, new StringReader(
					stringToAnalyze));
			stream.reset();
			while (stream.incrementToken()) {
				tokens.add(stream.getAttribute(CharTermAttribute.class)
						.toString());
			}
			stream.end();
			stream.close();
			analyzer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return tokens;
	}

	/**
	 * Tokenisiert einen gegebenen String, ermittelt automatisch Sprache des
	 * Strings und verwendet den laut
	 * {@link de.twitter4serioussearch.AnalyzerMapping AnalyzerMapping}
	 * passenden language specific {@link org.apache.lucene.analysis.Analyzer
	 * Analyzer}
	 *
	 * @param stringToAnalyze
	 *            String der tokenisiert werden soll
	 * @return Liste von tokens
	 */
	public static List<String> getTokensForString(String stringToAnalyze) {
		return getTokensForString(stringToAnalyze,
				AnalyzerMapping.getAnalyzerForText(stringToAnalyze));
	}

	/**
	 * Tokenisiert einen gegebenen String, verwendet den laut
	 * {@link de.twitter4serioussearch.AnalyzerMapping AnalyzerMapping} zum
	 * gegebenen languageCode passenden language specific
	 * {@link org.apache.lucene.analysis.Analyzer Analyzer}
	 *
	 * @param languageCode
	 *            zweistelliger ISO Code der verwendeten Sprache
	 * @param stringToAnalyze
	 *            String der tokenisiert werden soll
	 * @return Liste von tokens
	 */
	public static List<String> getTokensForString(String stringToAnalyze,
			String languageCode) {
		return getTokensForString(stringToAnalyze,
				AnalyzerMapping.getAnalyzerForLanguage(languageCode));
	}
}
