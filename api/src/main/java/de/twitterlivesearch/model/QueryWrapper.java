package de.twitterlivesearch.model;

import de.twitterlivesearch.api.TweetListener;
import de.twitterlivesearch.filter.TweetFilter;

/**
 * This class is used to hold a user-registered query string and all
 * corresponding data like the
 * {@link de.twitterlivesearch.api.TweetListener TweetListener}, any
 * {@link de.twitterlivesearch.filter.TweetFilter TweetFilters} and the
 * user-provided session identifier.
 *
 * @author tobiaslarscheid
 *
 */
public class QueryWrapper {
	private String session;

	private String query;

	private TweetListener listener;

	private TweetFilter[] filter;

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public TweetListener getListener() {
		return listener;
	}

	public void setListener(TweetListener listener) {
		this.listener = listener;
	}

	public TweetFilter[] getFilter() {
		return filter;
	}

	public void setFilter(TweetFilter[] filter) {
		this.filter = filter;
	}
}
