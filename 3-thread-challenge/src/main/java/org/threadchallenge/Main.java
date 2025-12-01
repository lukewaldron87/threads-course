package org.threadchallenge;

public class Main {

    public static void main(String[] args) {
        Thread oddThread = new Thread(() -> {
            for(int i = 1; i<=10; i+=2){
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted OddRunnable");
                    break;
                }
            }
        });
        Thread evenThread = new EvenThread();
        oddThread.start();
        evenThread.start();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        oddThread.interrupt();
    }
}
