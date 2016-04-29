package com.lujinhong.commons.storm.hook;

import backtype.storm.hooks.BaseTaskHook;
import backtype.storm.hooks.info.BoltAckInfo;
import backtype.storm.hooks.info.BoltExecuteInfo;

public class TraceTaskHook extends BaseTaskHook {
	
	@Override
	public void boltExecute(BoltExecuteInfo info) {
		super.boltExecute(info);
		System.out.println("executingTaskId:" + info.executingTaskId);
		System.out.println("executedLatencyMs:" + info.executeLatencyMs);
		System.out.println("execute msg:" + info.tuple.getString(0));
	}
	
	@Override
	public void boltAck(BoltAckInfo info) {
		super.boltAck(info);
		System.out.println("ackingTaskId:" + info.ackingTaskId);
		System.out.println("processLatencyMs:" + info.processLatencyMs);
		System.out.println("ack msg:" + info.tuple.getString(0));
	}

}
