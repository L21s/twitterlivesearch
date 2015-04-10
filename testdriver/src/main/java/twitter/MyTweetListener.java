package twitter;

import java.io.IOException;

import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.Status;
import de.twitterlivesearch.api.TweetListener;

public class MyTweetListener implements TweetListener {

	private Session session;
	private Logger log = LogManager.getLogger();

	public MyTweetListener(Session session) {
		this.session = session;
	}

	@Override
	public void handleNewTweet(Status tweet) {
		try {
			session.getBasicRemote().sendText(
					TwitterMaschine.createTweetMessage(tweet).toString());
			log.trace("sent text "
					+ TwitterMaschine.createTweetMessage(tweet).toString()
					+ " to session " + session);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
