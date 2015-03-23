package properties;

import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.twitter4serioussearch.api.configuration.ConfigurationHolder;
import de.twitter4serioussearch.api.configuration.build.ConfigurationFactory;
import de.twitter4serioussearch.filter.LanguageFilter;

@RunWith(JUnit4.class)
public class ConfigurationTest extends TestCase{
	
	@Test
	public void hashmapConfiguration() {
		HashMap<String, String> map = new HashMap<String, String>();
		String prefix = "twitter4serioussearch.";
		map.put(prefix + "globalFilters", "de.twitter4serioussearch.filter.LanguageFilter, de.twitter4serioussearch.filter.LanguageFilter");
		ConfigurationFactory.createConfiguration(map);
		assertEquals(LanguageFilter.class, ConfigurationHolder.getConfiguration().getFilters()[0].getClass());
		assertEquals(LanguageFilter.class, ConfigurationHolder.getConfiguration().getFilters()[1].getClass());
	}
}
