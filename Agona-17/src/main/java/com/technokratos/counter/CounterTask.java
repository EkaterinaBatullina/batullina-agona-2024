package com.technokratos.counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterTask {

    public static void main(String[] args) {
        Counter counter = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(task);
            executor.submit(task);
        }

        System.out.println("Final counter value: %s".formatted(counter.getCounter()));
    }
}