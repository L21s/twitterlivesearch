package de.twitter4serioussearch.filter;

import twitter4j.Status;

public class LanguageFilter implements TweetFilter {

	@Override
	public boolean tweetMatches(Status tweet) {
		if(tweet.getLang() == "DE" || tweet.getLang() == "EN") {
			return true;
		}
		return false;
	}

}
