package de.twitter4serioussearch.api;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import de.twitter4serioussearch.analysis.AnalyzerMapping;
import de.twitter4serioussearch.analysis.Searcher;
import de.twitter4serioussearch.api.configuration.ConfigurationHolder;
import de.twitter4serioussearch.api.configuration.build.AbstractConfiguration;
import de.twitter4serioussearch.api.configuration.build.ConfigurationBuilder;
import de.twitter4serioussearch.api.configuration.build.ConfigurationFactory;
import de.twitter4serioussearch.api.configuration.build.DefaultConfiguration;
import de.twitter4serioussearch.api.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitter4serioussearch.api.configuration.management.ConfigurationValues.StreamConfig;
import de.twitter4serioussearch.model.TweetHolder;
import de.twitter4serioussearch.twitter.TwitterStreamListener;

/**
 * The Factory class, which is need to build {@link Twitter4Serioussearch}. <br />
 * This factory configures all the necessary dependencies regarding Twitter4J, Lucene and so on. <br />
 * Please always use this factory in order to create a {@link Twitter4Serioussearch}.
 * @author schmitzhermes
 *
 */
public class Twitter4SerioussearchFactory {
	private static Logger log = LogManager.getLogger();

	/**
	 * builds {@link Twitter4Serioussearch} on top of the {@link DefaultConfiguration}
	 * @return a configured and running instance of {@link Twitter4Serioussearch}
	 */
	public static Twitter4Serioussearch build() {
		ConfigurationFactory.createConfiguration();
		return configureTwitter(ConfigurationHolder.getConfiguration());
	}

	/**
	 * builds {@link Twitter4Serioussearch} on top of an own configuration
	 * @param userConfig
	 * @return a configured and running instance of {@link Twitter4Serioussearch}
	 */
	public static Twitter4Serioussearch build(AbstractConfiguration userConfig) {
		ConfigurationFactory.createConfiguration(userConfig);
		return configureTwitter(ConfigurationHolder.getConfiguration());
	}
	
	/**
	 * builds {@link Twitter4Serioussearch} with the help of a {@link ConfigurationBuilder}
	 * @param configBuilder the configuration builder
	 * @return  a configured and running instance of {@link Twitter4Serioussearch}
	 */
	public static Twitter4Serioussearch build(ConfigurationBuilder configBuilder) {
		ConfigurationFactory.createConfiguration(configBuilder.build());
		return configureTwitter(ConfigurationHolder.getConfiguration());
	}

	private static Twitter4Serioussearch configureTwitter(
			AbstractConfiguration configuration) {
		Twitter4Serioussearch twitter = null;
		try {
			// several important variables are initialized here
			twitter = new Twitter4Serioussearch();
			TweetHolder tweetHolder = new TweetHolder();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					AnalyzerMapping.getInstance().ANALYZER_FOR_DELIMITER);
			TwitterStream twitterStream = new TwitterStreamFactory()
			.getInstance();
			Directory directory;

			// confguration part: Twitter4Serioussearch is configured here
			// according to the config
			if (configuration.getDirectoryConfig() == DirectoryConfig.RAM) {
				directory = new RAMDirectory();
				log.trace("initialized RAM-Directory");
			} else {
				directory = FSDirectory.open(Paths.get(configuration
						.getDirectory()));
				log.trace("initialized FS-Directory on path "
						+ configuration.getDirectory());
			}
			IndexWriter iwriter = new IndexWriter(directory, indexWriterConfig);
			Searcher searcher = new Searcher(directory);

			twitterStream.addListener(new TwitterStreamListener(directory,
					tweetHolder, iwriter, searcher));
			if (configuration.getStreamConfig() == StreamConfig.USER_STREAM) {
				twitterStream.user();
			} else if (configuration.getStreamConfig() == StreamConfig.GARDENHOSE) {
				twitterStream.sample();
			}

			// set everything needed in Twitter4Serioussearch
			twitter.setCurrentDirectory(directory);
			twitter.setIndexWriter(iwriter);
			twitter.setTweetHolder(tweetHolder);
			twitter.setTwitterStream(twitterStream); // Referenz auf
			// Twitter4Serioussearch
			twitter.setSearcher(searcher);

		} catch (IOException e) {
			e.printStackTrace();

		}

		return twitter;
	}
}
