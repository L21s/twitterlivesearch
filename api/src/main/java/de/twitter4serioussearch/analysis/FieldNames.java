package de.twitter4serioussearch.analysis;

/**
 * Enum containing the field names we use to store documents in the lucene
 * index.
 *
 * @author tobiaslarscheid
 *
 */
public enum FieldNames {
	ID("id"), TEXT("text");
	private String field;

	FieldNames(String field) {
		setField(field);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
