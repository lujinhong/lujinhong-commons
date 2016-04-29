package com.lujinhong.commons.storm.wordcount;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class ReportBolt extends BaseRichBolt{
	private Map<String, Long> counts;

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.counts = new HashMap<String,Long>();
	}
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

	public void execute(Tuple input) {
		String word = input.getStringByField("word");
		Long count = input.getLongByField("count");
		counts.put(word, count);
	}

	public void cleanup() {
		System.out.println("Final output");
		Iterator<Entry<String, Long>> iter = counts.entrySet().iterator();
		while (iter.hasNext()) {
		    Entry<String, Long> entry = iter.next();
		    String word = (String) entry.getKey();
		    Long count = (Long) entry.getValue();
		    System.out.println(word + " : " + count);
		} 
		
		super.cleanup();
	}	
	
}
