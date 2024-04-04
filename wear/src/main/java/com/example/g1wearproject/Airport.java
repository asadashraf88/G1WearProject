package com.example.g1wearproject;

import java.util.ArrayList;
import java.util.List;

public class Airport {

    private String iataCode;
    private String name;

    public Airport(String iataCode, String name) {
        this.iataCode = iataCode;
        this.name = name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Airport> getAirportList() {
        List<Airport> airportList = new ArrayList<>();

        // Empty Rows are required for View (UI) Logic
        airportList.add(new Airport("", ""));
        airportList.add(new Airport("JFK", "John F. Kennedy International Airport"));
        airportList.add(new Airport("LHR", "London Heathrow Airport"));
        airportList.add(new Airport("ISB", "Islamabad International Airport"));
        airportList.add(new Airport("KHI", "Jinnah International Airport"));
        airportList.add(new Airport("", ""));
        airportList.add(new Airport("", ""));
        return airportList;
    }
}
