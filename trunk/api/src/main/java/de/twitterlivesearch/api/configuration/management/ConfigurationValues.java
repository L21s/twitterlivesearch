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
