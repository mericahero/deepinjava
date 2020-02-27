package com.meric.deepinjava.resume.raw;

import lombok.val;
import sun.dc.pr.PRError;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class XThread {


    public static void main(String[] args) {
        //testJoin();
        //testTwoPrint();
        testPrint2();
    }

    private static void testJoin(){
        val t1=new Thread(()->{
            System.out.println("1");
            while (true){

            }

        });
        val t2=new Thread(()->{
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("2");
        });
        t1.start();
        t2.start();
    }



    private static void testPrint2(){

        Object o = new Object();


        val t1 = new Thread(()->{
            while(true) {
                synchronized (o){
                    System.out.println("苹果");
                    o.notify();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        val t2 = new Thread(()->{
            while(true) {
                synchronized (o){
                    System.out.println("香蕉");
                    o.notify();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        t1.start();
        t2.start();
    }


    int a=0;
    private static void testTwoPrint(){
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        XThread xThread = new XThread();

        val t1 = new Thread(()->{
            while(true) {
                lock.lock();
                try{
                    xThread.print(1);
                    condition.signal();
                    condition.await();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        });

        val t2 = new Thread(()->{

            while(true) {
                lock.lock();
                try{
                    xThread.print(2);
                    condition.signal();
                    condition.await();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        });

        t1.start();
        t2.start();
    }

    private void print(int num) throws InterruptedException {
        System.out.println("[" + num + "] --> "+ (a++));
        Thread.sleep(100);
    }



}
