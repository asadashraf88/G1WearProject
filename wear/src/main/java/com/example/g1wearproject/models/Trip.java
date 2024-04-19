package com.example.g1wearproject.models;

public class Trip {
    private final int origin;
    private final int destination;
    private final float price;

    public Trip(int origin, int destination, float price) {
        this.origin = origin;
        this.destination = destination;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

}
