package twitterTest.de.twittertest;

import twitter4j.Status;

public class MyTweetListener implements TweetListener {

	@Override
	public void handleNewTweet(Status tweet) {
		System.out.println("listener:" + tweet);
	}

}
