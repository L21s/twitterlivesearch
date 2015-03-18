package de.twitter4serioussearch;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.IndexWriter;

import twitter4j.TwitterStream;

public class Twitter4Serioussearch {

	private IndexWriter iwriter;
	private KeywordHolder keywordHolder;
	private TwitterStream twitterStream;


	/**
	 * Registriert einen {@link de.twitter4serioussearch.TweetListener
	 * TweetListener} für die Kombination aus Query und Session
	 *
	 * @param query
	 *            Vom User gesuchter String
	 * @param sessionId
	 *            eindeutiger Session Identifier (Hintergrund: Die gleiche Query
	 *            kann von mehreren Usern registriert werden)
	 * @param actionListener
	 *            {@link de.twitter4serioussearch.TweetListener TweetListener}
	 *            der invoked wird, sobald ein zum query passender Tweet
	 *            empfangen wurde
	 */
	public void registerQuery(String query, String sessionId,
			TweetListener actionListener) {
		query = StringUtils.join(Tokenizer.getTokensForString(query), " ");
		keywordHolder.registerQuery(query, sessionId, actionListener);
		// TODO in persitenten Suchen
	}

	/**
	 * Deregistriert ein Query für die gegebene Session
	 *
	 * @param query
	 *            zu unregistrierendes Query
	 * @param sessionId
	 *            eindeutiger Session Identifier (Hintergrund: Die gleiche Query
	 *            kann von mehreren Usern registriert werden)
	 */
	public void unregisterQuery(String query, String sessionId) {
		query = StringUtils.join(Tokenizer.getTokensForString(query), " ");
		keywordHolder.unregisterQuery(query, sessionId);
	}

	/**
	 * Deregistriert alle querys für eine gegebene session
	 *
	 * @param sessionId
	 *            eindeutiger Session Identifier
	 */
	public void unregisterSession(String sessionId) {
		keywordHolder.unregisterSession(sessionId);
	}

	TwitterStream getTwitterStream() {
		return twitterStream;
	}

	void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}
	
	@Override
	protected void finalize() throws Throwable {
		twitterStream.clearListeners();
		twitterStream.cleanUp();
		twitterStream.shutdown();
		iwriter.close();
		super.finalize();
	}
}