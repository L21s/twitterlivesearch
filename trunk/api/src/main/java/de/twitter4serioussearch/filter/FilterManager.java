package de.twitter4serioussearch.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.Status;
import twitter4j.UserStreamListener;
import de.twitter4serioussearch.configuration.ConfigurationHolder;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.StreamConfig;

/** 
 * the manager class which handles application-wide filters. <br />
 * Filters are applied to all new tweets and to all search results. New tweets, which do not match the filter, are not added to the index. <br />
 * Search results, which do not match the filter, are excluded from the results passed to the client.
 * @author schmitzhermes
 *
 */
public class FilterManager {
	private static Logger log = LogManager.getLogger();
	/**
	 * global filters: every tweet has to match these filters
	 */
	private Set<TweetFilter> globalFilters = new HashSet<>();

	/**
	 * user stream filter: only a specific user-stream has to match these filters
	 */
	private Map<UserStreamListener, Set<TweetFilter>> userStreamFilter = new HashMap<>();
	
	private static FilterManager instance;
	
	private FilterManager() {}
	
	public static FilterManager getInstance() {
		if(instance == null) {
			instance = new FilterManager();
		}
		
		return instance;
	}

	/**
	 * Checks if the tweet passes all the criteria specified in the filters. <br />
	 * Caution: returns false if only <b>one</b> filter does not match the criteria. So be careful with the registered filters. There should not be filters who exlude each other, since the one who EXcludes always wins in this scenario.
	 * @param tweet the tweet to check
	 * @param listener the listener that invoked this method
	 * @return
	 */
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
	
	/**
	 * Convenient: only check the global filters
	 * @param tweet
	 * @return tweet the tweet to check
	 * @see #tweetMatchesFilter(Status, UserStreamListener)
	 */
	public boolean tweetMatchesFilter(Status tweet) {
		for (TweetFilter f : globalFilters) {
			if (!f.tweetMatches(tweet)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @return all registered global filters
	 */
	public Set<TweetFilter> getGlobalFilters() {
		return globalFilters;
	}
	
	/**
	 * @param listener the listener
	 * @return all tweet filters belonging to the listener
	 */
	public Set<TweetFilter> getUserStreamFilters(UserStreamListener listener) {
		return userStreamFilter.get(listener);
	}
	
	/**
	 * adds a global filter
	 * @param filter the filter to add
	 * @return true, if the filter was really added; false if it was already registered
	 */
	public boolean addGlobalFilter(TweetFilter filter) {
		if(log.isTraceEnabled()) {
			log.trace("Registered global Filter: " + filter.getClass().getSimpleName());
		}
		return globalFilters.add(filter);
	}
	
	/** 
	 * removes global filter
	 * @param filter the filter to remove
	 * @return true if a filter was removed, false if it was not registered in the first place
	 */
	public boolean removeGlobalFilter(TweetFilter filter) {
		return globalFilters.remove(filter);
	}
	
	/**
	 * clears all registered global filters
	 */
	public void clearGlobalFilters() {
		globalFilters.clear();
	}
	
	/**
	 * Adds a User-Stream- specific filter
	 * @param listener the listener the filter should be added to
	 * @param filter the filter to add
	 * @return true if the filter was really added, false if it was already registered
	 */
	public boolean addUserStreamFilter(UserStreamListener listener, TweetFilter filter) {
		if(ConfigurationHolder.getConfiguration().getStreamConfig() != StreamConfig.USER_STREAM) {
			throw new IllegalArgumentException("Es kann kein User-Stream-Listener hinzugefügt werden, wenn die Gardenhose aktiv ist.");
		}
		
		if(userStreamFilter.get(listener) == null) {
			userStreamFilter.put(listener, new HashSet<TweetFilter>());
		}
		
		if(log.isTraceEnabled()) {
			log.trace("Registered userStreamFilter Filter: " + filter.getClass().getSimpleName() + " on " + filter.getClass().getSimpleName());
		}
		return userStreamFilter.get(listener).add(filter);
	}
	
	/**
	 * Removes a User-Stream- specific filter
	 * @param listener listener the filter should be removed from
	 * @param filter the filter to remove
	 * @return true if a filter was removed, false if it was not registered in the first place
	 */
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
