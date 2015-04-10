package language;


import twitter4j.Status;
import de.twitter4serioussearch.api.TweetListener;
import de.twitter4serioussearch.api.TwitterLiveSearch;
import de.twitter4serioussearch.api.TwitterLiveSearchFactory;

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
