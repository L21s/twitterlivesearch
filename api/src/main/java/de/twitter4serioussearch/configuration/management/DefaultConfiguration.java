package de.twitter4serioussearch.configuration.management;

import de.twitter4serioussearch.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.StreamConfig;

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
	}
}
