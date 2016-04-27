/*
 * Copyright 2002-2004 the original author or authors.
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

package org.springframework.beans;


import lombok.EqualsAndHashCode;


/**
 * Simple nested test bean used for testing bean factories, AOP framework etc.
 * @author  Trevor D. Cook
 * @since 30-Sep-2003
 */
@EqualsAndHashCode
public class NestedTestBean implements INestedTestBean {

	String company = "";

	public NestedTestBean() {
	}

	public NestedTestBean(String company) {
		setCompany(company);
	}

	/**
	 * Get the company Setter for property age.
	 * 
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Set the company
	 * 
	 * @param company the company
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @see Object#equals(Object)
	 */
	/*public boolean equals(Object obj) {
		if (!(obj instanceof NestedTestBean)) {
			return false;
		}
		NestedTestBean ntb = (NestedTestBean) obj;
		return new EqualsBuilder().append(company, ntb.company).isEquals();
	}*/

	/**
	 * @see Object#hashCode()
	 */
	/*public int hashCode() {
		return new HashCodeBuilder(23, 91).append(company).toHashCode();
	}*/

}
