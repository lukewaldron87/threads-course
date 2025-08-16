package org.consumerproducerchallenge.mine;

import java.util.List;
import java.util.Random;

public class OrdererProducer implements Runnable{

    private final ShoeWarehouse shoeWarehouse;

    private final List<Order> orders = List.of(
            new Order(1L, ShotType.BOOT, 10),
            new Order(2L, ShotType.DRESS, 4),
            new Order(3L, ShotType.BOOT, 7),
            new Order(4L, ShotType.TRAINER, 13),
            new Order(5L, ShotType.BOOT, 61),
            new Order(6L, ShotType.FOOTBALL, 3),
            new Order(7L, ShotType.BOOT, 8),
            new Order(8L, ShotType.TRAINER, 19),
            new Order(9L, ShotType.DRESS, 100),
            new Order(10L, ShotType.BOOT, 1)
    );

    public OrdererProducer(ShoeWarehouse shoeWarehouse){
        this.shoeWarehouse = shoeWarehouse;
    }

    @Override
    public void run() {

        Random random = new Random();

        for(Order order : orders){
            shoeWarehouse.receiveOrder(order);
            try{
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
