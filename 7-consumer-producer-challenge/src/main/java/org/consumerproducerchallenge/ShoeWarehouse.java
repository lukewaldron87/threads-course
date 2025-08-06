package org.consumerproducerchallenge;

import java.util.ArrayList;
import java.util.List;

public class ShoeWarehouse {

    private List<Order> orders = new ArrayList<>();
    private boolean hasOrder = false;

    public synchronized void receiveOrder(Order order) {

        while (hasOrder) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hasOrder = true;
            notifyAll();
            orders.add(order);
            System.out.println("Order received: " + order);
        }
    }

    public Order fulfillOrder() {

        while (!hasOrder) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hasOrder = false;
            notifyAll();

        }

        if(orders.isEmpty()){
            return null;
        }
        Order order = orders.getFirst();
        orders.removeFirst();
        return order;
    }
}
