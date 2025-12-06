package org.example.mine.beforegettinganswer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {

            ShoeWarehouse shoeWarehouse = new ShoeWarehouse();

            // create and start a single producer thread
            executorService.submit(new Producer(shoeWarehouse));

            // create and start 2 consumer threads
            executorService.submit(new Consumer(shoeWarehouse));
            executorService.submit(new Consumer(shoeWarehouse));
        }
    }
}
