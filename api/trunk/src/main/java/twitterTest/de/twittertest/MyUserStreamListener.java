package twitterTest.de.twittertest;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

public class MyUserStreamListener implements UserStreamListener {
	private IdGenerator idGenerator;
	private Analyzer analyzer;
	private TweetHolder tweetHolder;
	private Directory directory;
	private IndexWriter iwriter;
	private KeywordHolder keywordHolder;

	public MyUserStreamListener(IdGenerator idGenerator, Directory directory,
			Analyzer analyzer, TweetHolder tweetHolder,
			KeywordHolder keywordHolder, IndexWriter iwriter) {
		this.idGenerator = idGenerator;
		this.directory = directory;
		this.analyzer = analyzer;
		this.tweetHolder = tweetHolder;
		this.iwriter = iwriter;
		this.keywordHolder = keywordHolder;
	}

	@Override
	public void onStatus(Status status) {
		System.out.println("status incoming:" + status.getText());
		Document doc = new Document();
		Integer id = idGenerator.getId();
		tweetHolder.getTweets().put(id.intValue(), status);
		FieldType num = new FieldType();
		num.setNumericType(FieldType.NumericType.INT);
		num.setStored(true);
		// doc.add(new IntField("id", id.intValue(), num));
		doc.add(new Field("id", String.valueOf(id.intValue()),
				TextField.TYPE_STORED));
		doc.add(new Field("text", status.getText(), TextField.TYPE_STORED));
		try {
			iwriter.addDocument(doc);
			iwriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query text = null;
		Query idQuery = null;
		QueryParser parser = new QueryParser("text", analyzer);
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
		for (String keyword : keywordHolder.getKeywords().keySet()) {
			try {
				text = parser.parse("text:" + keyword);
				idQuery = parser.parse("id:<NUMBER>" + id.intValue()
						+ "<NUMBER>");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			BooleanQuery query = new BooleanQuery();
			query.add(idQuery, Occur.MUST);
			query.add(text, Occur.MUST);
			ScoreDoc[] hits = null;
			try {
				hits = isearcher.search(query, 1000).scoreDocs;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (hits != null) {
				for (int i = 0; i < hits.length; i++) {
					try {
						System.out.println("gefunden:"
								+ isearcher.doc(hits[i].doc).get("id"));
						for (TweetListener actionListener : keywordHolder
								.getKeywords().get(keyword)) {
							actionListener.handleNewTweet(tweetHolder
									.getTweets().get(
											Integer.parseInt(isearcher.doc(
													hits[i].doc).get("id"))));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		try {
			ireader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {

	}

	@Override
	public void onStallWarning(StallWarning warning) {

	}

	@Override
	public void onException(Exception ex) {

	}

	@Override
	public void onDeletionNotice(long directMessageId, long userId) {

	}

	@Override
	public void onFriendList(long[] friendIds) {

	}

	@Override
	public void onFavorite(User source, User target, Status favoritedStatus) {

	}

	@Override
	public void onUnfavorite(User source, User target, Status unfavoritedStatus) {

	}

	@Override
	public void onFollow(User source, User followedUser) {

	}

	@Override
	public void onUnfollow(User source, User unfollowedUser) {

	}

	@Override
	public void onDirectMessage(DirectMessage directMessage) {

	}

	@Override
	public void onUserListMemberAddition(User addedMember, User listOwner,
			UserList list) {

	}

	@Override
	public void onUserListMemberDeletion(User deletedMember, User listOwner,
			UserList list) {

	}

	@Override
	public void onUserListSubscription(User subscriber, User listOwner,
			UserList list) {

	}

	@Override
	public void onUserListUnsubscription(User subscriber, User listOwner,
			UserList list) {

	}

	@Override
	public void onUserListCreation(User listOwner, UserList list) {

	}

	@Override
	public void onUserListUpdate(User listOwner, UserList list) {

	}

	@Override
	public void onUserListDeletion(User listOwner, UserList list) {

	}

	@Override
	public void onUserProfileUpdate(User updatedUser) {

	}

	@Override
	public void onUserSuspension(long suspendedUser) {

	}

	@Override
	public void onUserDeletion(long deletedUser) {

	}

	@Override
	public void onBlock(User source, User blockedUser) {

	}

	@Override
	public void onUnblock(User source, User unblockedUser) {

	}

}
