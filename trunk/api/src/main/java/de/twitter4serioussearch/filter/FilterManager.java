package de.twitter4serioussearch.filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.Status;
import twitter4j.UserStreamListener;

/** 
 * the manager class which handles application-wide filters. <br />
 * Filters are applied to all new tweets and to all search results. New tweets, which do not match the filter, are not added to the index. <br />
 * Search results, which do not match the filter, are excluded from the results passed to the client.
 * @author schmitzhermes
 *
 */
public class FilterManager {
	private static class Holder {
		static FilterManager instance = new FilterManager();
	}
	private static Logger log = LogManager.getLogger();
	/**
	 * global filters: every tweet has to match these filters
	 */
	private Set<TweetFilter> globalFilters = Collections.synchronizedSet(new HashSet<TweetFilter>());

	private FilterManager() {}
	
	public static FilterManager getInstance() {
		return Holder.instance;
	}

	/**
	 * Checks if the tweet passes all the criteria specified in the filters. <br />
	 * Caution: returns false if only <b>one</b> filter does not match the criteria. So be careful with the registered filters. There should not be filters who exlude each other, since the one who EXcludes always wins in this scenario.
	 * @param tweet the tweet to check
	 * @param listener the listener that invoked this method
	 * @return
	 */
	public static boolean tweetMatchesFilter(Status tweet, TweetFilter... filters) {
		for(int i = 0; i < filters.length; i++) {
			if(filters[i].tweetMatches(tweet)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Only check the global filters
	 * @param tweet
	 * @return tweet the tweet to check
	 * @see #tweetMatchesFilter(Status, UserStreamListener)
	 */
	public boolean tweetMatchesGlobalFilters(Status tweet) {
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

}
