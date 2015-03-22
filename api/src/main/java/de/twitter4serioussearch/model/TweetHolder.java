package de.twitter4serioussearch.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.Status;

/**
 * This class is a simple wrapper around the list holding all the currently
 * buffered tweets by id. It does not keep track of the number of tweets in the
 * map and does not delete any tweets.
 *
 * @author tobiaslarscheid
 *
 */
public class TweetHolder {
	private List<Status> tweets = Collections
			.synchronizedList(new ArrayList<Status>());

	public List<Status> getTweets() {
		return tweets;
	}

	public Status getTweet(Integer id) {
		return tweets.get(id);
	}

	public Status getTweet(String id) {
		return tweets.get(Integer.parseInt(id));
	}

}
