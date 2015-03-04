package twitterTest.de.twittertest;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamListener;

public class TwitterTest {

	public static void main(String[] args) throws TwitterException, IOException {
		IdGenerator idGenerator = new IdGenerator();
		TweetHolder tweetHolder = new TweetHolder();
		Analyzer analyzer = new StandardAnalyzer();
		// Store the index in memory:
		Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(directory, config);
		UserStreamListener listener = new MyUserStreamListener(idGenerator,
				directory, analyzer, tweetHolder, iwriter);
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		twitterStream.user();

		// Document doc = new Document();
		// String text = "I got ten houses.";
		// doc.add(new Field("id", "1", TextField.TYPE_STORED));
		// doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
		// try {
		// iwriter.addDocument(doc);
		// iwriter.commit();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// // iwriter.close();
		// }
		//
		// Now search the index:

		// if (DirectoryReader.indexExists(directory)) {
		// QueryParser parser = new QueryParser("text", analyzer);
		// // Parse a simple query that searches for "text":
		// Query text = null;
		// try {
		// text = parser.parse("text:Frage");
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// BooleanQuery query = new BooleanQuery();
		// Query idQuery = NumericRangeQuery.newIntRange("id", id, id, true,
		// true);
		// query.add(idQuery, Occur.MUST);
		// query.add(text, Occur.MUST);
		// ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
		// // Iterate through the results:
		// for (int i = 0; i < hits.length; i++) {
		// Document hitDoc = isearcher.doc(hits[i].doc);
		// System.out.println(hitDoc.get("id"));
		// }
		// ireader.close();
		// directory.close();
		// }
	}
}
