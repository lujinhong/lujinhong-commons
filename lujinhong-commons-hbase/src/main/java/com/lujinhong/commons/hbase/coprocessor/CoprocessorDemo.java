package com.lujinhong.commons.hbase.coprocessor;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.util.Bytes;

/**
* date: 2016年5月20日 下午2:53:51
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年5月20日 下午2:53:51
*/

public class CoprocessorDemo extends BaseRegionObserver{
	
	public static final byte[] FIXED_ROW = Bytes.toBytes("@@@GETTIME@@@");

	@Override
	public void preGetOp(ObserverContext<RegionCoprocessorEnvironment> e, Get get, List<Cell> results)
			throws IOException {
		if(Bytes.equals(get.getRow(), FIXED_ROW)){
			KeyValue kv = new KeyValue(get.getRow(),FIXED_ROW,FIXED_ROW,Bytes.toBytes(System.currentTimeMillis()) );
			results.add(kv);
		}
	}

}
