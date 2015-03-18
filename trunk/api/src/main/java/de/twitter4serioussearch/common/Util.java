package de.twitter4serioussearch.common;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Util {
	public static <T> List<T> safe(List<T> collection) {
		return collection == null ? Collections.emptyList() : collection;
	}

	public static <V, K> Map<V, K> safe(Map<V, K> collection) {
		return collection == null ? Collections.emptyMap() : collection;
	}

}
