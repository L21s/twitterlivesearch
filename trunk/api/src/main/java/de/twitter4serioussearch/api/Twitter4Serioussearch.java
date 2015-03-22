package de.twitter4serioussearch.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;

import twitter4j.TwitterStream;
import de.twitter4serioussearch.analysis.AnalyzerMapping;
import de.twitter4serioussearch.analysis.FieldNames;
import de.twitter4serioussearch.analysis.Searcher;
import de.twitter4serioussearch.analysis.Tokenizer;
import de.twitter4serioussearch.common.Util;
import de.twitter4serioussearch.filter.TweetFilter;
import de.twitter4serioussearch.model.IdGenerator;
import de.twitter4serioussearch.model.QueryManager;
import de.twitter4serioussearch.model.TweetHolder;

/**
 * This is the single point of contact with the twitter4serioussearch library.
 * Please use the
 * {@link de.twitter4serioussearch.api.Twitter4SerioussearchFactory
 * Twitter4SerioussearchFactory} to create an instance of this class. If you are
 * in a Java EE environment, it is best practice to wrap your instance of
 * Twitter4Serioussearch in a CDI Singleton.
 *
 * @author tobiaslarscheid
 *
 */
public class Twitter4Serioussearch {

	private IndexWriter iwriter;
	private TwitterStream twitterStream;
	private Directory currentDirectory;
	private TweetHolder tweetHolder;
	private IdGenerator idGenerator;
	private Searcher searcher;
	private static Logger log = LogManager.getLogger();

	/**
	 * Registers a {@link de.twitter4serioussearch.api.TweetListener
	 * TweetListener}for the combination of query and sessionId
	 *
	 * @param query
	 *            the query as provided by the user
	 * @param sessionId
	 *            unique session identifier (reason: the same query can be
	 *            registered by multiple users)
	 * @param actionListener
	 *            {@link de.twitter4serioussearch.api.TweetListener
	 *            TweetListener} which is invoked whenever a new tweet matching
	 *            the users query is received
	 */
	public void registerQuery(String query, String sessionId,
			TweetListener actionListener, TweetFilter... filter) {
		query = StringUtils.join(Tokenizer.getTokensForString(query), " ");
		QueryManager.getInstance().registerQuery(query, sessionId,
				actionListener, filter);
		List<Document> documents = searcher.searchForTweets(query);
		for (Document document : Util.safe(documents)) {
			actionListener.handleNewTweet(tweetHolder.getTweets().get(
					Integer.parseInt(document.get(FieldNames.ID.getField()))));
		}
	}

	/**
	 * Unregisters a query for the provided sessionId
	 *
	 * @param query
	 *            to unregister
	 * @param sessionId
	 *            unique session identifier (reason: the same query can be
	 *            registered by multiple users)
	 */
	public void unregisterQuery(String query, String sessionId) {
		query = StringUtils.join(Tokenizer.getTokensForString(query), " ");
		QueryManager.getInstance().unregisterQuery(query, sessionId);
	}

	/**
	 * Unregisters all queries for the provided sessionId
	 *
	 * @param sessionId
	 *            unique session identifier
	 */
	public void unregisterSession(String sessionId) {
		QueryManager.getInstance().unregisterSession(sessionId);
	}

	TwitterStream getTwitterStream() {
		return twitterStream;
	}

	void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public void close() throws Throwable {
		finalize();
	}

	@Override
	protected void finalize() throws Throwable {
		AnalyzerMapping.getInstance().close();
		twitterStream.clearListeners();
		twitterStream.cleanUp();
		twitterStream.shutdown();
		if (log.isInfoEnabled()) {
			log.info("Cleanup invoked: listeners are cleared, stream is cleaned up and shut down.");
		}
		iwriter.close();
		super.finalize();
	}

	public IndexWriter getIndexWriter() {
		return iwriter;
	}

	public void setIndexWriter(IndexWriter iwriter) {
		this.iwriter = iwriter;
	}

	public Directory getCurrentDirectory() {
		return currentDirectory;
	}

	public void setCurrentDirectory(Directory currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	public TweetHolder getTweetHolder() {
		return tweetHolder;
	}

	public void setTweetHolder(TweetHolder tweetHolder) {
		this.tweetHolder = tweetHolder;
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public Searcher getSearcher() {
		return searcher;
	}

	public void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}
}