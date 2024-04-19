package com.example.g1wearproject.models;

public class Airport {
    private final int id;
    private final String iataCode;
    private final String name;

    public Airport(int id, String iataCode, String name) {
        this.id = id;
        this.iataCode = iataCode;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getName() {
        return name;
    }

}
