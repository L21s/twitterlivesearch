package advancedAnalysis;

import de.twitter4serioussearch.TweetListener;
import twitter4j.Status;

public class MyTweetListener implements TweetListener {

	@Override
	public void handleNewTweet(Status tweet) {
		System.out.println("listener:" + tweet);
	}

}
