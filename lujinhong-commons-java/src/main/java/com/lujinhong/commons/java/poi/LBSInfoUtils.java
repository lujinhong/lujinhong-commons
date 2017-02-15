package com.lujinhong.commons.java.poi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 17/1/17 10:25
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION: 将文件中的LBS信息load到一个map中，key为纬度+","+经度（其中经纬度只保留3位），value为LBS信息整行文本。
 */
public class LBSInfoUtils {
    private static final Logger LOG = LoggerFactory.getLogger(LBSInfoUtils.class);
    public static Map<String, String> lbsInfoMap = new HashMap<>();


    public static void main(String[] args) throws Exception {
        loadLBSCsvInfoToMap("/Users/liaoliuqing/Desktop/1");
        System.out.println("Size: " + lbsInfoMap.size());
        int count = 0;
        for (Map.Entry entry : lbsInfoMap.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
            count++;
            if (count > 10) break;
        }

    }


    public static Map<String, String>  loadLBSCsvInfoToMap(String dirName) throws Exception {
        List<File> fileList = getFileAbsolutePath(dirName);
        LOG.info("Loading {} file to memory...", fileList.size());
        for (File file : fileList) {

            if (file.getName().startsWith("酒店") || file.getName().startsWith("学校")) {
                loadLBSCsvInfoToMap(file, 5, 6);
            } else if (file.getName().startsWith("写字楼") || file.getName().startsWith("住房别墅")) {
                loadLBSCsvInfoToMap(file, 4, 5);
            }else if (file.getName().startsWith("综合商场")) {
                loadLBSCsvInfoToMap(file, 6, 7);
            } else if (file.getName().startsWith("其他")) {
                loadLBSCsvInfoToMap(file, 7, 8);
            } else {
                LOG.warn("Unkown LBS file: {}.", file.getName());
            }

        }
        return lbsInfoMap;

    }


    private static void loadLBSCsvInfoToMap(File file, int latitudeIndex, int longtitudeIndex) throws FileNotFoundException {
        LOG.info("Loading LBS info from {}...", file.getName());
        int count = 0;
        Scanner scanner = new Scanner(file);
        scanner.nextLine();//跳过标题行
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] strs = line.split(",");
            if (strs.length <= longtitudeIndex || strs[latitudeIndex].length() == 0 || strs[longtitudeIndex].length() == 0 || !strs[latitudeIndex].contains(".") || !strs[longtitudeIndex].contains(".")) {
                continue;
            }
            String mapKey = null;
            try {
                mapKey = String.format("%.3f", Double.parseDouble(strs[latitudeIndex])) + "," + String.format("%.3f", Double.parseDouble(strs[longtitudeIndex]));
            } catch (NumberFormatException e) {
                LOG.info("Error data : {}.", line);
                continue;
            }
            String originValue = lbsInfoMap.get(mapKey);
            if (null == originValue) {
                lbsInfoMap.put(mapKey, line);
            } else {
                lbsInfoMap.put(mapKey, line + "##$$##" + originValue);
            }
            count++;
        }
        LOG.info("Finish Loading {} LBS info from {}.", count, file.getName());
    }

    private static List<File> getFileAbsolutePath(String dirName) {
        List<File> files = new ArrayList<>();
        File dir = new File(dirName);
        if (dir.exists() && dir.isDirectory()) {
            File[] fileInDir = dir.listFiles();
            files = Arrays.asList(fileInDir);
        } else {
            LOG.warn("File {} Not Exists or not a directory.", dirName);
        }
        return files;
    }
//


}
