package de.twitter4serioussearch.common;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Simple Util Class providing NPE safe ways to access collections which could
 * be null from for each loops.
 *
 * @author tobiaslarscheid
 *
 */
public class CollectionsUtil {
	public static <T> List<T> safe(List<T> collection) {
		return collection == null ? Collections.emptyList() : collection;
	}

	public static <V, K> Map<V, K> safe(Map<V, K> collection) {
		return collection == null ? Collections.emptyMap() : collection;
	}

}
