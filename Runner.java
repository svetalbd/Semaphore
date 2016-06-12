package com.goit.semaphores;

import javafx.concurrent.Worker;

import java.util.Random;

/**
 * Created by Сергей on 12.06.2016.
 */
public class Runner {

    private SemaphoreImpl semaphore;
    Random next = new Random();
    int permits = 4;

    public static void main(String[] args) throws InterruptedException {
        new Runner().test();
        System.out.println("\n The second test is beginning \n");
        new Runner().testForConstant();
    }

    public void test() throws InterruptedException {
        semaphore = new SemaphoreImpl(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Number of permits: " + semaphore.getAvailablePermits());
                    semaphore.acquire(permits);
                    System.out.println("Thread " + Thread.currentThread().getName() + " started");
                    semaphore.release(permits);
                }
            }).start();
        }
    }

    public void testForConstant() throws InterruptedException {
        semaphore = new SemaphoreImpl(2, 8);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Number of permits: " + semaphore.getAvailablePermits());
                    semaphore.acquire(next.nextInt(8));
                    System.out.println("Thread " + Thread.currentThread().getName() + " started");
                    semaphore.release(next.nextInt(8));
                }
            }).start();
        }
    }



}

