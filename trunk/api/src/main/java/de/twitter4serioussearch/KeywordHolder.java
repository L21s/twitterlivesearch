package de.twitter4serioussearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeywordHolder {
	// mapping von keyword zu map<sessionId, Listener>
	private Map<String, Map<String, TweetListener>> keywords = Collections
			.synchronizedMap(new HashMap<>());
	// mapping von sessionId zu List<keyword>
	private Map<String, List<String>> keywordsForSessions = Collections
			.synchronizedMap(new HashMap<>());

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
		List<String> keywordsForSession = keywordsForSessions.get(sessionId);
		if (keywordsForSession == null) {
			keywordsForSession = new ArrayList<>();
			keywordsForSession.add(keyword);
			keywordsForSessions.put(sessionId, keywordsForSession);
		}
	}

	public void removeKeyword(String keyword, String sessionId) {
		Map<String, TweetListener> listeners = keywords.get(keyword);
		if (listeners == null) {
			return;
		}
		listeners.remove(sessionId);
	}

	public void removeSession(String sessionId) {
		// TODO Auto-generated method stub

	}
}
