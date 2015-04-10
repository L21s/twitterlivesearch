package stuff;

import de.twitterlivesearch.api.TweetListener;
import twitter4j.Status;

public class MyTweetListener implements TweetListener {

	@Override
	public void handleNewTweet(Status tweet) {
		System.out.println("listener:" + tweet);
	}

}
