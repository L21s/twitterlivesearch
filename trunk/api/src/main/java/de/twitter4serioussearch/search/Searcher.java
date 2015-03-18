package de.twitter4serioussearch.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

import de.twitter4serioussearch.AnalyzerMapping;
import de.twitter4serioussearch.QueryHolder;
import de.twitter4serioussearch.TweetHolder;
import de.twitter4serioussearch.common.FieldNames;
import de.twitter4serioussearch.configuration.ConfigurationHolder;
import de.twitter4serioussearch.configuration.management.AbstractConfiguration;

public class Searcher {
	private Logger log = Logger.getLogger(this.getClass());
	private Directory directory;
	private QueryHolder queryHolder;
	private TweetHolder tweetHolder;

	public Searcher(Directory directory, QueryHolder queryHolder,
			TweetHolder tweetHolder) {
		this.directory = directory;
		this.queryHolder = queryHolder;
		this.tweetHolder = tweetHolder;
	}

	public List<Document> searchForTweets(Integer id, String text) {
		AbstractConfiguration config = ConfigurationHolder.getConfiguration();
		try {
			if (!DirectoryReader.indexExists(directory)) {

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
			return new ArrayList<>();
		}
		IndexSearcher isearcher = new IndexSearcher(ireader);
		Query textQuery = null;
		QueryParser parser = new QueryParser(FieldNames.TEXT.getField(),
				AnalyzerMapping.ANALYZER_FOR_DELIMITER);
		parser.setDefaultOperator(config.getDefaultOperator());
		BooleanQuery query = new BooleanQuery();
		try {
			textQuery = parser.parse(text);
		} catch (ParseException e) {
			log.fatal("Error while parsing query: " + text, e);
		}
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
			} catch (IOException e) {
				log.fatal("Error when getting document!", e);
			}
		}
		return result;
	}

	public List<Document> searchForTweets(String text) {
		return searchForTweets(null, text);
	}
}
