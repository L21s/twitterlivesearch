package de.twitter4serioussearch.api.configuration.build;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;

import de.twitter4serioussearch.api.configuration.ConfigurationHolder;
import de.twitter4serioussearch.api.configuration.management.ConfigurationException;
import de.twitter4serioussearch.api.configuration.management.ConfigurationKey;
import de.twitter4serioussearch.api.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitter4serioussearch.api.configuration.management.ConfigurationValues.StreamConfig;
import de.twitter4serioussearch.filter.TweetFilter;

/**
 * Is used to create a configuration according to a properties file or a
 * programmatically specified configuration. To specify a custom configuration
 * programmatically plase use the {@link ConfigurationBuilder}. <br />
 * The properties file should be provided using the following name:
 * <em>twitter4serioussearch.properties</em> (see: {@link #PROPERTY_FILE}). <br/>
 * <br />
 * There is a suitable default configuration ({@link DefaultConfiguration}),
 * whose values are overriden in case you specify another value either in the
 * properties-File or in the custom configuration.
 * 
 * @author schmitzhermes
 *
 */
public class ConfigurationFactory {
	public static final String PROPERTY_FILE = "twitter4serioussearch.properties";

	private static Logger log = LogManager.getLogger();

	private ConfigurationFactory() {
	}

	/**
	 * Creates the default configuration according to the properties file. <br />
	 * Default Values are overridden.
	 * 
	 * @return the configuration
	 */
	public static void createConfiguration() {
		AbstractConfiguration defaultConfig = new DefaultConfiguration();
		createConfiguration(readConfigurationFromFile(defaultConfig),
				defaultConfig);
	}

	/**
	 * Creates the default configuration according to a Map. <br />
	 * Values in the properties file are ignored. <br />
	 * Default Values are overridden.
	 * 
	 * @param properties
	 *            the properties as a Map
	 * @return the configuration
	 */
	public static void createConfiguration(Map<String, String> properties) {
		AbstractConfiguration defaultConfig = new DefaultConfiguration();
		createConfiguration(properties, defaultConfig);
	}

	/**
	 * Creates the default configuration according to the AbstractConfiguration. <br />
	 * Values of this configuration are overriden by the properties File. So be
	 * sure you delete the entries in the properties file.
	 * 
	 * @return the configuration
	 */
	public static void createConfiguration(AbstractConfiguration config) {
		createConfiguration(readConfigurationFromFile(config), config);
	}

	/**
	 * creates the configuration according to a specified custom configuration
	 * and a Map. <br />
	 * Values of the Map have the priority. This means, if you decide to provide
	 * different values to the same key in properties file and configuration,
	 * the value of the configuration will be ignored.
	 * 
	 * @param properties
	 *            the properties as Map
	 * @param config
	 *            as custom configuration
	 */
	public static void createConfiguration(Map<String, String> properties,
			AbstractConfiguration config) {
		updateConfiguration(config, properties);
		ConfigurationHolder.setConfiguration(config); // sets the configuration
														// in the holder -> the
														// API can access it now
	}

	/**
	 * updates the configuration accroding to the specified Map.
	 * 
	 * @param config
	 * @param properties
	 */
	private static void updateConfiguration(AbstractConfiguration config,
			Map<String, String> properties) {
		String value;
		Integer modifiedProperties = 0;
		if ((value = properties.get(ConfigurationKey.STREAM_CONFIG)) != null) {
			try {
				config.setStreamConfig(StreamConfig.valueOf(StreamConfig.class,
						value));
				log.trace(ConfigurationKey.STREAM_CONFIG + ": " + value);
				modifiedProperties++;
			} catch (IllegalArgumentException e) {
				throw buildConfigValueException(value, StreamConfig.class,
						ConfigurationKey.STREAM_CONFIG, e);
			}
		}

		if ((value = properties.get(ConfigurationKey.DIRECTORY_CONFIG)) != null) {
			try {
				config.setDirectoryConfig(DirectoryConfig.valueOf(
						DirectoryConfig.class, value));
				log.trace(ConfigurationKey.DIRECTORY_CONFIG + ": " + value);
				modifiedProperties++;
			} catch (IllegalArgumentException e) {
				throw buildConfigValueException(value, DirectoryConfig.class,
						ConfigurationKey.DIRECTORY_CONFIG, e);
			}
		}

		if ((value = properties.get(ConfigurationKey.DIRECTORY)) != null) {
			if (config.getDirectoryConfig() != DirectoryConfig.FS_DIRECTORY) {
				log.warn("You are trying to set an explicit directory for Lucene, but it seems that your configuration uses the RAM directory. The configuration value will be ignored.");
			}
			log.trace(ConfigurationKey.DIRECTORY + ": " + value);
			config.setDirectory(value);
			modifiedProperties++;
		}

		if ((value = properties.get(ConfigurationKey.MAX_NUM_TWEETS)) != null) {
			log.trace(ConfigurationKey.MAX_NUM_TWEETS + ": " + value);
			try {
				config.setMaxNumberOfTweets(Integer.parseInt(value));
				modifiedProperties++;
			} catch (NumberFormatException e) {
				log.error(
						"You tried to set the maximum number of tweets, but the key was not a number. Please only assign numbers to this property.",
						e);
			}
		}

		if ((value = properties.get(ConfigurationKey.DEFAULT_OPERATOR)) != null) {
			try {
				config.setDefaultOperator(Operator.valueOf(Operator.class,
						value));
				log.trace(ConfigurationKey.DEFAULT_OPERATOR + ": " + value);
				modifiedProperties++;
			} catch (IllegalArgumentException e) {
				throw buildConfigValueException(value, Operator.class,
						ConfigurationKey.DEFAULT_OPERATOR, e);
			}

		}

		if ((value = properties.get(ConfigurationKey.GLOBAL_FILTERS)) != null) {
			String[] fqClassNames = value.replace(" ", "").split(",");
			List<TweetFilter> tweetFilters = new ArrayList<TweetFilter>();
			for (int i = 0; i < fqClassNames.length; i++) {
				try {
					tweetFilters.add((TweetFilter) Class.forName(
							fqClassNames[i]).newInstance());
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e) {
					ConfigurationException ce = new ConfigurationException(
							"Could not load filter " + fqClassNames[i] + ".", e);
					log.fatal("Could not load filter " + fqClassNames[i] + ".",
							ce);
					throw ce;
				}

				if (log.isTraceEnabled()) {
					log.trace("Added Filter: "
							+ ConfigurationKey.GLOBAL_FILTERS + ": "
							+ fqClassNames[i]);
				}
			}

			config.setFilters(tweetFilters.toArray(new TweetFilter[0]));
			modifiedProperties++;

		}

		if (modifiedProperties != properties.size()) {
			log.warn("You only modified "
					+ modifiedProperties
					+ " propertie(s), but your Properties-File contains "
					+ properties.size()
					+ " properties. It seems as if you are trying to set another property, "
					+ "which was ignored because it is not known by the system. "
					+ "Check your properties-file in case something does not work as expected.");
		}
	}

	/**
	 * reads the property file and returns its values as a HashMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, String> readConfigurationFromFile(
			AbstractConfiguration config) {
		Properties properties = new Properties();
		InputStream inputStream = ConfigurationFactory.class.getClassLoader()
				.getResourceAsStream(PROPERTY_FILE);

		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new HashMap<String, String>((Map) properties);
	}

	/**
	 * Creates a meaningful configuration exception.
	 * 
	 * @param actualValue
	 *            the user-provided value
	 * @param valueEnum
	 *            the enum of possible values
	 * @param property
	 *            the mentioned property
	 * @param cause
	 *            the root cause of the exception. Usually this is a
	 *            {@link IllegalArgumentException}, since the valueOf Method of
	 *            an enums throws this exception, if there is no such value.
	 * @return the meaningful exception
	 */
	private static ConfigurationException buildConfigValueException(
			String actualValue, Class<? extends Enum<?>> valueEnum,
			String property, Throwable cause) {
		StringBuilder s = new StringBuilder();
		s.append("The value ");
		s.append(actualValue);
		s.append(" of the property ");
		s.append(property);
		s.append(" is not valid. ");

		s.append("Valid properties are: ");
		s.append(Arrays.toString(valueEnum.getEnumConstants()));

		log.error(s.toString(), cause);
		return new ConfigurationException(s.toString(), cause);
	}
}
