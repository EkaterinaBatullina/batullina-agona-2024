package com.technokratos.executorservice;

import java.util.Arrays;
import java.util.concurrent.*;

public class ExecutorServiceTask {

    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
            int[] sortArray = {5, 3, 8, 1};
            int[] sumArray = {10, 20, 30, 40};
            Callable<int[]> sortTask = new SortTask(sortArray);
            Callable<Integer> sumTask = new SumTask(sumArray);
            Future<int[]> sortFuture = executor.submit(sortTask);
            Future<Integer> sumFuture = executor.submit(sumTask);
            int[] sortedResult = sortFuture.get();
            int sumResult = sumFuture.get();
            System.out.println("Sorted result: %s".formatted(Arrays.toString(sortedResult)));
            System.out.println("Sum result: %s".formatted(sumResult));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}