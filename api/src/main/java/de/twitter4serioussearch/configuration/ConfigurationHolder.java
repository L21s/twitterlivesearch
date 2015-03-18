package de.twitter4serioussearch.configuration;

import de.twitter4serioussearch.configuration.management.AbstractConfiguration;
import de.twitter4serioussearch.configuration.management.ConfigurationFactory;


public class ConfigurationHolder {
	private AbstractConfiguration currentConfiguration;
	
	private static ConfigurationHolder instance;
	
	private ConfigurationHolder() {}
	
	public static AbstractConfiguration getConfiguration() {
		if(instance == null) {
			instance = new ConfigurationHolder();
			instance.setCurrentConfiguration(ConfigurationFactory.createConfiguration());
		}
		
		return instance.currentConfiguration;
	}

	private void setCurrentConfiguration(AbstractConfiguration currentConfiguration) {
		this.currentConfiguration = currentConfiguration;
	}
}
