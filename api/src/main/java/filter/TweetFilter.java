package filter;

import twitter4j.Status;

public interface TweetFilter {
	public boolean tweetMatches(Status tweet);
}
