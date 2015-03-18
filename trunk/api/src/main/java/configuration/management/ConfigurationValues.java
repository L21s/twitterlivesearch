package configuration.management;

/**
 * Alle Property-Values als statische Enums. <br />
 * <b>Die Values der Enums sind auch die Values, die in der Properties-File für die entsprechende Property zur Verfügung stehen.</b>
 * @author schmitzhermes
 *
 */
public class ConfigurationValues {
	
	/**
	 * Property-Key: twitter4serioussearch.streamConfig ({@link ConfigurationKey#STREAM_CONFIG}) <br />
	 * Eigenschaften: {@link AbstractConfiguration#getStreamConfig()}
	 * @author schmitzhermes
	 *
	 */
	public static enum StreamConfig {
		USER_STREAM, GARDENHOSE
	}
}
