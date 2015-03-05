package twitterTest.de.twittertest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeywordHolder {
	private Map<String, List<TweetListener>> keywords = Collections
			.synchronizedMap(new HashMap<String, List<TweetListener>>());

	public Map<String, List<TweetListener>> getKeywords() {
		return keywords;
	}

	public void putKeyword(String keyword, TweetListener actionListener) {
		List<TweetListener> listeners = keywords.get(keyword);
		if (listeners == null) {
			listeners = new ArrayList<TweetListener>();
			listeners.add(actionListener);
			keywords.put(keyword, listeners);
			return;
		}
		listeners.add(actionListener);
	}
}
