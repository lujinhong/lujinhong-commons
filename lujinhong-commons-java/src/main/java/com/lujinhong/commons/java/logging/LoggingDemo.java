/**
 * 
 */
package com.lujinhong.commons.java.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
* date: 2016年4月1日 下午4:45:32
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年4月1日 下午4:45:32
*/

public class LoggingDemo {
	
    private static final Logger logger = Logger.getLogger("org.jediael.crawl.MyCrawler");

	public static void main(String[] args) {
		
		logger.setLevel(Level.WARNING);
		logger.info("info");
		logger.fine("debug");
		logger.warning("warn");
		logger.severe("error");

	}

}
