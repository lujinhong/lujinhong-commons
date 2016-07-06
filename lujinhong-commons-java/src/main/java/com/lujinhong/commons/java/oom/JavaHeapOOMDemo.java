package com.lujinhong.commons.java.oom;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
* date: 2016年6月15日 下午3:09:40
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年6月15日 下午3:09:40
*/

public class JavaHeapOOMDemo {
	public static void main(String[] args) {
		List<Integer> list = new LinkedList<Integer>();
		Random random  = new Random();
		
		while(true){
			list.add(new Integer(random.nextInt()));
		}
	}
}
