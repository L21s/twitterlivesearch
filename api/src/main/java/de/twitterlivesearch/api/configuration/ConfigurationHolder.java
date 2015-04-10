/*
 * Copyright 2015 Tobias Larscheid, Jan Schmitz-Hermes, Felix Nordhusen, Florian Scheil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.twitterlivesearch.api.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.twitterlivesearch.api.configuration.build.AbstractConfiguration;


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
