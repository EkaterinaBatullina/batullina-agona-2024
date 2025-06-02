package com.technokratos.executorservice;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class SortTask implements Callable<int[]> {
    private final int[] originalArray;

    public SortTask(int[] originalArray) {
        this.originalArray = originalArray;
    }

    @Override
    public int[] call() {
        int[] sortedArray = Arrays.copyOf(originalArray, originalArray.length);
        Arrays.sort(sortedArray);
        System.out.println("Sorted array: " + Arrays.toString(sortedArray));
        return sortedArray;
    }
}