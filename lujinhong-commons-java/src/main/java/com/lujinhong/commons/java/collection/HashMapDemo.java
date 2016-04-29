package com.lujinhong.commons.java.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * stackoverflow上推荐的遍历hashmap的最佳方法。
 */

public class HashMapDemo {

	public static void main(String[] args) {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		//获取键集，值集合（不是集，因为有可能重复），键/值对集的方法
		map.keySet();
		map.values();
		map.entrySet();
		//打印map的2种方法。
		printMap2(map);
		printMap(map);

	}
	
	public static void printMap(Map<String,String> mp) {
	    Iterator<Map.Entry<String, String>> it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String,String> pair = it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}

	private static void printMap2(Map<String, String> mp) {
		//另一种类似的方法
	    for(Map.Entry<String, String> entry : mp.entrySet()){
	    		String key = entry.getKey();
	    		String value = entry.getValue();
	    		System.out.println(key + " = " + value);
	    }
	}

}
