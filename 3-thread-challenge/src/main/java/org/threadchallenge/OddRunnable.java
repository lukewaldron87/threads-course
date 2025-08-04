package org.threadchallenge;

public class OddRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 1; i <= 10; i = i + 2) {
            System.out.println("OddRunnable: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("OddRunnable interrupted");
                break;
            }
        }
    }
}
