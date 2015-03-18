package properties;

import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.twitter4serioussearch.configuration.ConfigurationHolder;
import de.twitter4serioussearch.configuration.management.ConfigurationFactory;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.StreamConfig;

@RunWith(JUnit4.class)
public class ConfigurationTest extends TestCase{
	
	@Test
	public void shouldHaveGardenhose() {
		HashMap<String, String> map = new HashMap<String, String>();
		String prefix = "twitter4serioussearch.";
		map.put(prefix + "maxNumberOfTweets", "100");
		map.put(prefix + "streamConfig", "GARDENHOSE");
		map.put(prefix + "directoryConfig", "FS_DIRECTORY");
		map.put(prefix + "directory", "/bla/test");
		map.put(prefix + "defaultOperator", "OR");
		ConfigurationFactory.createConfiguration(map);
		assertEquals(100, ConfigurationHolder.getConfiguration().getMaxNumberTweets().intValue());
		assertEquals(StreamConfig.GARDENHOSE, ConfigurationHolder.getConfiguration().getStreamConfig());
		assertEquals(DirectoryConfig.FS_DIRECTORY, ConfigurationHolder.getConfiguration().getDirectoryConfig());
		assertEquals("/bla/test", ConfigurationHolder.getConfiguration().getDirectory());
		assertEquals(Operator.OR, ConfigurationHolder.getConfiguration().getDefaultOperator());
		
	}
}
