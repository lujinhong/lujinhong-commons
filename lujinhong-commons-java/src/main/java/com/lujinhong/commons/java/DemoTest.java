package com.lujinhong.commons.java;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
* date: 2016年6月27日 上午10:21:22
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年6月27日 上午10:21:22
*/

public class DemoTest {
	
	
	public static void main(String[] args) throws Exception{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		System.out.println(map.get("b"));
		System.out.println(map.get("d"));
		
		Files.createSymbolicLink(Paths.get("/Users/liaoliuqing/Downloads/7.lnk"),
				Paths.get("/Users/liaoliuqing/Downloads/7.txt"));
		
		new BufferedOutputStream(
				new FileOutputStream(new File("/Users/liaoliuqing/Downloads/7.txt"),true),65536);
		
		
	}

}
