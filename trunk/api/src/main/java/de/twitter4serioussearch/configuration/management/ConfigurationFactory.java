package de.twitter4serioussearch.configuration.management;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;

import de.twitter4serioussearch.configuration.ConfigurationHolder;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.StreamConfig;

/**
 * Kreiert eine Konfiguration anhand einer Default-Konfiguration und der
 * Properties-File. Die Properties-File muss den Namen
 * <em>twitter4serioussearch.properties</em> (siehe: {@link #PROPERTY_FILE}). <br/>
 * Es existiert eine Standardkonfiguration ({@link DefaultConfiguration}), deren
 * Werte überschrieben werden, wenn eine entsprechende Property in der
 * Property-File existiert.
 * 
 * @author schmitzhermes
 *
 */
public class ConfigurationFactory {
	public static final String PROPERTY_FILE = "twitter4serioussearch.properties";

	private static Logger log = Logger.getLogger(ConfigurationFactory.class);

	private ConfigurationFactory() {
	}

	/**
	 * Erstellt die Konfiguration.
	 * 
	 * @return die Konfiguration anhand der Standardkonfiguration und den in der
	 *         Property-File ({@link #PROPERTY_FILE}) überschriebenen Werten.
	 */
	public static void createConfiguration() {
		AbstractConfiguration config = new DefaultConfiguration();
		updateConfiguration(config, readConfigurationFromFile(config));
		ConfigurationHolder.setConfiguration(config); // sets the configuration in the holder -> the API can access it now
	}
	
	/**
	 * Erstellt die Konfiguration anhand einer übergebenen Map.
	 * 
	 * @return die Konfiguration anhand der Standardkonfiguration und den in der
	 *         Property-File ({@link #PROPERTY_FILE}) überschriebenen Werten.
	 */
	public static void createConfiguration(Map<String, String> properties) {
		AbstractConfiguration config = new DefaultConfiguration();
		updateConfiguration(config, properties);
		ConfigurationHolder.setConfiguration(config); // sets the configuration in the holder -> the API can access it now
	}
	
	private static void updateConfiguration(AbstractConfiguration config, Map<String, String> properties) {
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
				config.setMaxNumberTweets(Integer.parseInt(value));
				modifiedProperties++;
			} catch(NumberFormatException e) {
				log.error("You tried to set the maximum number of tweets, but the key was not a number. Please only assign numbers to this property.", e);
			}
		}
		
		if ((value = properties.get(ConfigurationKey.DEFAULT_OPERATOR)) != null) {
			try {
				config.setDefaultOperator(Operator.valueOf(Operator.class, value));
				log.trace(ConfigurationKey.DEFAULT_OPERATOR + ": " + value);
				modifiedProperties++;
			} catch (IllegalArgumentException e) {
				throw buildConfigValueException(value, Operator.class, ConfigurationKey.DEFAULT_OPERATOR, e);
			}

		}

		if (modifiedProperties != properties.size()) {
			log.warn("You have only modified "
					+ modifiedProperties
					+ " properties, but your Properties-File contains "
					+ properties.size()
					+ " properties. It seems as if you were trying to set another property, which was ignored because it is not known by the system. Check your properties-file in case something does not work as expected.");
		}
	}

	/**
	 * Liest die Konfiguration aus der Properties-File ({@link #PROPERTY_FILE})
	 * und passt die übergebene Konfiguration entsprechend an.
	 * (Call-by-reference)
	 * 
	 * @param config
	 *            die Konfiguration vor dem Einlesen der Properties-File (
	 *            {@link #PROPERTY_FILE})
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, String> readConfigurationFromFile(AbstractConfiguration config) {
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
	 * Erstellt eine sprechende Konfigurationsexception.
	 * 
	 * @param actualValue
	 *            die vom Nutzer eingetragene Value
	 * @param valueEnum
	 *            das Enum der zulässigen Values
	 * @param property
	 *            die betreffende Property
	 * @param root
	 *            -cause der Exception (hier häufig
	 *            {@link IllegalArgumentException}, da ein Enum eine solche
	 *            Exception wirft, falls .valueOf keinen entsprechendne Wert
	 *            findet.
	 * @return
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
