package com.lujinhong.commons.java;

import org.eclipse.jdt.internal.core.SourceType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 16/11/14 16:51
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION:
 */
public class SmallFunctionDemo {

    public static void main(String[] args) {
        binaryNumberDemo();
        stringSwitchDemo("CCB");
       // multiExceptionHandler();
        tryWithResource();
        binaryNumberDemo();
        bigNumberSeperator();
    }

    private static void binaryNumberDemo() {
        int a = 0b0101;
        System.out.println(a); //5
    }

    private static void stringSwitchDemo(String key) {
        switch (key) {
            case "ICBC":
                System.out.println("工商银行");
                break;
            case "ABC":
                System.out.println("农业银行");
                break;
            case "CCB":
                System.out.println("建设银行");
                break;
            case "BOC":
                System.out.println("中国银行");
                break;
            default:
                System.out.println("招商银行");
        }
    }

    private static void multiExceptionHandler() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/User/lujinhong/Downloads/1.txt"));
            int[] array = new int[5];
            array[6] = 9;
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private static void tryWithResource(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("/User/lujinhong/Downloads/1.txt"));){
            bw.write("k");
        }catch(IOException e){
        }
    }

    private static void bigNumberSeperator(){
        long i = 100_000_000_000L;
        System.out.println(i+"");
    }

    private static Set<String> set = new HashSet<>();
}
