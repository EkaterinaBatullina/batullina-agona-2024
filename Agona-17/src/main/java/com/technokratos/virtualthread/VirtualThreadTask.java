package com.technokratos.virtualthread;

import java.util.concurrent.locks.ReentrantLock;

public class VirtualThreadTask {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        int threadCount = 10000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            int id = i;
            threads[i] = Thread.startVirtualThread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                lock.lock();
                try {
                    System.out.println("Virtual thread ID: %s".formatted(id));
                } finally {
                    lock.unlock();
                }
            });
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("All virtual threads have finished");
    }
}