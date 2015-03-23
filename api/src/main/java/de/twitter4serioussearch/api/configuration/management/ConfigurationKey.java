package de.twitter4serioussearch.api.configuration.management;

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
