package advancedAnalysis;

import java.io.IOException;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.BeforeClass;
import org.junit.Test;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.UserStreamListener;
import twitterTest.de.twittertest.IdGenerator;
import twitterTest.de.twittertest.KeywordHolder;
import twitterTest.de.twittertest.MyUserStreamListener;
import twitterTest.de.twittertest.Tokenizer;
import twitterTest.de.twittertest.TweetHolder;

public class AutoAnalyzerTest extends TestCase {
	private IdGenerator idGenerator;
	private TweetHolder tweetHolder;
	private Analyzer analyzer;
	private IndexWriterConfig config;
	private IndexWriter iwriter;
	private KeywordHolder keywordHolder;
	private UserStreamListener listener;

	@Override
	@BeforeClass
	public void setUp() throws IOException {
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
		listener = new MyUserStreamListener(idGenerator, directory, analyzer,
				tweetHolder, keywordHolder, iwriter);
	}

	@Test
	public void testAnalyzer() {
		keywordHolder.putKeyword(
				StringUtils.join(Tokenizer.getTokensForString("shoe"), " "),
				"1", new MyTweetListener());
		Status status = new Status() {

			@Override
			public UserMentionEntity[] getUserMentionEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public URLEntity[] getURLEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public SymbolEntity[] getSymbolEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public MediaEntity[] getMediaEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public HashtagEntity[] getHashtagEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public MediaEntity[] getExtendedMediaEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RateLimitStatus getRateLimitStatus() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getAccessLevel() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int compareTo(Status o) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean isTruncated() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRetweetedByMe() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRetweeted() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRetweet() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isPossiblySensitive() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFavorited() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String[] getWithheldInCountries() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public User getUser() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getText() {
				return "I am wearing black shoes.";
			}

			@Override
			public String getSource() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Scopes getScopes() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Status getRetweetedStatus() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getRetweetCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Place getPlace() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getLang() {
				return "en";
			}

			@Override
			public long getInReplyToUserId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getInReplyToStatusId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getInReplyToScreenName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public GeoLocation getGeoLocation() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getFavoriteCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getCurrentUserRetweetId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Date getCreatedAt() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long[] getContributors() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		listener.onStatus(status);
	}
}
