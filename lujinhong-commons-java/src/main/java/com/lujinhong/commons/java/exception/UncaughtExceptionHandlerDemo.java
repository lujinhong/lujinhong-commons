package com.lujinhong.commons.java.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* date: 2016年6月15日 上午10:54:41
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年6月15日 上午10:54:41
*/

public class UncaughtExceptionHandlerDemo {
	private static Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandlerDemo.class);

	public static void main(String[] args) {
		
		setupDefaultUncaughtExceptionHandler();
		//NO argument pass to main, so ArrayIndexOutOfBoundsException will occur.
		System.out.println(args[1]);
	}
	
    public static void setupDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread thread, Throwable thrown) {
                    try {
                        handleUncaughtException(thrown);
                    } catch (Error err) {
                       // LOG.error("Received error in main thread.. terminating server...", err);
                        System.err.println("Received error in main thread.. terminating server..." + err);
                        Runtime.getRuntime().exit(-2);
                    }catch(Exception e){
                    	System.err.println("Exception happen. Cause:  " + e.getMessage());
                    	e.printStackTrace();
                    }
                }
            });
    }
    
    
    public static void handleUncaughtException(Throwable t) throws Exception {
        if (t != null && t instanceof Error) {
            if (t instanceof OutOfMemoryError) {
                try {
                    System.err.println("Halting due to Out Of Memory Error..." + Thread.currentThread().getName());
                } catch (Throwable err) {
                    //Again we don't want to exit because of logging issues.
                }
                Runtime.getRuntime().halt(-1);
            } else {
                //Running in daemon mode, we would pass Error to calling thread.
                throw (Error) t;
            }
        }else{
        	throw (Exception) t;
        }
        
    }

}
