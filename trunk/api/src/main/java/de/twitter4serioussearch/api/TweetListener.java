package de.twitter4serioussearch.api;

import twitter4j.Status;

public interface TweetListener {
	public void handleNewTweet(Status tweet);
}
