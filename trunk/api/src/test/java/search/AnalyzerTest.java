package search;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnalyzerTest extends TestCase {
	private Analyzer analyzer;
	private Directory directory;
	private IndexWriter iwriter;
	private IndexWriterConfig config;

	@BeforeClass
	public void setUp() throws IOException {
		analyzer = new GermanAnalyzer();
		// Store the index in memory:
		directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		config = new IndexWriterConfig(null);
		iwriter = new IndexWriter(directory, config);

		Document doc = new Document();
		doc.add(new Field("tweet", "Schuh", TextField.TYPE_STORED));
		try {
			iwriter.addDocument(doc);
			iwriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		iwriter.close();
	}

	@Test
	public void testStemmedWords() throws IOException {
		Query text = null;
		QueryParser parser = new QueryParser("tweet", analyzer);
		try {
			if (!DirectoryReader.indexExists(directory)) {
				return;
			}
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		DirectoryReader ireader;
		try {
			ireader = DirectoryReader.open(directory);
		} catch (IOException e2) {
			ireader = null;
			e2.printStackTrace();
		}
		IndexSearcher isearcher = new IndexSearcher(ireader);

		try {
			text = parser.parse("tweet:" + "seine Schuhe");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BooleanQuery query = new BooleanQuery();
		query.add(text, Occur.MUST);
		ScoreDoc[] hits = null;
		try {
			hits = isearcher.search(query, 1000).scoreDocs;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		assertEquals(1, hits.length);
		
		if (hits != null) {
			for (int i = 0; i < hits.length; i++) {
				assertEquals("Schuh", isearcher.doc(hits[i].doc).get("tweet"));
				
			}
		}
		
		ireader.close();
	}

	@AfterClass
	public void tearDown() {

	}
}
