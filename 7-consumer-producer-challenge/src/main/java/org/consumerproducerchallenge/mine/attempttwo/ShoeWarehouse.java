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
            System.out.println("ShoeWarehouse: Orders full, waiting for fulfillment");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("ShoeWarehouse: receiveOrder wait interrupted");
            }
        }
        notifyAll();
        orders.add(order);
        System.out.println("ShoeWarehouse: Order added, num of orders: " + orders.size());
    }

    public synchronized Order fulfillOrder() {
        // called by consumer
        // poll the list
        // if list empty wait in loop until order added
        while(orders.isEmpty()) {
            System.out.println("ShoeWarehouse: Orders are empty, waiting for order");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("ShoeWarehouse: fulfillOrder wait interrupted");
            }
        }
        notifyAll();
        Order order = orders.removeFirst();
        System.out.println("ShoeWarehouse: Order fulfilled, num of orders: " + orders.size());
        return order;
    }
}
