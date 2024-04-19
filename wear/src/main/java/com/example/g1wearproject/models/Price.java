package com.example.g1wearproject.models;

import java.util.Date;

public class Price {
    private final int id;
    private final String origin;
    private final String destination;


    private final Date departureDate;
    private final Date returnDate;

    private final double price;

    public static int nextId = 1;

    public Price(int id, String origin, String destination, Date departureDate, Date returnDate, double price) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public double getPrice() {
        return price;
    }
}