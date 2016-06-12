package com.goit.semaphores;

import javafx.concurrent.Worker;

import java.util.Random;

/**
 * Created by Сергей on 12.06.2016.
 */
public class Runner {

    private SemaphoreImpl semaphore;
    Random next = new Random();
    int permits;

    public static void main(String[] args) throws InterruptedException {
        new Runner().test();

        System.out.println("\n The second test is beginning \n");
        new Runner().testForConstant();

        System.out.println("\n The third test is beginning \n");
        new Runner().testForAcquire();
    }

    public void test() throws InterruptedException {
        semaphore = new SemaphoreImpl(5);
        for (int i = 0; i < 10; i++) {
            permits = next.nextInt(6);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Number of permits: " + semaphore.getAvailablePermits());
                    semaphore.acquire(permits);
                    System.out.println("Thread " + Thread.currentThread().getName() + " started");
                    semaphore.release(permits);
                    System.out.println("Thread " + Thread.currentThread().getName() + " finished");
                }
            }).start();
        }
    }

    public void testForConstant() throws InterruptedException {
        semaphore = new SemaphoreImpl(2, 8);
        for (int i = 0; i < 6; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Number of permits: " + semaphore.getAvailablePermits());
                    semaphore.acquire(next.nextInt(10));
                    System.out.println("Thread " + Thread.currentThread().getName() + " started");
                    semaphore.release(next.nextInt(10));
                    System.out.println("Thread " + Thread.currentThread().getName() + " finished");
                }
            }).start();
        }
    }

    public void testForAcquire() throws InterruptedException {
        semaphore = new SemaphoreImpl(5);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Number of permits: " + semaphore.getAvailablePermits());
                    semaphore.acquire();
                    System.out.println("Thread " + Thread.currentThread().getName() + " started");
                    semaphore.release();
                    System.out.println("Thread " + Thread.currentThread().getName() + " finished");
                }
            }).start();
        }
    }



}

