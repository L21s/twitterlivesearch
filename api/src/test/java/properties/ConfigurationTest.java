package properties;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import configuration.ConfigurationHolder;
import configuration.management.AbstractConfiguration;
import configuration.management.ConfigurationValues;

@RunWith(JUnit4.class)
public class ConfigurationTest extends TestCase{
	
	@Test
	public void shouldHaveGardenhose() {
		AbstractConfiguration c = ConfigurationHolder.getConfiguration();
		assertEquals(ConfigurationValues.StreamConfig.GARDENHOSE, c.getStreamConfig());
	}
}
