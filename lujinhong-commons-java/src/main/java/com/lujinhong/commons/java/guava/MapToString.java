package com.lujinhong.commons.java.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.Map;

/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 17/1/10 10:29
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION:
 */
public class MapToString {
    private static Map<String, String> udidGenderMap = new HashMap<>();

    public static void main(String[] args) {
        udidGenderMap.put("lujinhong", "good");
        udidGenderMap.put("ljh","perfect");
        udidGenderMap.put("ljhn1829","excellent");
        String udidGenderMapToString = Joiner.on(";").withKeyValueSeparator("=").join(udidGenderMap);
        System.out.println(udidGenderMapToString);

        Map<String, String> map = Splitter.on(";").withKeyValueSeparator("=").split(udidGenderMapToString);
        for(Map.Entry entry : map.entrySet()){
            System.out.println(entry.getKey() + "===" + entry.getValue());
        }
    }

}
