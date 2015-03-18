package configuration;

import configuration.management.AbstractConfiguration;
import configuration.management.ConfigurationFactory;


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
