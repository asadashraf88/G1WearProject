package com.example.g1wearproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.g1wearproject.databinding.ActivityTravelDateBinding;
import com.example.g1wearproject.models.Airport;
import com.example.g1wearproject.models.Fare;
import com.example.g1wearproject.models.Price;
import com.example.g1wearproject.utils.Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TravelDateActivity extends AppCompatActivity  {

    private @NonNull ActivityTravelDateBinding binding;

    private Price price;

    private List<Price> recordedPrices;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_travel_date);

        // Instantiating View using View Binding
        binding = ActivityTravelDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(Helper.PRICES_DB, MODE_PRIVATE);

        // Retrieving Selected Origin
        retrieveOriginAndDestination();

        // Initializing View
        init();
    }

    private void retrieveOriginAndDestination() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("origin_id") && intent.hasExtra("destination_id")) {
            int originId = intent.getIntExtra("origin_id", -1);
            int destinationId = intent.getIntExtra("destination_id", -1);
            price = new Price(Price.nextId, originId, destinationId);
        } else {
            Toast.makeText(this, "Origin and Destination not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void init(){
        // Hiding Price View
        binding.priceView.setVisibility(View.GONE);
        // Attaching On Click Listener to Submit Button
        binding.buttonSubmit.setOnClickListener(v -> {

            // get departure and return dates from the date picker
            Date departureDate = new Date(binding.editTextDepartureDate.getYear() - 1900, binding.editTextDepartureDate.getMonth(), binding.editTextDepartureDate.getDayOfMonth());
            Date returnDate = new Date(binding.editTextReturnDate.getYear() - 1900, binding.editTextReturnDate.getMonth(), binding.editTextReturnDate.getDayOfMonth());

            // set the departure and return dates to the price object
            price.setDepartureDate(departureDate);
            price.setReturnDate(returnDate);

            Fare fare = Helper.getFareByOriginAndDestination(this, price.getOrigin(), price.getDestination());
            if (fare == null) {
                Toast.makeText(this, "Fare not found", Toast.LENGTH_SHORT).show();
                return;
            }
            price.setPrice(fare.getPrice());

            Price.nextId++;
            recordedPrices = Helper.loadRecordedPrices(sharedPreferences);
            // Check if recordedSessions is null, initialize with an empty ArrayList if so
            if (recordedPrices == null) {
                recordedPrices = new ArrayList<>();
            }
            // Add the new task to the list of recorded sessions
            recordedPrices.add(price);
            // Save the recorded sessions to SharedPreferences
            Helper.saveRecordedPrices(sharedPreferences, recordedPrices);
            // Navigate to main activity
            Intent intent = new Intent(TravelDateActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

}