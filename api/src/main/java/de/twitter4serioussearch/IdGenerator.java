package de.twitter4serioussearch;

public class IdGenerator {
	private Integer id = Integer.MIN_VALUE;

	public synchronized int getId() {
		id++;
		return id;
	}
}
