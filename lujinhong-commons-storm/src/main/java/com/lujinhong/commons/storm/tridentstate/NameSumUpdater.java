package com.lujinhong.commons.storm.tridentstate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.tuple.TridentTuple;

public class NameSumUpdater extends BaseStateUpdater<NameSumState> {

	private static final long serialVersionUID = -6108745529419385248L;
	public void updateState(NameSumState state, List<TridentTuple> tuples, TridentCollector collector) {
		Map<String,Integer> map=new HashMap<String,Integer>();
        for(TridentTuple t: tuples) {
        	map.put(t.getString(0), t.getInteger(1));
        }
        state.setBulk(map);
    }
	

	
}