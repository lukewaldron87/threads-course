package org.example;

import java.util.concurrent.RecursiveTask;

public class RecursiveSumTak extends RecursiveTask<Long> {

   private final long[] numbers;
   private final int start;
   private final int end;
   private final int division;

   public RecursiveSumTak(long[] numbers, int start, int end, int division) {
       this.numbers = numbers;
       this.start = start;
       this.end = end;
       this.division = division;
   }

    @Override
    protected Long compute() {

       if ((end - start) <=  (numbers.length / division)) {
           System.out.println(start + " : " + end);
           long sum = 0;
           for (int i = start; i < end; i++) {
               sum += numbers[i];
           }
           return sum;
       } else  {
           int mid = (start + end) / 2;
           RecursiveSumTak leftTask = new RecursiveSumTak(numbers, start, mid, division);
           RecursiveSumTak rightTask = new RecursiveSumTak(numbers, mid, end, division);
           leftTask.fork();
           rightTask.fork();
           return leftTask.join() + rightTask.join();
       }
    }
}
