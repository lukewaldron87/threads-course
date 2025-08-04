package org.threadchallenge;

public class Main {
    public static void main(String[] args) {

        Runnable runnable = () -> {
            for (int i = 1; i <= 10; i = i + 2) {
                System.out.println("OddRunnable: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("OddRunnable interrupted");
                    break;
                }
            }
        };

        Thread oddThread = new Thread(runnable);
        EvenThread evenThread = new EvenThread();

        oddThread.start();
        evenThread.start();

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        evenThread.interrupt();
    }
}