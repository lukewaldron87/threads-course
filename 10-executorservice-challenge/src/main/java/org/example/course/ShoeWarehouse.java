package org.example.course;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoeWarehouse{

    public final ExecutorService fulfillmentService;

    public final static List<String> products = List.of(
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
        fulfillmentService = Executors.newFixedThreadPool(3);
    }

    public void shotDown(){
        fulfillmentService.shutdown();
    }

    public synchronized void receiveOrder(Order order) {
        // called by producer
        // poll/loop indefinitely checking the size of the list
        // if list == MAX_SIZE call wait in loop
        while(orders.size() > MAX_SIZE) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        orders.add(order);
        System.out.println(Thread.currentThread().getName()+": Received: Order added"+order+", num of orders: " + orders.size());
        fulfillmentService.submit(this::fulfillOrder);
        notifyAll();
    }

    public synchronized Order fulfillOrder() {
        // called by consumer
        // poll the list
        // if list empty wait in loop until order added
        while(orders.isEmpty()) {
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
