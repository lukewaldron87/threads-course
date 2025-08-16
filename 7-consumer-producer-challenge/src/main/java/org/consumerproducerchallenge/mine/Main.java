package org.consumerproducerchallenge.mine;

public class Main {
    public static void main(String[] args) {
        ShoeWarehouse shoeWarehouse = new ShoeWarehouse();
        Thread producer = new Thread(new OrdererProducer(shoeWarehouse));
        Thread consumer1 = new Thread(new FulfillerConsumer(shoeWarehouse, "fulfiller 1"));
        Thread consumer2 = new Thread(new FulfillerConsumer(shoeWarehouse, "fulfiller 2"));

        producer.start();
        consumer1.start();
        consumer2.start();
    }
}