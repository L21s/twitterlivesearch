package de.twitter4serioussearch.filter;

import twitter4j.Status;

/**
 * an example implementation of a filter. <br />
 * This filter only allows tweets in German or English.
 * @author schmitzhermes
 *
 */
public class LanguageFilter implements TweetFilter {

	@Override
	public boolean tweetMatches(Status tweet) {
		if(tweet.getLang() == "DE" || tweet.getLang() == "EN") {
			return true;
		}
		return false;
	}

}
