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

import de.twitterlivesearch.api.configuration.management.ConfigurationKey;
import de.twitterlivesearch.api.configuration.management.ConfigurationValues;
import de.twitterlivesearch.api.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitterlivesearch.api.configuration.management.ConfigurationValues.StreamConfig;
import de.twitterlivesearch.filter.TweetFilter;


/**
 * Abstract configuration class. All configurations may inherit from this class. <br />
 * You can find the configuration default values in the JavaDoc. They are <em>italic.</em> <br />
 * This object and all of its children are immutable. Please use {@link ConfigurationBuilder} to <br />
 * build a new configuration.
 * @author schmitzhermes
 *
 */
public abstract class AbstractConfiguration {
	
	/**
	 * Configures, whether Twitter4J should listen to a user-stream (specified account in twitter4j.properties) or to the gardenhose. <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.streamConfig (see: {@link ConfigurationKey#STREAM_CONFIG}) <br />
	 * <b>Property-Values:</b> <em>USER_STREAM</em> | GARDENHOSE (see: {@link ConfigurationValues.StreamConfig}) <br />
	 * <b>Default:</b> USER_STREAM
	 */
	private StreamConfig streamConfig;
	
	/**
	 * Configures, whether the RAM-Directory or a File-Directory should be used to store the Lucene Index. <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.directoryConfig (siehe: {@link ConfigurationKey#DIRECTORY_CONFIG}) <br />
	 * <b>Property-Values:</b> <em>RAM</em> | FS_DIRECTORY (siehe: {@link ConfigurationValues.DirectoryConfig}) <br />
	 * <b>Default:</b> RAM
	 */
	private DirectoryConfig directoryConfig;
	
	/**
	 * Specifies the directory of the Lucene Index (in case you chose File-Directory in {@link #streamConfig}). <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.directory (see: {@link ConfigurationKey#DIRECTORY}) <br />
	 * <b>Property-Values:</b> ein Verzeichnis 
	 * <b>Default:</b> /var/lucene/index
	 */
	private String directory;
	
	/**
	 * Maximum amount of tweets that is stored in the buffer.  <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.maxNumberOfTweets (siehe: {@link ConfigurationKey#MAX_NUM_TWEETS}) <br />
	 * <b>Property-Values:</b> Zahl
	 * <b>Default:</b> 50000
	 */
	private Integer maxNumberOfTweets;
	
	/**
	 * The default operator which is used to connect tokens in query string. <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.defaultOperator (siehe: {@link ConfigurationKey#DEFAULT_OPERATOR}) <br />
	 * <b>Property-Values:</b> <em>AND</em> | OR
	 * <b>Default:</b> AND
	 */
	private Operator defaultOperator;
	
	
	private TweetFilter[] filters;

	public StreamConfig getStreamConfig() {
		return streamConfig;
	}	

	void setStreamConfig(StreamConfig streamConfig) {
		this.streamConfig = streamConfig;
	}

	public DirectoryConfig getDirectoryConfig() {
		return directoryConfig;
	}

	void setDirectoryConfig(DirectoryConfig directoryConfig) {
		this.directoryConfig = directoryConfig;
	}

	public String getDirectory() {
		return directory;
	}

	void setDirectory(String directory) {
		this.directory = directory;
	}

	public Integer getMaxNumberOfTweets() {
		return maxNumberOfTweets;
	}

	void setMaxNumberOfTweets(Integer maxNumberTweets) {
		this.maxNumberOfTweets = maxNumberTweets;
	}

	public Operator getDefaultOperator() {
		return defaultOperator;
	}

	void setDefaultOperator(Operator defaultOperator) {
		this.defaultOperator = defaultOperator;
	}

	public TweetFilter[] getFilters() {
		return filters;
	}

	void setFilters(TweetFilter[] filters) {
		this.filters = filters;
	}

}


