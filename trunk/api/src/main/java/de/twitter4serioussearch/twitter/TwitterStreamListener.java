package de.twitter4serioussearch.twitter;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.store.Directory;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import de.twitter4serioussearch.analysis.AnalyzerMapping;
import de.twitter4serioussearch.analysis.FieldNames;
import de.twitter4serioussearch.analysis.Searcher;
import de.twitter4serioussearch.analysis.Tokenizer;
import de.twitter4serioussearch.filter.FilterManager;
import de.twitter4serioussearch.model.IdGenerator;
import de.twitter4serioussearch.model.QueryManager;
import de.twitter4serioussearch.model.QueryWrapper;
import de.twitter4serioussearch.model.TweetHolder;

public class TwitterStreamListener implements UserStreamListener,
		StatusListener {
	private TweetHolder tweetHolder;
	private IndexWriter iwriter;
	private Searcher searcher;
	private static Logger log = LogManager.getLogger();

	public TwitterStreamListener(Directory directory, TweetHolder tweetHolder,
			IndexWriter iwriter, Searcher searcher) {
		this.tweetHolder = tweetHolder;
		this.iwriter = iwriter;
		this.searcher = searcher;
	}

	@Override
	public void onStatus(Status status) {
		if (log.isTraceEnabled()) {
			log.trace("Incoming Tweet: " + status.getText());
		}
		String textForDoc = StringUtils.join(Tokenizer.getTokensForString(
				status.getText(), status.getLang()), AnalyzerMapping
				.getInstance().TOKEN_DELIMITER);
		// in case the document is empty after tokenizing (i.e. just stopwords)
		// we shouldnt add it to the index...
		if (textForDoc.isEmpty()) {
			return;
		}

		Document doc = new Document();
		Integer id = IdGenerator.getInstance().getNextId();
		// updating the tweet holder which holds all tweet objects
		tweetHolder.getTweets().set(id, status);
		// adding a new document to the lucene index, text the tweets message
		// and gets tokenized
		doc.add(new IntField(FieldNames.ID.getField(), id, Field.Store.YES));

		if (log.isTraceEnabled()) {
			log.trace("Indexing Document: " + textForDoc);
		}

		doc.add(new Field(FieldNames.TEXT.getField(), textForDoc,
				TextField.TYPE_NOT_STORED));
		try {
			// deleting the oldest document
			iwriter.deleteDocuments(NumericRangeQuery.newIntRange(
					FieldNames.ID.getField(), id, id, true, true));
			// writing the new document
			iwriter.addDocument(doc);
		} catch (IOException e) {
			log.fatal("Error when writing or deleting from the index!", e);
		}
		try {
			iwriter.commit();
		} catch (IOException e) {
			log.fatal("Error when trying to commit to index!", e);
		}
		// iterating through all the queries in the queryHolder to see if the
		// added tweet matches any existing query
		for (String queryString : QueryManager.getInstance().getQueries()) {
			List<Document> hits = searcher.searchForTweets(id, queryString);
			if (hits.size() > 1) {
				IllegalStateException e = new IllegalStateException(
						"The query with string "
								+ queryString
								+ " and id "
								+ id
								+ " returned "
								+ hits.size()
								+ " documents. This can only happen if id is not unique!");
				log.fatal("Error when searching", e);
			}
			for (Document document : hits) {
				// invoking every action listener registered for the given query
				// with every document (must actually be a single result,
				// because id must be unique!)
				for (QueryWrapper qw : QueryManager.getInstance()
						.getQueryWrappersForQuery(queryString)) {
					if (log.isTraceEnabled()) {
						log.trace("Informed client that new tweet is incoming: "
								+ queryString + " (untokenized)");
					}
					Status tweet = tweetHolder.getTweet(document
							.get(FieldNames.ID.getField()));
					if (FilterManager.tweetMatchesFilter(tweet, qw.getFilter())) {
						qw.getListener().handleNewTweet(tweet);
					}

				}
			}
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
