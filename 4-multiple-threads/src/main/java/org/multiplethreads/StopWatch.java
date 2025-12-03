package org.multiplethreads;

import java.util.concurrent.TimeUnit;

public class StopWatch {

    private final TimeUnit timeUnit;

    // made global to demonstrate time slicing
    private int i;

    public StopWatch(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void countDown() {
        countDown(5);
    }

    public void countDown(int unitCount){

        String threadName = Thread.currentThread().getName();

        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try{
            threadColor = ThreadColor.valueOf(threadName);
        } catch (IllegalArgumentException e){

        }

        String color = threadColor.getColor();
        for (i = unitCount; i> 0; i--){
            try{
                timeUnit.sleep(1);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.printf("%s%s Thread : i = %d%n", color, threadName, i);
        }
    }
}
