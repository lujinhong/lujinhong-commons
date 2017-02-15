package com.lujinhong.commons.hadoop.fs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.net.URI;

/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 17/1/11 11:32
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION:示范如何读取以snappy格式压缩的。虽然没指定压缩格式，但成功解压了。
 */
public class SequenceSnappyFileReader {
    public static void main(String[] args) throws IOException {
        String uri = args[0];
        Configuration conf = new Configuration();
        Path path = new Path(uri);
        SequenceFile.Reader reader = null;
        try {
            SequenceFile.Reader.Option filePath = SequenceFile.Reader.file(path);
            reader = new SequenceFile.Reader(conf, filePath);
            Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
            //long position = reader.getPosition();
            while (reader.next(key, value)) {
                //同步记录的边界
                //String syncSeen = reader.syncSeen() ? "*" : "";
                //System.out.printf("[%s%s]\t%s\t%s\n", position, syncSeen, key, value);
                System.out.println( value);
                //position = reader.getPosition(); // beginning of next record
            }
        } finally {
            IOUtils.closeStream(reader);
        }
    }
}


