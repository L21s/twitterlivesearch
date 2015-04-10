package properties;

import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.twitterlivesearch.api.configuration.ConfigurationHolder;
import de.twitterlivesearch.api.configuration.build.AbstractConfiguration;
import de.twitterlivesearch.api.configuration.build.ConfigurationBuilder;
import de.twitterlivesearch.api.configuration.build.ConfigurationFactory;
import de.twitterlivesearch.api.configuration.build.DefaultConfiguration;
import de.twitterlivesearch.filter.LanguageFilter;

@RunWith(JUnit4.class)
public class ConfigurationTest extends TestCase{
	
	@Test
	@Ignore
	public void hashmapConfiguration() {
		HashMap<String, String> map = new HashMap<String, String>();
		String prefix = "twitter4serioussearch.";
		map.put(prefix + "globalFilters", "de.twitterlivesearch.filter.LanguageFilter, de.twitterlivesearch.filter.LanguageFilter");
		ConfigurationFactory.createConfiguration(map);
		assertEquals(LanguageFilter.class, ConfigurationHolder.getConfiguration().getFilters()[0].getClass());
		assertEquals(LanguageFilter.class, ConfigurationHolder.getConfiguration().getFilters()[1].getClass());
	}
	
	@Test
	public void configBuilder() {
		ConfigurationBuilder cb = new ConfigurationBuilder()
								.setDefaultOperator(Operator.OR);
		
		AbstractConfiguration c = new DefaultConfiguration();
		ConfigurationFactory.createConfiguration(cb.build());
		assertEquals(Operator.OR, ConfigurationHolder.getConfiguration().getDefaultOperator());
	}
	
	
}
