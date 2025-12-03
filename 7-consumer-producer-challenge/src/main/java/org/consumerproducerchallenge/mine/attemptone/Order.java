package org.consumerproducerchallenge.mine.attemptone;

public record Order(
        long id,
        ShotType shoeType,
        int quantity
) {
}
