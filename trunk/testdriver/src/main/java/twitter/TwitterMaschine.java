package twitter;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

import twitter4j.Status;
import de.twitter4serioussearch.api.Twitter4Serioussearch;
import de.twitter4serioussearch.api.Twitter4SerioussearchFactory;

@Singleton
public class TwitterMaschine implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5748078495621149611L;

	private Twitter4Serioussearch twitter;

	@PostConstruct
	public void init() {
		setTwitter(Twitter4SerioussearchFactory.build());

	}

	@PreDestroy
	public void destroy() {

	}

	public static JsonObject createTweetMessage(Status tweet) {
		JsonProvider provider = JsonProvider.provider();
		JsonObject addMessage = provider.createObjectBuilder()
				.add("action", "new_tweet").add("text", tweet.getText())
				.build();
		return addMessage;
	}

	public Twitter4Serioussearch getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter4Serioussearch twitter) {
		this.twitter = twitter;
	}
}
