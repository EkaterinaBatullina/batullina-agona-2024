package com.technokratos.scheduledexecutorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTask {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Task is running: " + System.currentTimeMillis());
        scheduler.scheduleAtFixedRate(task, 0, 3, TimeUnit.SECONDS);
        scheduler.schedule(() -> {
            System.out.println("Stopping the scheduler");
            scheduler.shutdown();
        }, 15, TimeUnit.SECONDS);
    }
}