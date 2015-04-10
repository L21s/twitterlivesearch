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
package de.twitterlivesearch.api.configuration.management;

/** 
 * Will be thrown in case a value to an existing property does not exist.
 * @see ConfigurationKey
 * @see ConfigurationValues
 * @author schmitzhermes
 *
 */
public class ConfigurationException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5767334559140627960L;
	
	public ConfigurationException(String s) {
		super(s);
	}
	
	public ConfigurationException(String s, Throwable t) {
		super(s,t);
	}

}
