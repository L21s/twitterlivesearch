package configuration.management;

import configuration.management.ConfigurationValues.StreamConfig;

/**
 * 
 * @author schmitzhermes
 *
 */
public class DefaultConfiguration extends AbstractConfiguration{
	public DefaultConfiguration() {		
		setStreamConfig(StreamConfig.USER_STREAM);
	}
}
