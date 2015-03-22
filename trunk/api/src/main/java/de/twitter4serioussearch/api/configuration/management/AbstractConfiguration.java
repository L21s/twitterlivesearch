package de.twitter4serioussearch.api.configuration.management;

import org.apache.lucene.queryparser.classic.QueryParser.Operator;

import de.twitter4serioussearch.api.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitter4serioussearch.api.configuration.management.ConfigurationValues.StreamConfig;
import de.twitter4serioussearch.filter.TweetFilter;


/**
 * Abstrakte Konfigurationsklasse. Alle Konfigurationen sollen von dieser Klasse erben. <br />
 * Standardwerte sind im Java-Doc aufgeführt und <em>kursiv</em> gekennzeichnet. <br />
 * Das Objekt ist immutable. Es kann nur durch die {@link ConfigurationFactory} erzeugt werden. 
 * @author schmitzhermes
 *
 */
public abstract class AbstractConfiguration {
	
	/**
	 * Konfiguriert, ob es einen User-Account als Listener gibt oder die Gardenhose verwendet werden soll. <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.streamConfig (siehe: {@link ConfigurationKey#STREAM_CONFIG}) <br />
	 * <b>Property-Values:</b> <em>USER_STREAM</em> | GARDENHOSE (siehe: {@link ConfigurationValues.StreamConfig}) <br />
	 * <b>Default:</b> USER_STREAM
	 */
	private StreamConfig streamConfig;
	
	/**
	 * Konfiguriert, ob das RAM-Directory oder ein File-Directory von Lucene verwendet werden soll. <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.directoryConfig (siehe: {@link ConfigurationKey#DIRECTORY_CONFIG}) <br />
	 * <b>Property-Values:</b> <em>RAM</em> | FS_DIRECTORY (siehe: {@link ConfigurationValues.DirectoryConfig}) <br />
	 * <b>Default:</b> RAM
	 */
	private DirectoryConfig directoryConfig;
	
	/**
	 * Wenn Lucene mit einem Directory arbeiten soll, gibt diese Eigenschaft an, welches Directory es ist. <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.directory (siehe: {@link ConfigurationKey#DIRECTORY}) <br />
	 * <b>Property-Values:</b> ein Verzeichnis 
	 * <b>Default:</b> /var/lucene/index
	 */
	private String directory;
	
	/**
	 * Die maximale Anzahl von Tweets die im Buffer vorgehalten werden soll. <br /> <br />
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

	/**
	 * @return Aktuelle StreamConfig
	 * @see #streamConfig
	 */
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

