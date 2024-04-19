package com.example.g1wearproject.models;

public class Fare {
    private final int origin;
    private final int destination;
    private final float price;

    public Fare(int origin, int destination, float price) {
        this.origin = origin;
        this.destination = destination;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public int getOrigin() {
        return origin;
    }

    public int getDestination() {
        return destination;
    }
}
