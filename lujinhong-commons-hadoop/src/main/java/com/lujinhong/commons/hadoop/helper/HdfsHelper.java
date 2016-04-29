/**
 * 
 */
package com.lujinhong.commons.hadoop.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;


/**
* date: 2016年4月20日 上午10:32:31
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年4月20日 上午10:32:31
*/

public class HdfsHelper {
	
	private static HdfsHelper helper = null;
	private static Configuration _conf = null;
	private static Logger LOG = LoggerFactory.getLogger(HdfsHelper.class);
	
	public static HdfsHelper getHelper(Configuration conf){
		if(helper == null){
			helper = new HdfsHelper(_conf);
		}
		return helper;
	}
	
	private HdfsHelper(Configuration conf){
		_conf = conf;
	};
	
	//获取一个目录的du大小，以byte为单位
	public long getDirSize(String dir) throws IOException {
		Long blockSize = 0L;
		FileSystem fs = FileSystem.get(URI.create(dir), _conf);
		FileStatus[] stats =  fs.listStatus(new Path(dir));
		
		if (stats==null || stats.length ==0) {  
	          LOG.error("Cannot access " + dir +   
	              ": No such file or directory.");  
	          throw new FileNotFoundException("Cannot access " + dir +   
	                  ": No such file or directory."); 
	        }  
		
		for(FileStatus stat: stats){
			blockSize += stat.getLen();
		}
		return blockSize;
	}

}
