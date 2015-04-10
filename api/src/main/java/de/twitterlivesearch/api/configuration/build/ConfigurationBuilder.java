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
package de.twitterlivesearch.api.configuration.build;

import org.apache.lucene.queryparser.classic.QueryParser.Operator;

import de.twitterlivesearch.api.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitterlivesearch.api.configuration.management.ConfigurationValues.StreamConfig;
import de.twitterlivesearch.filter.TweetFilter;

/**
 * Can be used to build a new configuration. This class should be used to build
 * the configuration since {#link {@link AbstractConfiguration} and all of its
 * ancestors is immutable.
 * 
 * @author schmitzhermes
 *
 */
public class ConfigurationBuilder {
	private DefaultConfiguration config = new DefaultConfiguration();

	public ConfigurationBuilder setStreamConfig(StreamConfig streamConfig) {
		config.setStreamConfig(streamConfig);
		return this;
	}

	public ConfigurationBuilder setDirectoryConfig(
			DirectoryConfig directoryConfig) {
		config.setDirectoryConfig(directoryConfig);
		return this;
	}

	public ConfigurationBuilder setDirectory(String directory) {
		config.setDirectory(directory);
		return this;
	}

	public ConfigurationBuilder setMaxNumberOfTweets(Integer maxNumberOfTweets) {
		config.setMaxNumberOfTweets(maxNumberOfTweets);
		return this;
	}

	public ConfigurationBuilder setDefaultOperator(Operator defaultOperator) {
		config.setDefaultOperator(defaultOperator);
		return this;
	}

	public ConfigurationBuilder setFilters(TweetFilter... filters) {
		config.setFilters(filters);
		return this;
	}

	public AbstractConfiguration build() {
		return config;
	}

}
