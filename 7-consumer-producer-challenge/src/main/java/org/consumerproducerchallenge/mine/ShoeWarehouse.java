package org.consumerproducerchallenge.mine;

import java.util.ArrayList;
import java.util.List;

public class ShoeWarehouse {

    private final List<Order> orders = new ArrayList<>();
    private static final int MAXIMUM_ORDERS = 5;

    public synchronized void receiveOrder(Order order) {

        if(orders.size() == MAXIMUM_ORDERS) {
            System.out.println("Orders full waiting to add: " + order);
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        notifyAll();
        orders.add(order);
        System.out.println("Order received: " + order);
    }

    public synchronized Order fulfillOrder() {

        while (orders.isEmpty()) {

            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        notifyAll();
        Order order = orders.getFirst();
        orders.removeFirst();
        return order;
    }
}
