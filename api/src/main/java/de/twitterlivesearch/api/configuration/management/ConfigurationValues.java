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

import de.twitterlivesearch.api.configuration.build.AbstractConfiguration;


/**
 * Custom property values as static enums. <br />
 * <b>The values of those enums are used as values in the property file as well. </b>
 * @author schmitzhermes
 *
 */
public class ConfigurationValues {
	
	/**
	 * Property-Key: twitter4serioussearch.streamConfig ({@link ConfigurationKey#STREAM_CONFIG}) <br />
	 * Eigenschaften: {@link AbstractConfiguration#getStreamConfig()}
	 *
	 */
	public static enum StreamConfig {
		USER_STREAM, GARDENHOSE
	}
	
	/**
	 * Property-Key: twitter4serioussearch.directoryConfig ({@link ConfigurationKey#DIRECTORY_CONFIG}) <br />
	 * Eigenschaften: {@link AbstractConfiguration#getDirectoryConfig()}
	 *
	 */
	public static enum DirectoryConfig {
		RAM, FS_DIRECTORY
	}
}
