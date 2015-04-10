# twitterlivesearch

## Introduction
This library was actually designed for a university purpose: the main idea is to provide a live search through a Twitter user stream or the Gardenhose. This functionality is achieved by storing the last n (you can configure it...) tweets in a Apache Lucene index. As a user of this library you can register keywords you are interested in and the library will inform you in case a keyword-matching tweet is incoming. We actually designed it generally; its main purpose is the usage in a search engine (http://serioussearch.de), though.

## How to include it?
To use the library either package it via maven and add it to your classpath or directly use it as a maven dependency:
```
<dependency>
		<groupId>twitterlivesearch</groupId>
		<artifactId>de.twitterlivesearch</artifactId>
		<version>{CURRENT_VERSION}</version>
		<scope>compile</scope>
</dependency>
```
The current version is: 0.1-SNAPSHOT

## How to use it?
First of all you need to make sure that you have a twitter account and you have OAuth credentials for it. You can find this instructions by simply googling it.
In your project you need two different configuration files:

*twitter4j.properties*
```
debug=false
oauth.consumerKey=
oauth.consumerSecret=
oauth.accessToken=
oauth.accessTokenSecret=
```

*twitterlivesearch.properties*
You can find instructions how to configure this API in /de.twitterlivesearch.api.configuration.build.AbstractConfiguration/ class. 


Use of the API itself is really simple. You need a main class which builds the TwitterLiveSearch object by making use of the TwitterLiveSearchFactory. 

```
public class App 
{
	static TwitterLiveSearch twitter;
	
    public static void main( String[] args )
    {
    	twitter = TwitterLiveSearchFactory.build();
		  twitter.registerQuery("MyRegisteredKeyWord", "1", new TweetListener() {
  			@Override
  			public void handleNewTweet(Status tweet) {
  				System.out.println("A new Tweet is incoming :" + tweet);
  				
  			}
		});
		
    }
}
```

As you can see above: an instance of TwitterLiveSearch is build by the factory. TwitterLiveSearch provides a method to register a keyword. In this case the first argument "MyRegisteredKeyWord" is the keyword, the second argument "1" is the unique session id and the third argument is the Listener, which is invoked when a new tweet that matches the keyword is incoming.
