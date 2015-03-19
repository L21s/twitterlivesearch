package de.twitter4serioussearch.model;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import de.twitter4serioussearch.api.TweetListener;
import de.twitter4serioussearch.filter.TweetFilter;

public class QueryManager {
	private static class Holder {
		static QueryManager instance = new QueryManager();
	}
	
	private Logger log = LogManager.getLogger();
	
	private ListMultimap<String, QueryWrapper> queryToQueryWrappers = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
		
	private ListMultimap<String, QueryWrapper> sessionToQueryWrappers = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
	
	public static QueryManager getInstance() {
		return Holder.instance;
	}
	
	private QueryManager() {}
	
	public Set<String> getQueries() {
		return queryToQueryWrappers.keySet();
	}
	
	public List<QueryWrapper> getQueryWrappersForQuery(String query) {
		return queryToQueryWrappers.get(query);
	}

	public void registerQuery(String query, String session, TweetListener listener, TweetFilter... filters) {
		QueryWrapper qw = new QueryWrapper();
		qw.setFilter(filters);
		qw.setListener(listener);
		qw.setQuery(query);
		qw.setSession(session);
		
		queryToQueryWrappers.put(query,qw);
		sessionToQueryWrappers.put(query, qw);
		
		if(log.isTraceEnabled()) {
			log.trace("Registered Query : " + query + " on Session " + session);
		}
	}
	
	public void unregisterQuery(String session, String query) {
		QueryWrapper toDelete = null;
		for(QueryWrapper qw : sessionToQueryWrappers.get(session)) {
			if(qw.getQuery().equals(query)) {
				toDelete = qw;
				break;
			}
		}
		
		sessionToQueryWrappers.get(session).remove(toDelete);
		queryToQueryWrappers.get(query).remove(toDelete);
	}
	
	public void unregisterSession(String session) {
		List<QueryWrapper> toDelete = sessionToQueryWrappers.get(session);
		
		for (QueryWrapper qw : toDelete) {
			queryToQueryWrappers.remove(qw.getQuery(), qw);
		}
		sessionToQueryWrappers.removeAll(session);
	}
	
}
