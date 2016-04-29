/**
 * 
 */
package com.lujinhong.commons.java.hashcode;

import java.util.Objects;

/**
 * date: 2016年3月16日 下午12:30:34
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年3月16日 下午12:30:34
 */

public class Employee {
	private String name;
	private String phone;
	private String address;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			Employee other = (Employee) obj;
			return (this.name.equals(other.name) && this.phone.equals(phone));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 7*name.hashCode() + 11*phone.hashCode();
	}
	
	public int hashCode2() {
		return 7* Objects.hash(name) + 11* Objects.hash(phone);
	}
	
	public int hashCode3() {
		return Objects.hash(name,phone);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
