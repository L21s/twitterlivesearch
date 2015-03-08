package twitter;

import java.io.IOException;

import javax.websocket.Session;

import twitter4j.Status;
import twitterTest.de.twittertest.TweetListener;

public class MyTweetListener implements TweetListener {

	private Session session;
	
	public MyTweetListener(Session session) {
		this.session = session;
	}
	
	@Override
	public void handleNewTweet(Status tweet) {
		try {
			session.getBasicRemote().sendText(TwitterMaschine.createTweetMessage(tweet).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
