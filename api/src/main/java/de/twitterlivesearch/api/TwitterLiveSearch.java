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
package de.twitterlivesearch.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;

import twitter4j.Status;
import twitter4j.TwitterStream;
import de.twitterlivesearch.analysis.AnalyzerMapping;
import de.twitterlivesearch.analysis.FieldNames;
import de.twitterlivesearch.analysis.Searcher;
import de.twitterlivesearch.analysis.Tokenizer;
import de.twitterlivesearch.common.CollectionsUtil;
import de.twitterlivesearch.filter.TweetFilter;
import de.twitterlivesearch.model.IdGenerator;
import de.twitterlivesearch.model.QueryManager;
import de.twitterlivesearch.model.TweetHolder;

/**
 * This is the single point of contact with the twitter4serioussearch library.
 * Please use the
 * {@link de.twitterlivesearch.api.TwitterLiveSearchFactory
 * TwitterLiveSearchFactory} to create an instance of this class. This class
 * is meant to be immutable. <br />
 * In case you are in a Java EE environment, it is best
 * practice to wrap your instance of TwitterLiveSearch in a CDI Singleton.
 *
 * @author tobiaslarscheid
 *
 */
public class TwitterLiveSearch {

	/**
	 * Lucene Index Writer. Is used to store documents in the index.
	 */
	private IndexWriter iwriter;

	/**
	 * the twitter-stream. It is a twitter4j implementation
	 */
	private TwitterStream twitterStream;

	/**
	 * the specified directory.
	 */
	private Directory currentDirectory;

	/**
	 * the tweet-Holder. A class which holds the entire {@link Status} objects.
	 */
	private TweetHolder tweetHolder;

	/**
	 * is used to generate continuous ids for the index and the tweet holder.
	 */
	private IdGenerator idGenerator;

	/**
	 * searches through the entire index for tweets
	 */
	private Searcher searcher;

	private static Logger log = LogManager.getLogger();

	/**
	 * Registers a {@link de.twitterlivesearch.api.TweetListener
	 * TweetListener}for the combination of query and sessionId
	 *
	 * @param query
	 *            the query as provided by the user
	 * @param sessionId
	 *            unique session identifier (reason: the same query can be
	 *            registered by multiple users)
	 * @param actionListener
	 *            {@link de.twitterlivesearch.api.TweetListener
	 *            TweetListener} which is invoked whenever a new tweet matching
	 *            the users query is received
	 */
	public void registerQuery(String query, String sessionId,
			TweetListener actionListener, TweetFilter... filter) {
		query = StringUtils.join(Tokenizer.getTokensForString(query), " ");
		QueryManager.getInstance().registerQuery(query, sessionId,
				actionListener, filter);
		List<Document> documents = searcher.searchForTweets(query);
		for (Document document : CollectionsUtil.safe(documents)) {
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

	void setIndexWriter(IndexWriter iwriter) {
		this.iwriter = iwriter;
	}

	public Directory getCurrentDirectory() {
		return currentDirectory;
	}

	void setCurrentDirectory(Directory currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	public TweetHolder getTweetHolder() {
		return tweetHolder;
	}

	void setTweetHolder(TweetHolder tweetHolder) {
		this.tweetHolder = tweetHolder;
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public Searcher getSearcher() {
		return searcher;
	}

	void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}

	void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public void close() throws Throwable {
		finalize();
	}
}
