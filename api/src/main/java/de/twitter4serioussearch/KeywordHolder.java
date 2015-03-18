package de.twitter4serioussearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.twitter4serioussearch.common.Util;

public class KeywordHolder {
	// mapping von query zu map<sessionId, Listener>
	private Map<String, Map<String, TweetListener>> keywords = Collections
			.synchronizedMap(new HashMap<>());
	// mapping von sessionId zu List<keyword>
	private Map<String, List<String>> queriesForSessions = Collections
			.synchronizedMap(new HashMap<>());

	public Map<String, Map<String, TweetListener>> getKeywords() {
		return keywords;
	}

	/**
	 * Registriert einen {@link de.twitter4serioussearch.TweetListener
	 * TweetListener} für die Kombination aus Query und Session
	 *
	 * @param query
	 *            Vom User gesuchter String
	 * @param sessionId
	 *            eindeutiger Session Identifier (Hintergrund: Die gleiche Query
	 *            kann von mehreren Usern registriert werden)
	 * @param actionListener
	 *            {@link de.twitter4serioussearch.TweetListener TweetListener}
	 *            der invoked wird, sobald ein zum query passender Tweet
	 *            empfangen wurde
	 */
	public void registerQuery(String query, String sessionId,
			TweetListener actionListener) {
		Map<String, TweetListener> listeners = keywords.get(query);
		if (listeners == null) {
			listeners = Collections
					.synchronizedMap(new HashMap<String, TweetListener>());
			listeners.put(sessionId, actionListener);
			keywords.put(query, listeners);
			return;
		}
		listeners.put(sessionId, actionListener);
		List<String> keywordsForSession = queriesForSessions.get(sessionId);
		if (keywordsForSession == null) {
			keywordsForSession = new ArrayList<>();
			keywordsForSession.add(query);
			queriesForSessions.put(sessionId, keywordsForSession);
		}
		keywordsForSession.add(query);
	}

	/**
	 * Deregistriert ein Query für die gegebene Session
	 *
	 * @param query
	 *            zu unregistrierendes Query
	 * @param sessionId
	 *            eindeutiger Session Identifier (Hintergrund: Die gleiche Query
	 *            kann von mehreren Usern registriert werden)
	 */
	public void unregisterQuery(String query, String sessionId) {
		Map<String, TweetListener> listeners = keywords.get(query);
		Util.safe(listeners).remove(sessionId);
	}

	/**
	 * Deregistriert alle querys für eine gegebene session
	 *
	 * @param sessionId
	 *            eindeutiger Session Identifier
	 */
	public void unregisterSession(String sessionId) {
		for (String query : Util.safe(queriesForSessions.get(sessionId))) {
			unregisterQuery(query, sessionId);
		}
		queriesForSessions.remove(sessionId);
	}
}
