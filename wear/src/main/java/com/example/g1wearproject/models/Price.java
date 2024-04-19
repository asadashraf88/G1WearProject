package com.example.g1wearproject.models;

import java.util.Date;

public class Price {
    private final int id;
    private final int origin;
    private final int destination;


    private Date departureDate;
    private Date returnDate;

    private final double price;

    public static int nextId = 1;

    public Price(int id, int origin, int destination, Date departureDate, Date returnDate, double price) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.price = price;
    }

    public Price(int nextId, int originId, int destinationId) {
        this(nextId, originId, destinationId, new Date(), new Date(), 0.0);
    }

    public int getId() {
        return id;
    }

    public int getOrigin() {
        return origin;
    }

    public int getDestination() {
        return destination;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public double getPrice() {
        return price;
    }
}
