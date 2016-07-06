package com.lujinhong.commons.java.disruptor;

import com.lmax.disruptor.EventFactory;

/**
* date: 2016年7月5日 下午5:23:35
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年7月5日 下午5:23:35
*/

public class LongEventFactory implements EventFactory<LongEvent>{

	@Override
	public LongEvent newInstance() {
		return new LongEvent();
	}
	

}
