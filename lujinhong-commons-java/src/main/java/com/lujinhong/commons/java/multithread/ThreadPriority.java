/**
 * 本类示范了如何使用java线程的优先级。但切记，不要将程序的正常运行寄托于线程的优先级，因为每个操作系统
 * 对优先级的处理均不相同，有些操作系统甚至会将其忽略。
 */
package com.lujinhong.commons.java.multithread;

/**
 * @author lujinhong
 * @date 2015年6月22日 上午10:42:50
 * @Description: 
 */
public class ThreadPriority {

    public static void main(String[] args) {
        Runnable r = new MyThread();
        Thread t = new Thread(r,"t1");
        
        System.out.println(t.getPriority());//默认优先级为5
        System.out.println(Thread.MAX_PRIORITY);//最大优先级为10
        System.out.println(Thread.MIN_PRIORITY);//最小优先级为1
        
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
        
        Thread t2 = new Thread(r,"t2");
        t2.start();
        
        Thread t3 = new Thread(r,"t3");
        t3.setPriority(Thread.MAX_PRIORITY);
        t3.start();
    }
}

class MyThread implements Runnable{

    public void run() {
        for(int i = 0; i < 100; i++){
            System.out.println(Thread.currentThread().getName());
        }
    }
    
}
