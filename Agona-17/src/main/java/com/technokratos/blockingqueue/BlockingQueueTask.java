package com.technokratos.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueTask {
    private static final ReentrantLock logLock = new ReentrantLock();

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            executor.submit(() -> {
                try {
                    for (int i = 1; i <= 10; i++) {
                        queue.put(i);
                        logLock.lock();
                        try {
                            System.out.println("Producer: put %s".formatted(i));
                        } finally {
                            logLock.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            executor.submit(() -> {
                try {
                    for (int i = 1; i <= 10; i++) {
                        Integer item = queue.take();

                        logLock.lock();
                        try {
                            System.out.println("Consumer: take %s".formatted(item));
                        } finally {
                            logLock.unlock();
                        }

                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

        }

        System.out.println("All producer and consumer tasks completed successfully.");
    }
}