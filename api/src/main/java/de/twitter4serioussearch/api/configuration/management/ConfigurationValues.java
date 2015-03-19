package de.twitter4serioussearch.api.configuration.management;


/**
 * Alle Property-Values als statische Enums. <br />
 * <b>Die Values der Enums sind auch die Values, die in der Properties-File für die entsprechende Property zur Verfügung stehen.</b>
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
