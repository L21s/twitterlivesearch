package twitterTest.de.twittertest;

import java.util.HashMap;
import java.util.Map;

import twitter4j.Status;

public class TweetHolder {
	private Map<Integer, Status> tweets = new HashMap<Integer, Status>();

	public Map<Integer, Status> getTweets() {
		return tweets;
	}

}
