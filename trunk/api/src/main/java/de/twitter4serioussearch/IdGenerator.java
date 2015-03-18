package de.twitter4serioussearch;

public class IdGenerator {
	private Integer id = Integer.MIN_VALUE;
	private Integer MAX_NUMBER_OF_TWEETS;

	public IdGenerator(Integer maxNumberOfTweets) {
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
