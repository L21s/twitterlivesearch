package de.twitter4serioussearch.api.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.twitter4serioussearch.api.configuration.build.AbstractConfiguration;


public class ConfigurationHolder {
	/**
	 * the current configuration
	 */
	private AbstractConfiguration currentConfiguration;
	
	/**
	 * the instance of this holder
	 */
	private static ConfigurationHolder instance;
	
	private static Logger log = LogManager.getLogger();
	
	/** 
	 * private due to singleton pattern
	 */
	private ConfigurationHolder() {}
	
	
	/**
	 * Returns the current configuration, which was set on startup. It is not possible to change the configuration during runtime.
	 * @return AbstractConfiguration the current configuration
	 */
	public static AbstractConfiguration getConfiguration() {
		if(instance == null) {
			IllegalStateException e = new IllegalStateException("There is no instance of the holder yet. You have to instantiate the holder first.");
			log.error("There is no instance of the holder yet. You have to instantiate the logger first.", e);
			throw e;
		}
		
		if(instance.currentConfiguration == null) {
			IllegalStateException e = new IllegalStateException("The configuration is not set yet. You have to set the configuration before you access it.");
			log.fatal("The configuration is not set yet. You have to set the configuration before you access it.", e);
			throw e;
		}
		
		return instance.currentConfiguration;
	}
	
	/**
	 * Sets the configuration once.
	 * @param config the configuration
	 * @throws IllegalStateException if the configuration is already set
	 */
	public static void setConfiguration(AbstractConfiguration config) {
		if(instance == null) {
			instance = new ConfigurationHolder();
			log.trace("ConfigurationHolder initialized.");
		}
		
		if(instance.currentConfiguration != null) {
			IllegalStateException e = new IllegalStateException("The configuration of the holder can only be set once and is immutable.");
			log.error("The configuration of the holder can only be set once and is immutable.", e);
			throw e;
		}
		
		log.trace("Configuration is set.");
		instance.currentConfiguration = config;
	}
}
