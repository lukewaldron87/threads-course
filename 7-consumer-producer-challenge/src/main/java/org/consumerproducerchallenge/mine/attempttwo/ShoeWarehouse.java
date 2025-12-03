package org.consumerproducerchallenge.mine.attempttwo;

import java.util.ArrayList;
import java.util.List;

public class ShoeWarehouse{

    public List<String> products = List.of(
            "runner",
            "boot",
            "sandal",
            "football boots",
            "leather shoe"
    );

    private final List <Order> orders;

    private static final int MAX_SIZE = 5;

    public ShoeWarehouse() {
        this.orders = new ArrayList<>();
    }

    public synchronized void receiveOrder(Order order) {
        // called by producer
        // poll/loop indefinitely checking the size of the list
        // if list == MAX_SIZE call wait in loop
        while(orders.size() > MAX_SIZE) {
            System.out.println("Received:, waiting for fulfillment");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        orders.add(order);
        notifyAll();
        System.out.println("Received: Order added"+order+", num of orders: " + orders.size());
    }

    public synchronized Order fulfillOrder() {
        // called by consumer
        // poll the list
        // if list empty wait in loop until order added
        while(orders.isEmpty()) {
            System.out.println(Thread.currentThread().getName()+": Orders are empty, waiting for order");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Order order = orders.removeFirst();
        System.out.println(Thread.currentThread().getName()+": Order fulfilled: "+order+", num of orders: " + orders.size());
        notifyAll();
        return order;
    }
}
