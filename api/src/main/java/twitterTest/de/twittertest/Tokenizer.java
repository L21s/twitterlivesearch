package twitterTest.de.twittertest;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class Tokenizer {
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
			// not thrown b/c we're using a string reader...
			throw new RuntimeException(e);
		}
		return tokens;
	}

	public static List<String> getTokensForString(String stringToAnalyze) {
		return getTokensForString(stringToAnalyze,
				AnalyzerMapping.getAnalyzerForText(stringToAnalyze));
	}

	public static List<String> getTokensForString(String stringToAnalyze,
			String languageCode) {
		return getTokensForString(stringToAnalyze,
				AnalyzerMapping.getAnalyzerForLanguage(languageCode));
	}
}
