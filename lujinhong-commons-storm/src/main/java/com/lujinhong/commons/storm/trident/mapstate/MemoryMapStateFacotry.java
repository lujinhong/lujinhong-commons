package com.lujinhong.commons.storm.trident.mapstate;

import java.util.Map;

import backtype.storm.task.IMetricsContext;
import storm.trident.state.State;
import storm.trident.state.StateFactory;
import storm.trident.state.TransactionalValue;
import storm.trident.state.map.IBackingMap;
import storm.trident.state.map.TransactionalMap;

public class MemoryMapStateFacotry implements StateFactory{

	@Override
	public State makeState(Map conf, IMetricsContext metrics,
			int partitionIndex, int numPartitions) {
		return TransactionalMap.build((IBackingMap<TransactionalValue>) new MemoryMapStateBacking());
	}

}
