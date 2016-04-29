/**
 * 
 */
package com.lujinhong.commons.java.autoclosable;

/**
 * date: 2016年2月29日 下午4:27:51
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年2月29日 下午4:27:51
 */

public class TestAutoColseable {
	public static void main(String[] args) {
		try (MyResource mr = new MyResource()) {
			mr.doSomething();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}
}
