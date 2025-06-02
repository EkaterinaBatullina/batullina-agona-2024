package com.technokratos.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreTask {
    private static final Semaphore parkingSpots = new Semaphore(3, true);

    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            final String carName = "Car-" + i;
            new Thread(() -> {
                try {
                    System.out.println(carName + " is trying to park");
                    parkingSpots.acquire();
                    System.out.println(carName + " has parked");
                    System.out.println(carName + " is leaving the parking");
                    parkingSpots.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}