package twitterTest.de.twittertest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KeywordHolder {
	private Map<String, Map<String, TweetListener>> keywords = Collections
			.synchronizedMap(new HashMap<String, Map<String, TweetListener>>());

	public Map<String, Map<String, TweetListener>> getKeywords() {
		return keywords;
	}

	public void putKeyword(String keyword, String sessionId,
			TweetListener actionListener) {
		Map<String, TweetListener> listeners = keywords.get(keyword);
		if (listeners == null) {
			listeners = Collections
					.synchronizedMap(new HashMap<String, TweetListener>());
			listeners.put(sessionId, actionListener);
			keywords.put(keyword, listeners);
			return;
		}
		listeners.put(sessionId, actionListener);
	}

	public void removeKeyword(String keyword, String sessionId,
			TweetListener actionListener) {
		Map<String, TweetListener> listeners = keywords.get(keyword);
		if (listeners == null) {
			return;
		}
		listeners.remove(sessionId);
	}
}
