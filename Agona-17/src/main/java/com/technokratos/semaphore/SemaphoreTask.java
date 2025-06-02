package com.technokratos.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreTask {
    private static final Semaphore parkingSpots = new Semaphore(3, true);

    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            final String carName = "Car-%s".formatted(i);
            new Thread(() -> {
                try {
                    System.out.println("%s is trying to park".formatted(carName));
                    parkingSpots.acquire();
                    System.out.println("%s has parked".formatted(carName));
                    System.out.println("%s is leaving the parking".formatted(carName));
                    parkingSpots.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}