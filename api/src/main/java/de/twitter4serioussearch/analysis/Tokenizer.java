package de.twitter4serioussearch.analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Helper class for tokenizing strings.
 *
 * @author tobiaslarscheid
 */
public class Tokenizer {
	private static Logger log = LogManager.getLogger();

	/**
	 * @param stringToAnalyze
	 *            String to be tokenized
	 * @param {@link org.apache.lucene.analysis.Analyzer Analyzer} to be used
	 *        for analysis
	 *
	 * @return list of tokens
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
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return tokens;
	}

	/**
	 * Tokenizes a string and uses the according to
	 * {@link de.twitter4serioussearch.analysis.AnalyzerMapping AnalyzerMapping}
	 * appropriate language specific {@link org.apache.lucene.analysis.Analyzer
	 * Analyzer}
	 *
	 * @param stringToAnalyze
	 *            String to be tokenized
	 * @return list of tokens
	 */
	public static List<String> getTokensForString(String stringToAnalyze) {
		if (log.isTraceEnabled()) {
			log.trace(stringToAnalyze
					+ " is going to be indexed with the follwing Analyzer: "
					+ AnalyzerMapping.getInstance().getAnalyzerForText(
							stringToAnalyze));
		}

		return getTokensForString(stringToAnalyze, AnalyzerMapping
				.getInstance().getAnalyzerForText(stringToAnalyze));

	}

	/**
	 * Tokenizes a string and uses the according to
	 * {@link de.twitter4serioussearch.analysis.AnalyzerMapping AnalyzerMapping}
	 * appropriate language specific {@link org.apache.lucene.analysis.Analyzer
	 * Analyzer} for the given language Code
	 *
	 * @param languageCode
	 *            two character ISO code of the language of the string
	 * @param stringToAnalyze
	 *            String to be tokenized
	 * @return list of tokens
	 */
	public static List<String> getTokensForString(String stringToAnalyze,
			String languageCode) {
		if (log.isTraceEnabled()) {
			log.trace(stringToAnalyze
					+ " is going to be indexed with the follwing analyzer: "
					+ AnalyzerMapping.getInstance().getAnalyzerForLanguage(
							languageCode));
		}

		return getTokensForString(stringToAnalyze, AnalyzerMapping
				.getInstance().getAnalyzerForLanguage(languageCode));

	}
}
