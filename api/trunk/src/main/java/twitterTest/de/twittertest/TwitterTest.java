package twitterTest.de.twittertest;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterTest {

	public static void main(String[] args) throws TwitterException, IOException {
		Analyzer analyzer = new StandardAnalyzer();
		// Store the index in memory:
		Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		final IndexWriter iwriter = new IndexWriter(directory, config);
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				Document doc = new Document();
				String text = status.getText();
				doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
				try {
					iwriter.addDocument(doc);
					iwriter.commit();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(status.getLang());
				
			}

			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {

			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

			}

			public void onException(Exception ex) {
				try {
					iwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ex.printStackTrace();
			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			public void onStallWarning(StallWarning arg0) {
				System.out.println("stall warning" + arg0);

			}
		};
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		// sample() method internally creates a thread which manipulates
		// TwitterStream and calls these adequate listener methods continuously.
//		twitterStream.sample();
//		displayTokenUsingStandardAnalyzer();
		
		Document doc = new Document();
		String text = "I got ten houses.";
		doc.add(new Field("id", "1", TextField.TYPE_STORED));
		doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
		try {
			iwriter.addDocument(doc);
			iwriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			 iwriter.close();
		}
		
		 // Now search the index:
	   
	    
	   if(DirectoryReader.indexExists(directory)) {
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		   // Parse a simple query that searches for "text":
		    QueryParser parser = new QueryParser("fieldname", analyzer);
		    Query query = null;
			try {
				query = parser.parse("house");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			isearcher.setSimilarity(new LMJelinekMercerSimilarity(0.8f));
		    ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
		    // Iterate through the results:
		    for (int i = 0; i < hits.length; i++) {
		      Document hitDoc = isearcher.doc(hits[i].doc);
		      System.out.println(hitDoc.get("id"));
		    }
		    ireader.close();
		    directory.close();
	   }
	   
		
	}
}
