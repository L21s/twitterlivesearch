package twitter;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

import twitter4j.Status;
import de.twitterlivesearch.api.TwitterLiveSearch;
import de.twitterlivesearch.api.TwitterLiveSearchFactory;

@Singleton
public class TwitterMachine implements Serializable { 
	/**
	 *
	 */
	private static final long serialVersionUID = 5748078495621149611L;

	private TwitterLiveSearch twitter;

	@PostConstruct
	public void init() {
		setTwitter(TwitterLiveSearchFactory.build());

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

	public TwitterLiveSearch getTwitter() {
		return twitter;
	}

	public void setTwitter(TwitterLiveSearch twitter) {
		this.twitter = twitter;
	}
}
