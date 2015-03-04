package twitterTest.de.twittertest;

public class IdGenerator {
	private Integer id = Integer.MIN_VALUE;

	public synchronized Integer getId() {
		id++;
		return id;
	}
}
