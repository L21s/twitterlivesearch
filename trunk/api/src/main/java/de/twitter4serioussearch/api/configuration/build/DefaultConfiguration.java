package de.twitter4serioussearch.api.configuration.build;

import org.apache.lucene.queryparser.classic.QueryParser.Operator;

import de.twitter4serioussearch.api.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitter4serioussearch.api.configuration.management.ConfigurationValues.StreamConfig;

/**
 * 
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
