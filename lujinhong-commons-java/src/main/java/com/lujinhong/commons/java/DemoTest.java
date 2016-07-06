package com.lujinhong.commons.java;

import java.util.HashMap;
import java.util.Map;

/**
* date: 2016年6月27日 上午10:21:22
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年6月27日 上午10:21:22
*/

public class DemoTest {
	
	
	public static void main(String[] args){
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		System.out.println(map.get("b"));
		System.out.println(map.get("d"));
		
		
	}

}
