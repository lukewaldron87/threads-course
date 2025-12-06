package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        ExecutorService multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of(
                () -> Main.sum(1, 10, 1, "red"),
                () -> Main.sum(10, 100, 10, "blue"),
                () -> Main.sum(2, 20, 2, "green")
        );

        try {
            List<Future<Integer>> results = multiExecutor.invokeAll(taskList);
            for(Future<Integer> result : results ) {
                System.out.println(result.get(500, TimeUnit.MILLISECONDS));
            }
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally{
            multiExecutor.shutdown();
        }

    }

    public static void cachedMain(String[] args) {
        ExecutorService multiExecutor = Executors.newCachedThreadPool();
        try {
            Future<Integer> redValue = multiExecutor.submit(() -> Main.sum(1, 10, 1, "red"));
            Future<Integer> blueValue = multiExecutor.submit(() -> Main.sum(10, 100, 10, "blue"));
            Future<Integer> greenValue = multiExecutor.submit(() -> Main.sum(2, 20, 2, "green"));
            
            try {
                System.out.println(redValue.get(500, TimeUnit.SECONDS));
                System.out.println(blueValue.get(500, TimeUnit.SECONDS));
                System.out.println(greenValue.get(500, TimeUnit.SECONDS));
            }catch (Exception e){
                throw new  RuntimeException(e);
            }
        } finally{
            multiExecutor.shutdown();
        }
    }

    public static void fixedMain(String[] args) {

        int count = 6;
        ExecutorService multiExecutor = Executors.newFixedThreadPool(count, new ColorThreadFactory());

        for (int i = 0; i < count; i++) {
            multiExecutor.execute(Main::countDown);
        }
        multiExecutor.shutdown();
    }

    public static void singleMain(String[] args) {

        ExecutorService blueExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_BLUE));
        blueExecutor.execute(Main::countDown);
        blueExecutor.shutdown();

        boolean isDone = false;
        try {
            isDone = blueExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(isDone) {
            System.out.println("Blue finished, starting Yellow");
            ExecutorService yellowExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_YELLOW));
            yellowExecutor.execute(Main::countDown);
            yellowExecutor.shutdown();

            try {
                isDone = yellowExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(isDone) {
                System.out.println("Yellow finished, starting Red");
                ExecutorService redExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_RED));
                redExecutor.execute(Main::countDown);
                redExecutor.shutdown();

                try {
                    isDone = redExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if(isDone) {
                    System.out.println("All processes completed");
                }
            }
        }
    }

    public static void notmain(String[] args) {

        Thread blue = new Thread(Main::countDown, ThreadColor.ANSI_BLUE.name());
        Thread yellow = new Thread(Main::countDown, ThreadColor.ANSI_YELLOW.name());
        Thread red = new Thread(Main::countDown, ThreadColor.ANSI_RED.name());

        blue.start();

        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        yellow.start();

        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        red.start();

        try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void countDown() {

        String threadName = Thread.currentThread().getName();
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            // bad name ignored
        }

        String color = threadColor.getColor();
        for (int i = 20; i >= 0; i--) {
            System.out.println(color + " " + threadName.replace("ANSI_", "") + " " + i);
        }
    }

    private static int sum(int start, int end, int delta, String colorString){

        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf("ANSI_" + colorString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            // User may pass a bad color name, Will just ignore this error
        }

        String color = threadColor.getColor();
        int sum = 0;
        for ( int i = start; i <=  end; i += delta) {
            sum += i;
        }
        System.out.println(color + Thread.currentThread().getName() + ", " + colorString + " " + sum);
        return sum;
    }
}