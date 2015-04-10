package de.twitter4serioussearch.api;

import twitter4j.Status;

/**
 * This is the action listener you need to provide when registering a new query
 * with
 * {@link de.twitter4serioussearch.api.TwitterLiveSearch#registerQuery(String, String, TweetListener, de.twitter4serioussearch.filter.TweetFilter...)
 * TwitterLiveSearch#registerQuery}. It receives the
 * {@link twitter4j.Status} Object for any tweets matching your query.
 *
 * @author tobiaslarscheid
 *
 */
public interface TweetListener {
	public void handleNewTweet(Status tweet);
}
