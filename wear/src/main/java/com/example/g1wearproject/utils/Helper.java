package com.example.g1wearproject.utils;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.g1wearproject.models.Airport;
import com.example.g1wearproject.models.Fare;
import com.example.g1wearproject.models.Price;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    public static List<Airport> loadOriginList(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("airports.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Log.e("loadOriginList", "loadOriginList: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

        Type type = new TypeToken<List<Airport>>() {}.getType();
        List<Airport> airports = new Gson().fromJson(json, type);
        Log.d("loadOriginList", "loadOriginList: " + airports.size());
        return airports;
    }

    public static List<Fare> loadFareList(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("fares.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Type type = new TypeToken<List<Fare>>() {}.getType();
        List<Fare> fares = new Gson().fromJson(json, type);
        return fares;
    }

    public static Airport getAirportById(Context context, int airportId) {
        List<Airport> airports = loadOriginList(context);
        if (airports == null) {
            return null;
        }
        return airports.stream().filter(airport -> airport.getId() == airportId).findFirst().orElse(null);
    }

    public static Fare getFareByOriginAndDestination(Context context, int originId, int destinationId) {
        List<Fare> fares = loadFareList(context);
        if (fares == null) {
            return null;
        }
        return fares.stream().filter(fare -> fare.getOrigin() == originId && fare.getDestination() == destinationId).findFirst().orElse(null);
    }



}
