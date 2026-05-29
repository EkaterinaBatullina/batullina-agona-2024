package com.technokratos.scheduledexecutorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledExecutorServiceTask {
    private static final ReentrantLock logLock = new ReentrantLock();

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                logLock.lock();
                try {
                    System.out.println("Task is running: %s".formatted(System.currentTimeMillis()));
                } finally {
                    logLock.unlock();
                }

                if (Math.random() < 0.1) {
                    throw new RuntimeException("Simulated network failure");
                }

            } catch (Throwable t) {
                logLock.lock();
                try {
                    System.err.println("ALERT: Task failed but scheduler survived! Reason: " + t.getMessage());
                } finally {
                    logLock.unlock();
                }
            }
        };

        scheduler.scheduleWithFixedDelay(task, 0, 3, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            logLock.lock();
            try {
                System.out.println("Stopping the scheduler...");
            } finally {
                logLock.unlock();
            }
            scheduler.shutdown();
        }, 15, TimeUnit.SECONDS);
    }
}