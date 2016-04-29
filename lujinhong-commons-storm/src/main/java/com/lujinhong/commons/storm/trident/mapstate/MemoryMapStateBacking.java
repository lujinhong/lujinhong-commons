package com.lujinhong.commons.storm.trident.mapstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import storm.trident.state.map.IBackingMap;

public class MemoryMapStateBacking<T> implements IBackingMap<T> {
	
    Map<List<Object>, T> db = new HashMap<List<Object>, T>();
    @Override
    public List<T> multiGet(List<List<Object>> keys) {
        List<T> ret = new ArrayList();
        for (List<Object> key : keys) {
            ret.add(db.get(key));
        }
        return ret;
    }

    @Override
    public void multiPut(List<List<Object>> keys, List<T> vals) {
        for (int i = 0; i < keys.size(); i++) {
            List<Object> key = keys.get(i);
            //T val = Integer.parseInt(vals.get(i).toString()) + Integer.parseInt(db.get(key).toString());
            T val = vals.get(i);
            db.put(key, val);
        }
    }
}
