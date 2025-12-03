package org.consumerproducerchallenge.mine.attempttwo;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {

    private ShoeWarehouse shoeWarehouse;

    public Producer(ShoeWarehouse shoeWarehouse) {
        this.shoeWarehouse = shoeWarehouse;
    }

    @Override
    public void run() {

        // generate 10 sales orders
        // call receiveOrder for each
        for (int i = 0; i < 10; i++) {

            int productNumber = ThreadLocalRandom.current().nextInt(0, shoeWarehouse.products.size());
            String product = shoeWarehouse.products.get(productNumber);
            int quantity = ThreadLocalRandom.current().nextInt(1, 10);
            Order order = new Order(i, product, quantity);
            shoeWarehouse.receiveOrder(order);
        }
    }
}
