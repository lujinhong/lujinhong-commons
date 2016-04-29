/**
 * 
 */
package com.lujinhong.commons.java.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
* date: 2016年3月20日 下午9:40:41
* @author LUJINHONG lu_jin_hong@163.com
* last modified: 2016年3月20日 下午9:40:41
*/

public class CollectionBasicDemo {

	public static void main(String[] args) {
		
		Collection<String> students = new LinkedList<String>();
		students.add("Jason");
		students.add("Jim");
		students.add("Tom");
		
		printCollection(students);
		printCollection2(students);
		

	}

	private static void printCollection(Collection<String> students) {
		Iterator<String> it = students.iterator();
		while(it.hasNext()){
			String student = it.next();
			System.out.println(student);
		}
	}
	
	private static void printCollection2(Collection<String> students) {
		for(String student : students){
			System.out.println(student);
		}
	}

}
