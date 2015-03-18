package twitterTest.de.twittertest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import twitter4j.Status;

public class TweetHolder {
	private Map<Integer, Status> tweets = Collections
			.synchronizedMap(new HashMap<Integer, Status>());

	// TODO löschen wenn voll
	public Map<Integer, Status> getTweets() {
		return tweets;
	}

}
