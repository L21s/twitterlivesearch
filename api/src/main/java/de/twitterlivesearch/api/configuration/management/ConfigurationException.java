package de.twitterlivesearch.api.configuration.management;

/** 
 * Will be thrown in case a value to an existing property does not exist.
 * @see ConfigurationKey
 * @see ConfigurationValues
 * @author schmitzhermes
 *
 */
public class ConfigurationException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5767334559140627960L;
	
	public ConfigurationException(String s) {
		super(s);
	}
	
	public ConfigurationException(String s, Throwable t) {
		super(s,t);
	}

}
