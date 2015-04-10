package language;


import twitter4j.Status;
import de.twitterlivesearch.api.TweetListener;
import de.twitterlivesearch.api.TwitterLiveSearch;
import de.twitterlivesearch.api.TwitterLiveSearchFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	static TwitterLiveSearch twitter;
	
    public static void main( String[] args )
    {
    	twitter = TwitterLiveSearchFactory.build();
		twitter.registerQuery("Hallo", "1", new TweetListener() {
			
			@Override
			public void handleNewTweet(Status tweet) {
				System.out.println("listener:" + tweet);
				
			}
		});
		
    }
}
