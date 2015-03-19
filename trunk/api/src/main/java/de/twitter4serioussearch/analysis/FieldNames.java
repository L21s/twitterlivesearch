package de.twitter4serioussearch.analysis;

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
