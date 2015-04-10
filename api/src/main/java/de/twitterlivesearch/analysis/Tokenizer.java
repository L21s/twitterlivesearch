/*
 * Copyright 2015 Tobias Larscheid, Jan Schmitz-Hermes, Felix Nordhusen, Florian Scheil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.twitterlivesearch.analysis;

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
	 * {@link de.twitterlivesearch.analysis.AnalyzerMapping AnalyzerMapping}
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
	 * {@link de.twitterlivesearch.analysis.AnalyzerMapping AnalyzerMapping}
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
