package org.example.course;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    private static final Random random = new Random();

    public static void main(String[] args) {

        ShoeWarehouse warehouse = new ShoeWarehouse();
        ExecutorService orderingService = Executors.newCachedThreadPool();
        Callable<Order> orderingTask = () -> {
          Order newOrder = generateOrder();
          try{
              Thread.sleep(random.nextInt(500, 5000));
              warehouse.receiveOrder(newOrder);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
            return newOrder;
        };

//        List<Callable<Order>> tasks = Collections.nCopies(15, orderingTask);
//        try {
//            orderingService.invokeAll(tasks);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        try{
            Thread.sleep(random.nextInt(500, 2000));
            for (int j = 0; j < 10; j++) {
                orderingService.submit(() -> warehouse.receiveOrder(generateOrder()));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        orderingService.shutdown();
        try {
            orderingService.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        warehouse.shotDown();
    }

    private static Order generateOrder() {
        long id = random.nextLong(1000000, 9999999);
        int productNumber = ThreadLocalRandom.current().nextInt(0, ShoeWarehouse.products.size());
        String product = ShoeWarehouse.products.get(productNumber);
        int quantity = ThreadLocalRandom.current().nextInt(1, 10);
        return new Order(id, product, quantity);
    }
}
