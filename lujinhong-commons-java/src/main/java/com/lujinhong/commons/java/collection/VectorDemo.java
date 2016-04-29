/**
 * 
 */
package com.lujinhong.commons.java.collection;

import java.util.Vector;

/**
* date: 2016年3月24日 上午10:04:10
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年3月24日 上午10:04:10
*/

public class VectorDemo {

	public static void main(String[] args) {
		
		Vector<String> vector = new Vector<String>();
		vector.add("jason");
		//在vector的最后添加这个元素
		vector.add("jim");
		//在指定的index位置添加这个元素，后面的元素依次向后移。
		vector.add("lucy");
		System.out.println(vector);
		System.out.println(vector.size());
		System.out.println(vector.capacity());
		System.out.println(vector.firstElement());
		System.out.println(vector.lastElement());
		System.out.println(vector.elementAt(2));
		System.out.println(vector.get(2));
		vector.remove(2);
		System.out.println(vector.size());
		System.out.println(vector.capacity());
		vector.clear();
		System.out.println(vector.size());
		System.out.println(vector.capacity());
	}

}
