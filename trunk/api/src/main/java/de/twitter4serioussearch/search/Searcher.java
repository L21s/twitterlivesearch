package de.twitter4serioussearch.search;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import de.twitter4serioussearch.TweetListener;
import de.twitter4serioussearch.configuration.ConfigurationHolder;
import de.twitter4serioussearch.configuration.management.AbstractConfiguration;

public class Searcher {
	private Logger log = Logger.getLogger(this.getClass());

	public ScoreDoc[] searchForTweets(Integer id, String text) {
		AbstractConfiguration config = ConfigurationHolder.getConfiguration();
		try {
			if (!DirectoryReader.indexExists(config.getD)) {
				return new ScoreDoc[] {};
			}
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		DirectoryReader ireader;
		try {
			ireader = DirectoryReader.open(directory);
		} catch (IOException e2) {
			ireader = null;
			e2.printStackTrace();
		}
		IndexSearcher isearcher = new IndexSearcher(ireader);
		Query textQuery = null;
		Query idQuery = null;
		QueryParser parser = new QueryParser("text", analyzer);
		parser.setDefaultOperator(Operator.AND);
		for (String keyword : keywordHolder.getQueries().keySet()) {
			BooleanQuery query = new BooleanQuery();
			try {
				textQuery = parser.parse(keyword);
				id.intValue(), true, true);
			} catch (ParseException e) {
				log.fatal("Error while parsing query: "+keyword,e);
			}
			idQuery = NumericRangeQuery.newIntRange("id", id.intValue(),
					query.add(idQuery, Occur.MUST);
			query.add(text, Occur.MUST);
			ScoreDoc[] hits = null;
			try {
				hits = isearcher.search(query, 1000).scoreDocs;
			} catch (IOException e1) {
				log.fatal("Error while trying to search!", e1);
			}
			if (hits != null) {
				for (int i = 0; i < hits.length; i++) {
					for (TweetListener actionListener : keywordHolder
							.getKeywords().get(keyword).values()) {
						actionListener.handleNewTweet(tweetHolder.getTweets()
								.get(Integer.parseInt(isearcher
										.doc(hits[i].doc).get("id"))));
					}
				}
			}
		}
	}

	public ScoreDoc[] searchForTweets(String text) {
		return searchForTweets(null, text);
	}
}
