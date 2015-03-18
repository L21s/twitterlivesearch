package de.twitter4serioussearch;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import de.twitter4serioussearch.common.FieldNames;
import de.twitter4serioussearch.search.Searcher;
rt twitter4j.UserStreamListener;

//TODO rename
public class MyUserStreamListener implements UserStreamListener, StatusListener {
	private IdGenerator idGenerator;
	private TweetHolder tweetHolder;
	private Directory directory;
	private IndexWriter iwriter;
	private QueryHolder keywordHolder;
	private Logger log = Logger.getLogger(this.getClass());
	private Searcher searcher;

	public MyUserStreamListener(IdGenerator idGenerator, Directory directory,
			Analyzer analyzer, TweetHolder tweetHolder,
			QueryHolder keywordHolder, IndexWriter iwriter, Searcher searcher) {
		this.idGenerator = idGenerator;
		this.directory = directory;
		this.tweetHolder = tweetHolder;
		this.iwriter = iwriter;
		this.keywordHolder = keywordHolder;
		this.searcher = searcher;
	}

	@Override
	public void onStatus(Status status) {
		//writing Document to the Index
		Document doc = new Document();
		Integer id = idGenerator.getNextId();
		Integer idToRemove = idGenerator.getIdToRemove();
		tweetHolder.getTweets().put(id.intValue(), status);
		tweetHolder.getTweets().remove(idToRemove);
		doc.add(new IntField(FieldNames.ID.getField(), id, Field.Store.YES));
		String textForDoc = StringUtils.join(Tokenizer.getTokensForString(
				status.getText(), status.getLang()), AnalyzerMapping.TOKEN_DELIMITER);
		doc.add(new Field(FieldNames.TEXT.getField(), textForDoc, TextField.TYPE_NOT_STORED));
		iwriter.addDocument(doc);
		iwriter.deleteDocuments(NumericRangeQuery.newIntRange(FieldNames.ID.getField(), idToRemove,	idToRemove, true, true));
		try {
			iwriter.commit();
		} catch (Exception e) {
			log.fatal("Error when trying to commit to index!",e);
		}
		for (String queryString : keywordHolder.getQueries().keySet()) {
			List<Document> hits = searcher.searchForTweets(id, queryString);
			for (Document document : hits) {
				for (TweetListener actionListener : keywordHolder
						.getQueries().get(queryString).values()) {
					actionListener.handleNewTweet(tweetHolder
							.getTweets().get(
									Integer.parseInt(document.get(FieldNames.ID.getField()))));
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
