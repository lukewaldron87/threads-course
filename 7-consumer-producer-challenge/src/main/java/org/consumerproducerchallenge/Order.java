package org.consumerproducerchallenge;

public record Order(
        long id,
        ShotType shoeType,
        int quantity
) {
}
