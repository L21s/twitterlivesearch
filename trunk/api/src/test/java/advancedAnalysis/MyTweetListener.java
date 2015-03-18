package advancedAnalysis;

import twitter4j.Status;
import twitterTest.de.twittertest.TweetListener;

public class MyTweetListener implements TweetListener {

	@Override
	public void handleNewTweet(Status tweet) {
		System.out.println("listener:" + tweet);
	}

}
