package com.lujinhong.commons.java.disruptor;

/**
* date: 2016年7月5日 下午5:22:10
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年7月5日 下午5:22:10
*/

public class LongEvent {
	private long _value;
	public void set(long value){
		_value = value;
	}
	public long getValue(){
		return _value;
	}

}
