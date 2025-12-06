package org.example.mine.aftergettinganswer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoeWarehouse{

    public List<String> products = List.of(
            "runner",
            "boot",
            "sandal",
            "football boots",
            "leather shoe"
    );

    private final List <Order> orders;

    private final ExecutorService executorService;

    private static final int MAX_SIZE = 5;

    public ShoeWarehouse() {
        this.orders = new ArrayList<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    public void shutDown(){
        executorService.shutdown();
    }

    public synchronized void receiveOrder(Order order) {
        while(orders.size() > MAX_SIZE) {
            System.out.println(getThreadName()+"Received:"+order+", queue is full, waiting for fulfillment");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        orders.add(order);
        System.out.println(getThreadName()+"Received: Order added"+order+", num of orders: " + orders.size());
        executorService.submit(this::fulfillOrder);
        notifyAll();
    }

    public synchronized Order fulfillOrder() {
        while(orders.isEmpty()) {
            System.out.println(getThreadName()+"Orders are empty, waiting for order");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Order order = orders.removeFirst();
        System.out.println(getThreadName()+"Order fulfilled: "+order+", num of orders: " + orders.size());
        notifyAll();
        return order;
    }

    private String getThreadName() {
        return Thread.currentThread().getName() + ": ";
    }
}
