package de.twitter4serioussearch.configuration.management;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import de.twitter4serioussearch.configuration.management.ConfigurationValues.StreamConfig;

/**
 * Kreiert eine Konfiguration anhand einer Default-Konfiguration und der Properties-File. Die Properties-File muss den Namen <em>twitter4serioussearch.properties</em> (siehe: {@link #PROPERTY_FILE}). <br/>
 * Es existiert eine Standardkonfiguration ({@link DefaultConfiguration}), deren Werte überschrieben werden, wenn eine entsprechende Property in der Property-File existiert.
 * @author schmitzhermes
 *
 */
public class ConfigurationFactory {
	public static final String PROPERTY_FILE = "twitter4serioussearch.properties";
	
	private static Properties properties = new Properties();
	
	private ConfigurationFactory() {}
	
	/**
	 * Erstellt die Konfiguration. 
	 * @return die Konfiguration anhand der Standardkonfiguration und den in der Property-File ({@link #PROPERTY_FILE}) überschriebenen Werten.
	 */
	public static AbstractConfiguration createConfiguration() {
		AbstractConfiguration config = new DefaultConfiguration();
		readConfigurationFromFile(config);
		
		return config;
	}
	
	/**
	 * Liest die Konfiguration aus der Properties-File ({@link #PROPERTY_FILE}) und passt die übergebene Konfiguration entsprechend an. (Call-by-reference)
	 * @param config die Konfiguration vor dem Einlesen der Properties-File ({@link #PROPERTY_FILE})
	 */
	private static void readConfigurationFromFile(AbstractConfiguration config) {
		InputStream inputStream = ConfigurationFactory.class.getClassLoader().getResourceAsStream(PROPERTY_FILE);
		
		if(inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// TODO ordentlich machen
		// TODO unknown property -> log error
		String streamConfig;
		if((streamConfig = properties.getProperty(ConfigurationKey.STREAM_CONFIG)) != null) {
			try {
				config.setStreamConfig(StreamConfig.valueOf(StreamConfig.class, streamConfig));
			} catch(IllegalArgumentException e) {
				throw buildConfigValueException(streamConfig, StreamConfig.class, ConfigurationKey.STREAM_CONFIG, e);
			}
		} 
	}
	 
	/**
	 * Erstellt eine sprechende Konfigurationsexception.
	 * @param actualValue die vom Nutzer eingetragene Value
	 * @param valueEnum das Enum der zulässigen Values
	 * @param property die betreffende Property
	 * @param root-cause der Exception (hier häufig {@link IllegalArgumentException}, da ein Enum eine solche Exception wirft, falls .valueOf keinen entsprechendne Wert findet.
	 * @return
	 */
	private static ConfigurationException buildConfigValueException(String actualValue, Class<? extends Enum<?>> valueEnum, String property, Throwable cause) {
		StringBuilder s = new StringBuilder();
		s.append("The value ");
		s.append(actualValue);
		s.append(" of the property ");
		s.append(property);
		s.append(" is not valid. ");
		
		s.append("Valid properties are: ");
		s.append(Arrays.toString(valueEnum.getEnumConstants()));
		
		return new ConfigurationException(s.toString(), cause);
	}
}

