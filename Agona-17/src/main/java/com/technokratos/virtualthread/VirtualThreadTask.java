package com.technokratos.virtualthread;

public class VirtualThreadTask {

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
                System.out.println("Virtual thread ID: %s".formatted(id));
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