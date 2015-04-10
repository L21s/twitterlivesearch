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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.Status;

/**
 * This class is a simple wrapper around the list holding all the currently
 * buffered tweets by id. It does not keep track of the number of tweets in the
 * map and does not delete any tweets.
 *
 * @author tobiaslarscheid
 *
 */
public class TweetHolder {
	private List<Status> tweets = Collections
			.synchronizedList(new ArrayList<Status>());

	public List<Status> getTweets() {
		return tweets;
	}

	public Status getTweet(Integer id) {
		return tweets.get(id);
	}

	public Status getTweet(String id) {
		return tweets.get(Integer.parseInt(id));
	}

}
