package de.twitter4serioussearch;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
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
	private KeywordHolder keywordHolder;
	private TwitterStream twitterStream;

	public Twitter4Serioussearch() throws IOException {
		idGenerator = new IdGenerator();
		tweetHolder = new TweetHolder();
		analyzer = new WhitespaceAnalyzer();
		keywordHolder = new KeywordHolder();
		// Store the index in memory:
		Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		config = new IndexWriterConfig(analyzer);
		iwriter = new IndexWriter(directory, config);
		UserStreamListener listener = new MyUserStreamListener(idGenerator,
				directory, analyzer, tweetHolder, keywordHolder, iwriter);
		twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		twitterStream.user();
	}

	@Override
	protected void finalize() throws Throwable {
		twitterStream.clearListeners();
		twitterStream.cleanUp();
		twitterStream.shutdown();
		iwriter.close();
		super.finalize();
	}

	/**
	 * Registriert einen {@link de.twitter4serioussearch.TweetListener
	 * TweetListener} für die Kombination aus Query und Session
	 *
	 * @param query
	 *            Vom User gesuchter String
	 * @param sessionId
	 *            eindeutiger Session Identifier (Hintergrund: Die gleiche Query
	 *            kann von mehreren Usern registriert werden)
	 * @param actionListener
	 *            {@link de.twitter4serioussearch.TweetListener TweetListener}
	 *            der invoked wird, sobald ein zum query passender Tweet
	 *            empfangen wurde
	 */
	public void registerQuery(String query, String sessionId,
			TweetListener actionListener) {
		query = StringUtils.join(Tokenizer.getTokensForString(query), " ");
		keywordHolder.registerQuery(query, sessionId, actionListener);
		// TODO in persitenten Suchen
	}

	/**
	 * Deregistriert ein Query für die gegebene Session
	 *
	 * @param query
	 *            zu unregistrierendes Query
	 * @param sessionId
	 *            eindeutiger Session Identifier (Hintergrund: Die gleiche Query
	 *            kann von mehreren Usern registriert werden)
	 */
	public void unregisterQuery(String query, String sessionId) {
		query = StringUtils.join(Tokenizer.getTokensForString(query), " ");
		keywordHolder.unregisterQuery(query, sessionId);
	}

	/**
	 * Deregistriert alle querys für eine gegebene session
	 *
	 * @param sessionId
	 *            eindeutiger Session Identifier
	 */
	public void unregisterSession(String sessionId) {
		keywordHolder.unregisterSession(sessionId);
	}
}
