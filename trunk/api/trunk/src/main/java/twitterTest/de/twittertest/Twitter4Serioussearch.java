package twitterTest.de.twittertest;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamListener;

public class Twitter4Serioussearch {
	private IdGenerator idGenerator;
	private TweetHolder tweetHolder;
	private Analyzer analyzer;
	private IndexWriterConfig config;
	private IndexWriter iwriter;

	public Twitter4Serioussearch() throws IOException {
		idGenerator = new IdGenerator();
		tweetHolder = new TweetHolder();
		analyzer = new StandardAnalyzer();
		// Store the index in memory:
		Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		config = new IndexWriterConfig(analyzer);
		iwriter = new IndexWriter(directory, config);
		UserStreamListener listener = new MyUserStreamListener(idGenerator,
				directory, analyzer, tweetHolder, iwriter);
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		twitterStream.user();
	}

	public void registerKeyword() {

	}
}
