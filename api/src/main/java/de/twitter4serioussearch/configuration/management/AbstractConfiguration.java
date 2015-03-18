package de.twitter4serioussearch.configuration.management;

import de.twitter4serioussearch.configuration.management.ConfigurationValues.StreamConfig;


/**
 * Abstrakte Konfigurationsklasse. Alle Konfigurationen sollen von dieser Klasse erben. <br />
 * Standardwerte sind im Java-Doc aufgeführt und kursiv gekennzeichnet. <br />
 * Das Objekt ist immutable. Es kann nur durch die {@link ConfigurationFactory} erzeugt werden. 
 * @author schmitzhermes
 *
 */
public abstract class AbstractConfiguration {
	
	/**
	 * Konfiguriert, ob es einen User-Account als Listener gibt oder die Gardenhose verwendet werden soll. <br /> <br />
	 * <b>Property-Key:</b> twitter4serioussearch.streamConfig (siehe: {@link ConfigurationKey#STREAM_CONFIG}) <br />
	 * <b>Property-Values:</b> <em>USER_STREAM</em> | GARDENHOSE (siehe: {@link ConfigurationValues.StreamConfig}) <br />
	 * <b>Default:</b> USER_STREAM
	 */
	private StreamConfig streamConfig;

	/**
	 * @return Aktuelle StreamConfig
	 * @see #streamConfig
	 */
	public StreamConfig getStreamConfig() {
		return streamConfig;
	}	

	void setStreamConfig(StreamConfig streamConfig) {
		this.streamConfig = streamConfig;
	}

}


