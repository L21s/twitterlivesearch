package twitterTest.de.twittertest;

import twitter4j.Status;

public interface TweetListener {
	public void handleNewTweet(Status tweet);
}
