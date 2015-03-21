package de.twitter4serioussearch.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import twitter4j.Status;

/**
 * This class is a simple wrapper around the map holding all the currently
 * buffered tweets by id. It does not keep track of the number of tweets in the
 * map and does not delete any tweets.
 *
 * @author tobiaslarscheid
 *
 */
public class TweetHolder {
	private Map<Integer, Status> tweets = Collections
			.synchronizedMap(new HashMap<Integer, Status>());

	public Map<Integer, Status> getTweets() {
		return tweets;
	}

	public Status getTweet(Integer id) {
		return tweets.get(id);
	}

	public Status getTweet(String id) {
		return tweets.get(Integer.parseInt(id));
	}

}
