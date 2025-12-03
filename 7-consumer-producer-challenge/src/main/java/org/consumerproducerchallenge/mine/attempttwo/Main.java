package org.consumerproducerchallenge.mine.attempttwo;

public class Main {
    public static void main(String[] args) {

        ShoeWarehouse  shoeWarehouse = new ShoeWarehouse();

        // create and start a single producer thread
        Thread producerThread = new Thread(new Producer(shoeWarehouse));

        // create and start 2 consumer threads
        Thread consumerThread1 = new Thread(new Consumer(shoeWarehouse));
        Thread consumerThread2 = new Thread(new Consumer(shoeWarehouse));


        producerThread.start();
        consumerThread1.start();
        consumerThread2.start();
    }
}
