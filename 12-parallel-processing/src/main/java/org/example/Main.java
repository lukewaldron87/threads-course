package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int numbersLength = 100000;
        long[] numbers = new Random()
                .longs(numbersLength, 1, numbersLength)
                .toArray();

        long sum = Arrays.stream(numbers).sum();
        System.out.println("Sum: " + sum);

//        ForkJoinPool threadPool = (ForkJoinPool) Executors.newWorkStealingPool(4);
        ForkJoinPool threadPool = new ForkJoinPool();

        List<Callable<Long>> tasks = new ArrayList<>();

        int taskNo = 10;
        int splitCount = numbersLength / taskNo;
        for(int i = 0; i < taskNo; i++){
            int start = i * splitCount;
            int end = start + splitCount;
            tasks.add(() -> {
               long tasksum = 0;
               for(int j = start; j < end; j++){
                   tasksum += numbers[j];
               }
               return tasksum;
            });
        }

        List<Future<Long>> futures = threadPool.invokeAll(tasks);

        System.out.println("CPUs: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Parallelism = " + threadPool.getParallelism());
        System.out.println("Pool size = " + threadPool.getPoolSize());
        System.out.println("Steal count = " + threadPool.getStealCount());

        long taskSum = 0;
        for (Future<Long> future : futures) {
            taskSum += future.get();
        }

        System.out.println("Thread Pool Sum: " + taskSum);

        RecursiveTask<Long> task = new RecursiveSumTak(numbers, 0, numbersLength, 8);
        long forkJoinSum = threadPool.invoke(task);
        System.out.println("Recursive sum is: " + forkJoinSum);
        threadPool.shutdown();
    }
}