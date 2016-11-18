/**
 * 
 */
package com.lujinhong.commons.java.collection;

import java.util.Comparator;
import java.util.TreeSet;

/**
* date: 2016年3月22日 上午9:21:12
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年3月22日 上午9:21:12
*/

public class TreeSetComparatorDemo {

	public static void main(String[] args) {
		//默认情况下，String根据字符的ASCII码排序
		TreeSet<String> set1 = new TreeSet<String>();
		set1.add("jason");
		set1.add("jim");
		set1.add("tom");
		set1.add("lucy");
		for(String s : set1){
			System.out.print(s + "\t");
			System.out.println();
			System.out.println();
		}

		
		System.out.println();
		
		//改变set2中的String元素默认排序方式，使用字符串长度比较。当长度相同时，使用String的默认排序方法，即字母顺序
		TreeSet<String> set2 = new TreeSet<String>(new Comparator<Object>(){
			@Override
			public int compare(Object o1, Object o2) {
				if(o1 instanceof String && o1 instanceof String){
					String s1 = o1.toString();
					String s2 = o2.toString();
					//注意， 如果这样定义，则对于长度相同的String会被认为同一对对象，下面例子中的tom会被认为和jim是一样的，因此不会被加入set2中。
					//return s1.length() - s2.length();
					if(s1.length() - s2.length() != 0){
						return s1.length() - s2.length();
					}else{
						return s1.compareTo(s2);
					}
				}
				return 0;
			}
			
		});
		set2.add("hhhhhh");
		set2.add("hi");
		set2.add("jason");
		set2.add("jim");
		set2.add("tom");
		set2.add("lucy");
		for(String s : set2){
			System.out.print(s + "\t");
		}
	}
}
