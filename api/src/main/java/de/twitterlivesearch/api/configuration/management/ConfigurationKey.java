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
package de.twitterlivesearch.api.configuration.management;

/** 
 * all keys of the properties file. ({@link #PROPERTY_FILE}). <br />
 * @author schmitzhermes
 *
 */
public class ConfigurationKey {
	private static final String PREFIX = "twitter4serioussearch.";
	
	public static final String STREAM_CONFIG = PREFIX + "streamConfig";
	
	public static final String DIRECTORY_CONFIG = PREFIX + "directoryConfig";
	
	public static final String DIRECTORY = PREFIX + "directory";
	
	public static final String MAX_NUM_TWEETS = PREFIX + "maxNumberOfTweets";
	
	public static final String DEFAULT_OPERATOR = PREFIX + "defaultOperator";
	
	public static final String GLOBAL_FILTERS = PREFIX + "globalFilters";
}
