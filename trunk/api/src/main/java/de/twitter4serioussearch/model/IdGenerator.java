package de.twitter4serioussearch.model;

import de.twitter4serioussearch.api.configuration.ConfigurationHolder;

/**
 * This class consecutively generates IDs for the buffered tweets in the range
 * between {@link java.lang.Integer#MIN_VALUE Integer.MIN_VALUE} and the
 * configured
 * {@link de.twitter4serioussearch.api.configuration.build.AbstractConfiguration#getMaxNumberOfTweets()
 * maxNumberOfTweets}
 *
 * @author tobiaslarscheid
 *
 */
public class IdGenerator {
	private static class Holder {
		static final IdGenerator INSTANCE = new IdGenerator(ConfigurationHolder
				.getConfiguration().getMaxNumberOfTweets());
	}

	private Integer id = Integer.MIN_VALUE;
	private Integer MAX_NUMBER_OF_TWEETS;

	public static IdGenerator getInstance() {
		return Holder.INSTANCE;
	}

	private IdGenerator(Integer maxNumOfTweet) {
		MAX_NUMBER_OF_TWEETS = maxNumOfTweet;
	}

	public synchronized int getNextId() {
		id++;
		return (id) % MAX_NUMBER_OF_TWEETS;
	}
}
