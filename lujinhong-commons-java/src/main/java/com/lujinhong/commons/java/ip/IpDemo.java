/**
 * 
 */
package com.lujinhong.commons.java.ip;

import java.io.File;
import java.net.InetAddress;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;

/**
 * date: 2016年3月16日 下午6:12:12
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年3月16日 下午6:12:12
 */

public class IpDemo {
	public static void main(String[] args) throws Exception {
		File database = new File("resources/ip.mmdb");
		Reader reader = new Reader(database);
		InetAddress address = InetAddress.getByName("24.24.24.24");

		JsonNode response = reader.get(address);
		System.out.println(response);

		reader.close();
	}
}
