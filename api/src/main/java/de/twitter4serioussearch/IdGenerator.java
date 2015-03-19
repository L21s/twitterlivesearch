package de.twitter4serioussearch;

import de.twitter4serioussearch.configuration.ConfigurationHolder;

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
