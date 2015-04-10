/*
 * Copyright 2015 Tobias Larscheid, Jan Schmitz-Hermes, Felix Nordhusen, Florian Scheil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.twitterlivesearch.analysis;

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
