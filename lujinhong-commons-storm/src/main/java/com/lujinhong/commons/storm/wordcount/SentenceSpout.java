package com.lujinhong.commons.storm.wordcount;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class SentenceSpout extends BaseRichSpout {
	private SpoutOutputCollector collector;
	private int index = 0;
	private String[] sentences = { "when i was young i'd listen to the radio",
			"waiting for my favorite songs", "when they played i'd sing along",
			"it make me smile",
			"those were such happy times and not so long ago",
			"how i wondered where they'd gone",
			"but they're back again just like a long lost friend",
			"all the songs i love so well", "every shalala every wo'wo",
			"still shines.", "every shing-a-ling-a-ling",
			"that they're starting", "to sing so fine"};

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

	public void nextTuple() {
		this.collector.emit(new Values(sentences[index]));
		index++;
		if (index >= sentences.length) {
			index = 0;
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

}
