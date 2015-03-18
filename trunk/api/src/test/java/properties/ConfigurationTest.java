package properties;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.twitter4serioussearch.configuration.ConfigurationHolder;
import de.twitter4serioussearch.configuration.management.AbstractConfiguration;
import de.twitter4serioussearch.configuration.management.ConfigurationFactory;
import de.twitter4serioussearch.configuration.management.ConfigurationValues;

@RunWith(JUnit4.class)
public class ConfigurationTest extends TestCase{
	
	@Test
	public void shouldHaveGardenhose() {
		ConfigurationFactory.createConfiguration();
		AbstractConfiguration c = ConfigurationHolder.getConfiguration();
		assertEquals(ConfigurationValues.StreamConfig.USER_STREAM, c.getStreamConfig());
	}
}
