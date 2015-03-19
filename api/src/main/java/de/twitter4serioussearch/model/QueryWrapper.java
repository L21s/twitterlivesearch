package de.twitter4serioussearch.model;

import de.twitter4serioussearch.api.TweetListener;
import de.twitter4serioussearch.filter.TweetFilter;

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
