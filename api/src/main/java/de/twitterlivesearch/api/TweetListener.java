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
package de.twitterlivesearch.api;

import twitter4j.Status;

/**
 * This is the action listener you need to provide when registering a new query
 * with
 * {@link de.twitterlivesearch.api.TwitterLiveSearch#registerQuery(String, String, TweetListener, de.twitterlivesearch.filter.TweetFilter...)
 * TwitterLiveSearch#registerQuery}. It receives the
 * {@link twitter4j.Status} Object for any tweets matching your query.
 *
 * @author tobiaslarscheid
 *
 */
public interface TweetListener {
	public void handleNewTweet(Status tweet);
}
