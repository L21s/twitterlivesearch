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

import de.twitterlivesearch.api.configuration.ConfigurationHolder;

/**
 * This class consecutively generates IDs for the buffered tweets in the range
 * between {@link java.lang.Integer#MIN_VALUE Integer.MIN_VALUE} and the
 * configured
 * {@link de.twitterlivesearch.api.configuration.build.AbstractConfiguration#getMaxNumberOfTweets()
 * maxNumberOfTweets}
 *
 * @author tobiaslarscheid
 *
 */
public class IdGenerator {
	private static class Holder {
		static final IdGenerator INSTANCE = new IdGenerator(ConfigurationHolder
				.getConfiguration().getMaxNumberOfTweets());
	}

	private Integer id = -1;
	private Integer MAX_NUMBER_OF_TWEETS;

	public static IdGenerator getInstance() {
		return Holder.INSTANCE;
	}

	private IdGenerator(Integer maxNumOfTweet) {
		MAX_NUMBER_OF_TWEETS = maxNumOfTweet;
	}

	public synchronized int getNextId() {
		id++;
		return (id) % MAX_NUMBER_OF_TWEETS;
	}
}
