package com.technokratos.executorservice;

import java.util.concurrent.Callable;

public class SumTask implements Callable<Integer> {
    private final int[] numbers;

    public SumTask(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public Integer call() {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        System.out.println("Sum: %s".formatted(sum));
        return sum;
    }
}