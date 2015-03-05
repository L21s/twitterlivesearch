package twitter;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

import twitter4j.Status;
import twitterTest.de.twittertest.Twitter4Serioussearch;

@Singleton
public class TwitterMaschine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5748078495621149611L;
	private Twitter4Serioussearch twitter;
	
	@PostConstruct
	public void init() {
		try {
			this.setTwitter(new Twitter4Serioussearch());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void destroy() {
		
	}

	public Twitter4Serioussearch getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter4Serioussearch twitter) {
		this.twitter = twitter;
	}
	
	 public static JsonObject createTweetMessage(Status tweet) {
	        JsonProvider provider = JsonProvider.provider();
	        JsonObject addMessage = provider.createObjectBuilder()
	                .add("action", "new_tweet")
	                .add("text", tweet.getText())
	                .build();
	        return addMessage;
	    }
}
