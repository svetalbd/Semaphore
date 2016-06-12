package com.goit.semaphores;

/**
 * Created by Сергей on 10.06.2016.
 */
public class SemaphoreImpl implements Semaphore {
    private volatile int freePermits = 0;
    private final Object lock = new Object();
    private final int MAX_FLOW_NUMBER;

    public SemaphoreImpl(int MAX_FLOW_NUMBER, int freePermits) {
        this.MAX_FLOW_NUMBER = MAX_FLOW_NUMBER;
        this.freePermits = freePermits;
    }

    public SemaphoreImpl(int freePermits) {
        this.MAX_FLOW_NUMBER = freePermits;
        this.freePermits = freePermits;
    }


    @Override
    public void acquire() {
        synchronized (lock) {
            if (freePermits > 0)
                freePermits--;
            else {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void acquire(int permits) {
        if (freePermits < permits || freePermits < 0) {
            System.out.println("ERROR: free permits = [" + freePermits + "]");
            System.out.println("ERROR: required permits = [" + permits + "]");
        } else {
            synchronized (lock) {
                freePermits -= permits;
            }
        }
    }

    @Override
    public void release() {
        synchronized (lock) {
            if (freePermits < MAX_FLOW_NUMBER) {
                freePermits++;
                lock.notify();
            }
        }

    }

    @Override
    public void release(int permits) {
        synchronized (lock) {
            if (permits < 0) {
                System.out.println("ERROR: free permits = [" + freePermits + "]");
                System.out.println("ERROR: required permits = [" + permits + "]");
            }
            if (freePermits + permits >=MAX_FLOW_NUMBER) {
                freePermits += permits;
                lock.notify();
            }
        }
    }

    @Override
    public int getAvailablePermits() {
        return freePermits;
    }
}
