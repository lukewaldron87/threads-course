package org.consumerproducerchallenge.mine;

public record Order(
        long id,
        ShotType shoeType,
        int quantity
) {
}
