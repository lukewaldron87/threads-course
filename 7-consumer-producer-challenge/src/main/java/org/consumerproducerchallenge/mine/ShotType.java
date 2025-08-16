package org.consumerproducerchallenge.mine;

public enum ShotType {
    TRAINER("Trainer"),
    BOOT("Boot"),
    DRESS("Dress shoes"),
    FOOTBALL("Football boots");

    private final String name;

    ShotType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
