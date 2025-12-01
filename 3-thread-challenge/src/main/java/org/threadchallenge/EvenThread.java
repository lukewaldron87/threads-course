package org.threadchallenge;

public class EvenThread extends Thread {

    @Override
    public void run() {
        for(int i = 2; i<=10; i+=2){
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted EvenThread");
                break;
            }
        }
    }
}
