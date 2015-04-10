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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

import de.twitterlivesearch.api.configuration.ConfigurationHolder;
import de.twitterlivesearch.api.configuration.build.AbstractConfiguration;

/**
 * This class is used to search the lucene index.
 *
 * @author tobiaslarscheid
 *
 */
public class Searcher {
	private static Logger log = LogManager.getLogger();
	private Directory directory;

	public Searcher(Directory directory) {
		this.directory = directory;
	}

	/**
	 * This is the same as
	 * {@link de.twitterlivesearch.analysis.Searcher#searchForTweets(String)
	 * searchForTweets(String)}, but the search is limited to the tweet with the
	 * given id. This can for example be used to analyze the latest incoming
	 * tweet.
	 *
	 * @param id
	 * @param queryString
	 * @return
	 */
	public List<Document> searchForTweets(Integer id, String queryString) {
		if(queryString.isEmpty()) {
			return Collections.emptyList();
		}
		
		AbstractConfiguration config = ConfigurationHolder.getConfiguration();
		try {
			if (!DirectoryReader.indexExists(directory)) {
				return null;
			}
		} catch (IOException e) {
			log.fatal("Error when trying to check if directory exists!", e);
			return new ArrayList<>();
		}
		DirectoryReader ireader;
		try {
			ireader = DirectoryReader.open(directory);
		} catch (IOException e) {
			log.fatal("Error when trying to open directory!", e);
			return null;
		}

		IndexSearcher isearcher = new IndexSearcher(ireader);
		Query textQuery = null;
		QueryParser parser = new QueryParser(FieldNames.TEXT.getField(),
				AnalyzerMapping.getInstance().ANALYZER_FOR_DELIMITER);
		parser.setDefaultOperator(config.getDefaultOperator());
		BooleanQuery query = new BooleanQuery();
		try {
			textQuery = parser.parse(queryString);
		} catch (ParseException e) {
			log.fatal("Error while parsing query: " + queryString, e);
		}

		// if id does not equal null only the query with the given id will be
		// searched
		// this can be used to search the latest element only
		if (id != null) {
			Query idQuery = NumericRangeQuery.newIntRange(
					FieldNames.ID.getField(), id.intValue(), id.intValue(),
					true, true);
			query.add(idQuery, Occur.MUST);
		}
		query.add(textQuery, Occur.MUST);
		ScoreDoc[] hits = null;
		try {
			hits = isearcher.search(query, 1000).scoreDocs;
		} catch (IOException e) {
			log.fatal("Error while trying to search!", e);
		}
		List<Document> result = new ArrayList<>();
		for (int i = 0; i < hits.length; i++) {
			try {
				result.add(isearcher.doc(hits[i].doc));
				log.info("Found result for query \"" + queryString + "\".");
			} catch (IOException e) {
				log.fatal("Error when getting document!", e);
			}
		}
		return result;
	}

	/**
	 * Search for a tweet in the lucene index. The query you provide must
	 * already be tokenized and the tokens must be seperated by the correct
	 * {@link de.twitterlivesearch.analysis.AnalyzerMapping#TOKEN_DELIMITER
	 * token delimiter}. The tokens are then linked by the
	 * {@link de.twitterlivesearch.api.configuration.build.AbstractConfiguration#getDefaultOperator()
	 * default operator} as provided in your configuration.
	 *
	 * @param queryString
	 *            The tokenized query to search for.
	 * @return A list of matching lucene documents, empty collection if nothing
	 *         found.
	 */
	public List<Document> searchForTweets(String queryString) {
		if(queryString.isEmpty()) {
			return Collections.emptyList();
		}
		
		return searchForTweets(null, queryString);
	}
}
