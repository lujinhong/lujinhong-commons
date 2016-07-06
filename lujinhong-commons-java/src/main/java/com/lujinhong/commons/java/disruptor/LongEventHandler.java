package com.lujinhong.commons.java.disruptor;

import com.lmax.disruptor.EventHandler;

/**
* date: 2016年7月5日 下午5:26:59
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年7月5日 下午5:26:59
*/

public class LongEventHandler implements EventHandler<LongEvent> {

	@Override
	public void onEvent(LongEvent event, long sequent, boolean endOfBatch) throws Exception {
		System.out.println("Dealing " + event.getValue());
	}

}
