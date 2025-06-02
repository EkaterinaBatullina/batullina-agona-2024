package com.technokratos.reentrantlock;

import java.util.concurrent.locks.Lock;

public class ReentrantLockTask {

    public static void main(String[] args) {
        Account firstAccount = new Account(1000);
        Account secondAccount = new Account(1000);
        Thread[] threads = {
                new Thread(() -> transfer(firstAccount, secondAccount, 100)),
                new Thread(() -> transfer(secondAccount, firstAccount, 200))
        };
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("FirstAccount balance: " + firstAccount.getBalance());
        System.out.println("SecondAccount balance: " + secondAccount.getBalance());
    }

    public static void transfer(Account source, Account target, int amount) {
        Account firstAccount = source.hashCode() < target.hashCode() ? source : target;
        Account secondAccount = source.hashCode() < target.hashCode() ? target : source;
        Lock firstLock = firstAccount.getLock();
        Lock secondLock = secondAccount.getLock();
        firstLock.lock();
        secondLock.lock();
        try {
            if (source.getBalance() >= amount) {
                source.withdraw(amount);
                target.deposit(amount);
                System.out.println("Transfer of " + amount + " completed");
            } else {
                System.out.println("Insufficient funds for transfer");
            }
        } finally {
            secondLock.unlock();
            firstLock.unlock();
        }
    }
}