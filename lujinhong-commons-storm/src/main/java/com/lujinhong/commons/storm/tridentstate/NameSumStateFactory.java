package com.lujinhong.commons.storm.tridentstate;

import java.util.Map;

import backtype.storm.task.IMetricsContext;
import storm.trident.state.State;
import storm.trident.state.StateFactory;



public class NameSumStateFactory implements StateFactory {
	
	private static final long serialVersionUID = 8753337648320982637L;

	@Override
	public State makeState(Map arg0, IMetricsContext arg1, int arg2, int arg3) {
		return new NameSumState();  
	} 
}