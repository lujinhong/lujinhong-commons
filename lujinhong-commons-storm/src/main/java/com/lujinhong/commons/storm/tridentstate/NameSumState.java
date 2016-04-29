package com.lujinhong.commons.storm.tridentstate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import storm.trident.state.State;

public class NameSumState implements State {


	// 此处将state记录在一个HashMap中，如果需要记录在其它地方，如mysql，则使用jdbc写入mysql代替下面的map操作即可。
	public Map<String, Integer> map = new HashMap<String, Integer>();

	public void setBulk(Map<String, Integer> map) {
		// 将新到的tuple累加至map中
		for (Entry<String, Integer> entry : map.entrySet()) {
			String key = entry.getKey();
			if (this.map.containsKey(key)) {
				this.map.put(key, this.map.get(key) + map.get(key));
			} else {
				this.map.put(key, entry.getValue());
			}
		}
		System.out.println("-------");
		// 将map中的当前状态打印出来。
		for (Entry<String, Integer> entry : this.map.entrySet()) {
			String Key = entry.getKey();
			Integer Value = entry.getValue();
			System.out.println(Key + "|" + Value);
		}
	}

	@Override
	public void beginCommit(Long arg0) {
		System.out.println("beginCommit--" + arg0);

	}

	@Override
	public void commit(Long arg0) {
		System.out.println("commit--" + arg0);

	}

}