package twitterTest.de.twittertest;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

public class MyUserStreamListener implements UserStreamListener {
	private IdGenerator idGenerator;
	private IndexWriter iwriter;
	private IndexSearcher isearcher;
	private Analyzer analyzer;
	private TweetHolder tweetHolder;

	public MyUserStreamListener(IdGenerator idGenerator, IndexWriter iwriter,
			IndexSearcher isearcher, Analyzer analyzer, TweetHolder tweetHolder) {
		this.idGenerator = idGenerator;
		this.iwriter = iwriter;
		this.isearcher = isearcher;
		this.analyzer = analyzer;
		this.tweetHolder = tweetHolder;
	}

	public void onStatus(Status status) {
		System.out.println("status incoming:" + status.getText());
		Document doc = new Document();
		Integer id = idGenerator.getId();
		tweetHolder.getTweets().put(id, status);
		doc.add(new IntField("id", id, IntField.TYPE_STORED));
		doc.add(new Field("text", "Frage Frage Frage Frage",
				TextField.TYPE_NOT_STORED));
		try {
			iwriter.addDocument(doc);
			iwriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query text = null;
		QueryParser parser = new QueryParser("text", analyzer);
		try {
			text = parser.parse("text:Frage");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BooleanQuery query = new BooleanQuery();
		// Query idQuery = NumericRangeQuery.newIntRange("id", id, id, true,
		// true);
		// query.add(idQuery, Occur.MUST);
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
					System.out
							.println("gefunden:" + isearcher.doc(i).get("id"));
					System.out.println(tweetHolder.getTweets().get(
							isearcher.doc(i).get("id")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO Auto-generated method stub

	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO Auto-generated method stub

	}

	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO Auto-generated method stub

	}

	public void onStallWarning(StallWarning warning) {
		// TODO Auto-generated method stub

	}

	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

	public void onDeletionNotice(long directMessageId, long userId) {
		// TODO Auto-generated method stub

	}

	public void onFriendList(long[] friendIds) {
		// TODO Auto-generated method stub

	}

	public void onFavorite(User source, User target, Status favoritedStatus) {
		// TODO Auto-generated method stub

	}

	public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
		// TODO Auto-generated method stub

	}

	public void onFollow(User source, User followedUser) {
		// TODO Auto-generated method stub

	}

	public void onUnfollow(User source, User unfollowedUser) {
		// TODO Auto-generated method stub

	}

	public void onDirectMessage(DirectMessage directMessage) {
		// TODO Auto-generated method stub

	}

	public void onUserListMemberAddition(User addedMember, User listOwner,
			UserList list) {
		// TODO Auto-generated method stub

	}

	public void onUserListMemberDeletion(User deletedMember, User listOwner,
			UserList list) {
		// TODO Auto-generated method stub

	}

	public void onUserListSubscription(User subscriber, User listOwner,
			UserList list) {
		// TODO Auto-generated method stub

	}

	public void onUserListUnsubscription(User subscriber, User listOwner,
			UserList list) {
		// TODO Auto-generated method stub

	}

	public void onUserListCreation(User listOwner, UserList list) {
		// TODO Auto-generated method stub

	}

	public void onUserListUpdate(User listOwner, UserList list) {
		// TODO Auto-generated method stub

	}

	public void onUserListDeletion(User listOwner, UserList list) {
		// TODO Auto-generated method stub

	}

	public void onUserProfileUpdate(User updatedUser) {
		// TODO Auto-generated method stub

	}

	public void onUserSuspension(long suspendedUser) {
		// TODO Auto-generated method stub

	}

	public void onUserDeletion(long deletedUser) {
		// TODO Auto-generated method stub

	}

	public void onBlock(User source, User blockedUser) {
		// TODO Auto-generated method stub

	}

	public void onUnblock(User source, User unblockedUser) {
		// TODO Auto-generated method stub

	}

}
