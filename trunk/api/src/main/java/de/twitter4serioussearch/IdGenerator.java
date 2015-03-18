package de.twitter4serioussearch;

import de.twitter4serioussearch.configuration.ConfigurationHolder;

public class IdGenerator {
	private Integer id = Integer.MIN_VALUE;
	private Integer MAX_NUMBER_OF_TWEETS;
	private static IdGenerator instance;

	public static IdGenerator getInstance() {
		if (instance == null) {
			instance = new IdGenerator(ConfigurationHolder.getConfiguration()
					.getMaxNumberOfTweets());
		}
		return instance;
	}

	private IdGenerator(Integer maxNumberOfTweets) {
		MAX_NUMBER_OF_TWEETS = maxNumberOfTweets;
	}

	public synchronized int getNextId() {
		id = (id + 1) % MAX_NUMBER_OF_TWEETS;
		return id - 1;
	}

	public synchronized int getIdToRemove() {
		return (id + 1) % MAX_NUMBER_OF_TWEETS;
	}
}
