package org.multiplethreads;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        StopWatch greenStopWatch =  new StopWatch(TimeUnit.SECONDS);
        StopWatch purpleStopWatch =  new StopWatch(TimeUnit.SECONDS);
        StopWatch redStopWatch =  new StopWatch(TimeUnit.SECONDS);

        Thread green = new Thread(greenStopWatch::countDown, ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread (() -> purpleStopWatch.countDown(7), ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(redStopWatch::countDown, ThreadColor.ANSI_RED.name());

        green.start();
        purple.start();
        red.start();
    }
}