package de.twitter4serioussearch.configuration.management;

/** 
 * Alle Keys der Property-File ({@link #PROPERTY_FILE}). 
 * @author schmitzhermes
 *
 */
public class ConfigurationKey {
	private static final String PREFIX = "twitter4serioussearch.";
	
	public static final String STREAM_CONFIG = PREFIX + "streamConfig";
	
	public static final String DIRECTORY_CONFIG = PREFIX + "directoryConfig";
	
	public static final String DIRECTORY = PREFIX + "directory";
	
	public static final String MAX_NUM_TWEETS = PREFIX + "maxNumberTweets";
}
