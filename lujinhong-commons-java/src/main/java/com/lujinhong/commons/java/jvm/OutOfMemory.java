package com.lujinhong.commons.java.jvm;

import java.util.HashMap;
import java.util.Map;

public class OutOfMemory {

	private static Map map = new HashMap();

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("We have accumulated " + map.size()
						+ " entries");
			}
		});
		for (int i = 0;; i++) {
			map.put(Integer.toBinaryString(i), i);
		}
	}

}
