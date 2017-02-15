package com.lujinhong.commons.java.lbs;

import java.util.HashMap;
import java.util.Map;

/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 17/2/15 16:56
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION:
 */
public class LBSUtils {

    //根据给定经纬度获取一个范围内的经纬度范围。
    public static Map<String, Double> getAround(Double latitude, Double longitude, Double raidusMile) {
        HashMap<String, Double> map = new HashMap();

        Double degree = (24901 * 1609) / 360.0; // 获取每度

        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180))+"").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLng = longitude - radiusLng;
        // 获取最大经度
        Double maxLng = longitude + radiusLng;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLat = latitude - radiusLat;
        // 获取最大纬度
        Double maxLat = latitude + radiusLat;

        map.put("minLat", minLat);
        map.put("maxLat", maxLat);
        map.put("minLng", minLng);
        map.put("maxLng", maxLng);

        return map;
    }


    //计算2个经纬度点之间的距离，单位为米。
    public static double getDistance(double lng1, double lat1, double lng2,
                                     double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (lng1 - lng2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }
}
