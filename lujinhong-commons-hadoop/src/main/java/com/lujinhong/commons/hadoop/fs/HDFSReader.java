package com.lujinhong.commons.hadoop.fs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

public class FileSystemDoubleCat {

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(fileName), conf);
        FSDataInputStream hdfsInStream = fs.open(new Path(fileName));

        String line;
        BufferedReader in =new BufferedReader(new InputStreamReader(hdfsInStream, "UTF-8"));
        while ((line = in.readLine()) != null) {

        }


    }

}
