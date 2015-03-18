package de.twitter4serioussearch;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import de.twitter4serioussearch.configuration.ConfigurationHolder;
import de.twitter4serioussearch.configuration.management.AbstractConfiguration;
import de.twitter4serioussearch.configuration.management.ConfigurationFactory;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.DirectoryConfig;
import de.twitter4serioussearch.configuration.management.ConfigurationValues.StreamConfig;
import de.twitter4serioussearch.search.Searcher;

public class Twitter4SerioussearchFactory {
	private static Logger log = Logger.getLogger(Twitter4SerioussearchFactory.class);
	
	public static Twitter4Serioussearch build() {
		Twitter4Serioussearch twitter = null;
		try {
			// Load configuration
			ConfigurationFactory.createConfiguration();
			AbstractConfiguration configuration = ConfigurationHolder.getConfiguration();
			
			// several important variables are initialized here
			twitter = new Twitter4Serioussearch();
			TweetHolder tweetHolder = new TweetHolder();
			Analyzer analyzer = new WhitespaceAnalyzer();
			QueryHolder queryHolder = new QueryHolder();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
			twitter.setTwitterStream(twitterStream); // Referenz auf Twitter4Serioussearch
			Directory directory;
			
			// confguration part: Twitter4Serioussearch is configured here according to the config
			if(configuration.getDirectoryConfig() == DirectoryConfig.RAM) {
				 directory = new RAMDirectory();
				 log.trace("initialized RAM-Directory");
			} else {
				directory = FSDirectory.open(Paths.get(configuration.getDirectory()));
				log.trace("initialized FS-Directory on path " + configuration.getDirectory());
			}
			IndexWriter iwriter = new IndexWriter(directory, indexWriterConfig);
			Searcher searcher = new Searcher(directory, queryHolder, tweetHolder);
			
			if(configuration.getStreamConfig() == StreamConfig.USER_STREAM) {
				twitterStream.addListener(new TwitterStreamListener(directory, analyzer, tweetHolder, queryHolder, iwriter, searcher));
				twitterStream.user();
			} else if(configuration.getStreamConfig() == StreamConfig.GARDENHOSE){
				twitterStream.addListener(null);
				twitterStream.sample();
			}
			
			// set everything needed in Twitter4Serioussearch
			twitter.setCurrentDirectory(directory);
			twitter.setIndexWriter(iwriter);
			twitter.setTweetHolder(tweetHolder);
			twitter.setKeywordHolder(queryHolder);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return twitter;
	}
}
