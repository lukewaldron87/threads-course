package org.consumerproducerchallenge;

import java.util.Random;

public class FulfillerConsumer implements Runnable{

    private ShoeWarehouse shoeWarehouse;

    private final String name;

    public FulfillerConsumer(
            ShoeWarehouse shoeWarehouse,
            String name){
        this.shoeWarehouse = shoeWarehouse;
        this.name = name;
    }

    @Override
    public void run() {

        Random random = new Random();
        Order latestOrder;

        do{
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latestOrder = shoeWarehouse.fulfillOrder();
            System.out.println("Order fulfilled by "+name+": " + latestOrder);
        } while (latestOrder != null);

        System.out.println("Fulfilled all orders "+name);
    }
}
