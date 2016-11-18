package com.lujinhong.commons.java.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;


/**
 * date: 2016年10月27日 下午3:02:58
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年10月27日 下午3:02:58
 */

public class FileHanderReleaseDemo {
	//private static Logger LOG = LoggerFactory.getLogger(FileHanderReleaseDemo.class);

	public static void main(String[] args) throws Exception {
		HashSet<BufferedOutputStream> bws = new HashSet<BufferedOutputStream>();

		for (int i = 0; i < 1000; i++) {
			BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("/home/ljhn1829/000" + i));
			bw.write(1);
			bw.flush();
			bws.add(bw);
		}
		// for(BufferedWriter bw: bws){
		// bw.close();
		// }
		//LOG.info("WRITE FINISH, sleep.");
		System.out.println("WRITE FINISH, sleep.");
		Thread.sleep(60000);

		for (BufferedOutputStream bw : bws) {
			bw.close();
		}

		System.out.println("CLOSE FINISH, sleep");
		//LOG.info("CLOSE FINISH, sleep");
		Thread.sleep(60000);

	}

}
