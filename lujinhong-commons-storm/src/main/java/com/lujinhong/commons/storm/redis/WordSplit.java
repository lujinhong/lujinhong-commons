package com.lujinhong.commons.storm.redis;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class WordSplit extends BaseFunction {

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String sentence = (String) tuple.getValue(0);
		if (sentence != null) {
			sentence = sentence.replaceAll("\r", "");
			sentence = sentence.replaceAll("\n", "");
			for (String word : sentence.split(" ")) {
				if (word.length() != 0)
					collector.emit(new Values(word));
			}
		}
	}
}
