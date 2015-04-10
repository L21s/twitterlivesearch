/*
 * Copyright 2015 Tobias Larscheid, Jan Schmitz-Hermes, Felix Nordhusen, Florian Scheil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
