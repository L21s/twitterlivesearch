package twitterTest.de.twittertest;

public class IdGenerator {
	private Integer id = Integer.MIN_VALUE;

	public synchronized int getId() {
		id++;
		System.out.println("gebe id aus:" + id);
		return id;
	}
}
