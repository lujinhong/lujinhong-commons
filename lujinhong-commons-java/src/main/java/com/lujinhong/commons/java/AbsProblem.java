package com.lujinhong.commons.java;

/**
 * (Math.abs(Integer.MAX_VALUE)返回的是Integer.MAX_VALUE，即绝对值不生效，返回的还是负数。
 * 输出：
 * 2147483647
 * -2147483648
 * -2147483648
 * 2147483647
 */

public class AbsProblem {

	public static void main(String[] args) {
		
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MIN_VALUE);
	
		System.out.println(Math.abs(Integer.MIN_VALUE));
		System.out.println(Math.abs(Integer.MAX_VALUE));
		//System.out.println(Math.abs(-200));ss

	}

}
