package org.example.mine.aftergettinganswer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        ShoeWarehouse shoeWarehouse = new ShoeWarehouse();

        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (int i = 1; i <= 15; i++) {
                executorService.submit(new Producer(shoeWarehouse));
            }
        }   finally {
            shoeWarehouse.shutDown();
        }
    }
}
