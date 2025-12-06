package org.example.mine.aftergettinganswer;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {

    private final ShoeWarehouse shoeWarehouse;

    public Producer(ShoeWarehouse shoeWarehouse) {
        this.shoeWarehouse = shoeWarehouse;
    }

    @Override
    public void run() {
            long id = ThreadLocalRandom.current().nextLong(1, 10000);
            int productNumber = ThreadLocalRandom.current().nextInt(0, shoeWarehouse.products.size());
            String product = shoeWarehouse.products.get(productNumber);
            int quantity = ThreadLocalRandom.current().nextInt(1, 10);
            Order order = new Order(id, product, quantity);
            shoeWarehouse.receiveOrder(order);
    }
}
