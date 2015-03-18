package de.twitter4serioussearch.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import twitter4j.Status;
import twitter4j.UserStreamListener;
import de.twitter4serioussearch.configuration.ConfigurationHolder;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.StreamConfig;

public class FilterManager {
	private Set<TweetFilter> globalFilters = new HashSet<>();

	private Map<UserStreamListener, Set<TweetFilter>> userStreamFilter = new HashMap<>();

	public boolean tweetMatchesFilter(Status tweet, UserStreamListener listener) {
		for (TweetFilter f : globalFilters) {
			if (!f.tweetMatches(tweet)) {
				return false;
			}
		}

		if (ConfigurationHolder.getConfiguration().getStreamConfig() == StreamConfig.USER_STREAM) {
			if (userStreamFilter.get(listener) != null) {
				for (TweetFilter f : userStreamFilter.get(listener)) {
					if (!f.tweetMatches(tweet)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public Set<TweetFilter> getGlobalFilters() {
		return globalFilters;
	}
	
	public boolean addGlobalFilter(TweetFilter filter) {
		return globalFilters.add(filter);
	}
	
	public boolean removeGlobalFilter(TweetFilter filter) {
		return globalFilters.remove(filter);
	}
	
	public void clearGlobalFilters() {
		globalFilters.clear();
	}
	
	public boolean addUserStreamFilter(UserStreamListener listener, TweetFilter filter) {
		if(ConfigurationHolder.getConfiguration().getStreamConfig() != StreamConfig.USER_STREAM) {
			throw new IllegalArgumentException("Es kann kein User-Stream-Listener hinzugefügt werden, wenn die Gardenhose aktiv ist.");
		}
		
		if(userStreamFilter.get(listener) == null) {
			userStreamFilter.put(listener, new HashSet<TweetFilter>());
		}
		return userStreamFilter.get(listener).add(filter);
	}
	
	public boolean removeUserStreamFilter(UserStreamListener listener, TweetFilter filter) {
		if(ConfigurationHolder.getConfiguration().getStreamConfig() != StreamConfig.USER_STREAM) {
			throw new IllegalArgumentException("Es kann kein User-Stream-Listener gelöscht werden, wenn die Gardenhose aktiv ist.");
		}
		
		if(userStreamFilter.get(listener) == null) {
			return false;
		}
		
		return userStreamFilter.get(listener).remove(filter);
	}

}
