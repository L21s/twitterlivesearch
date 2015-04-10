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

/**
 * The default configuration. See this class if you want to know some default values.
 * @author schmitzhermes
 *
 */
public class DefaultConfiguration extends AbstractConfiguration{
	public DefaultConfiguration() {		
		setStreamConfig(StreamConfig.USER_STREAM);
		setDirectoryConfig(DirectoryConfig.RAM);
		setDirectory("/var/lucene/index");
		setMaxNumberOfTweets(50000);
		setDefaultOperator(Operator.AND);
	}
}
