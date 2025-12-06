package org.example.mine.beforegettinganswer;

public class Consumer implements Runnable {

    private ShoeWarehouse shoeWarehouse;

    public Consumer(ShoeWarehouse shoeWarehouse) {
        this.shoeWarehouse = shoeWarehouse;
    }

    @Override
    public void run() {
        // each thread should:
        // - process 5 fulfillment orders
        // - calling fulfillOrder for each item
        for (int i = 0; i < 5; i++) {
            Order order = shoeWarehouse.fulfillOrder();
        }

    }
}
