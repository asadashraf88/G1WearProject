package com.example.g1wearproject.utils;

import android.content.SharedPreferences;

import com.example.g1wearproject.models.Airport;
import com.example.g1wearproject.models.Price;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Helper {
    // Key for the tasks in SharedPreferences
    private static final String PRICES_KEY = "prices";
    public static final String PRICES_DB = "PriceDb03";

    // Method to load recorded workout sessions from SharedPreferences
    public static List<Price> loadRecordedPrices(SharedPreferences sharedPreferences) {
        String json = sharedPreferences.getString(PRICES_KEY, null);
        Type type = new TypeToken<List<Price>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    // Method to save recorded workout sessions to SharedPreferences
    public static void saveRecordedPrices(SharedPreferences sharedPreferences, List<Price> recordedPrices) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(recordedPrices);
        editor.putString(PRICES_KEY, json);
        editor.apply();
    }

    public static List<Airport> loadOriginList() {
        // harcode Airport list
        List<Airport> airports = new java.util.ArrayList<>();
        airports.add(new Airport(1, "LAX", "Los Angeles International Airport"));
        airports.add(new Airport(2, "JFK", "John F. Kennedy International Airport"));
        airports.add(new Airport(3, "ORD", "O'Hare International Airport"));
        airports.add(new Airport(4, "DFW", "Dallas/Fort Worth International Airport"));
        airports.add(new Airport(5, "DEN", "Denver International Airport"));
        airports.add(new Airport(6, "SFO", "San Francisco International Airport"));
        airports.add(new Airport(7, "LAS", "McCarran International Airport"));
        airports.add(new Airport(8, "SEA", "Seattle-Tacoma International Airport"));
        airports.add(new Airport(9, "ATL", "Hartsfield-Jackson Atlanta International Airport"));
        airports.add(new Airport(10, "MIA", "Miami International Airport"));
        airports.add(new Airport(11, "MCO", "Orlando International Airport"));
        airports.add(new Airport(12, "BOS", "Logan International Airport"));
        airports.add(new Airport(13, "PHL", "Philadelphia International Airport"));
        airports.add(new Airport(14, "IAD", "Washington Dulles International Airport"));
        airports.add(new Airport(15, "DCA", "Ronald Reagan Washington National Airport"));
        airports.add(new Airport(16, "MDW", "Chicago Midway International Airport"));
        airports.add(new Airport(17, "HNL", "Daniel K. Inouye International Airport"));
        airports.add(new Airport(18, "SAN", "San Diego International Airport"));
        airports.add(new Airport(19, "TPA", "Tampa International Airport"));
        airports.add(new Airport(20, "PHX", "Phoenix Sky Harbor International Airport"));
        airports.add(new Airport(21, "PDX", "Portland International Airport"));
        airports.add(new Airport(22, "MSP", "Minneapolis-Saint Paul International Airport"));
        airports.add(new Airport(23, "DTW", "Detroit Metropolitan Wayne County Airport"));
        airports.add(new Airport(24, "SLC", "Salt Lake City International Airport"));
        airports.add(new Airport(25, "FLL", "Fort Lauderdale-Hollywood International Airport"));
        airports.add(new Airport(26, "BWI", "Baltimore/Washington International Thurgood Marshall Airport"));
        airports.add(new Airport(27, "IAH", "George Bush Intercontinental Airport"));
        airports.add(new Airport(28, "EWR", "Newark Liberty International Airport"));
        airports.add(new Airport(29, "LGA", "LaGuardia Airport"));
        return airports;

    }

    public static Airport getAirportById(int airportId) {
        return loadOriginList().stream().filter(airport -> airport.getId() == airportId).findFirst().orElse(null);
    }
}
