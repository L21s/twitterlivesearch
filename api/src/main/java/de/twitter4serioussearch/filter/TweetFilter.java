package de.twitter4serioussearch.filter;

import twitter4j.Status;

public interface TweetFilter {
	/**
	 * Decides whether the tweet matches the criteria or not.
	 * @param tweet the tweet to check against the criteria
	 * @return true if the tweet matches the criteria, false if not
	 */
	public boolean tweetMatches(Status tweet);
}
