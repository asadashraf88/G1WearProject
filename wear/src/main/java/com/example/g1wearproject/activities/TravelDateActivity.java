package com.example.g1wearproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    float calcPrice = 0.0f;
    String originId;
    String destinationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_travel_date);

        // Instantiating View using View Binding
        binding = ActivityTravelDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonSubmit.setText("SUBMIT");

        sharedPreferences = getSharedPreferences(Helper.PRICES_DB, MODE_PRIVATE);

        // Retrieving Selected Origin
        retrieveOriginAndDestination();

        // Initializing View
        init();
    }

    @SuppressLint("SetTextI18n")
    private void retrieveOriginAndDestination() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("origin_id") && intent.hasExtra("destination_id")) {
            originId = intent.getStringExtra("origin_id");
            destinationId = intent.getStringExtra("destination_id");

            binding.originTextView.setText(originId + " to " + destinationId);
            binding.destTextView.setText(destinationId + " to " + originId);

            Toast.makeText(this, originId + " " + destinationId, Toast.LENGTH_SHORT).show();

            int originIdInt = Helper.loadOriginList(this).stream()
                    .filter(airport -> airport.getIataCode().equals(originId))
                    .findFirst()
                    .map(Airport::getId)
                    .orElse(-1);

            int destinationIdInt = Helper.loadOriginList(this).stream()
                    .filter(airport -> airport.getIataCode().equals(destinationId))
                    .findFirst()
                    .map(Airport::getId)
                    .orElse(-1);

            Toast.makeText(this, String.valueOf(originIdInt), Toast.LENGTH_SHORT).show();

            price = new Price(Price.nextId, originIdInt, destinationIdInt);
        } else {
            Toast.makeText(this, "Origin and Destination not found", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void init(){
        binding.priceViewContainer.setVisibility(View.GONE);
        // Attaching On Click Listener to Submit Button
        binding.buttonSubmit.setOnClickListener(v -> {

            if(binding.buttonSubmit.getText()!="RESET") {
                binding.priceViewContainer.setVisibility(View.VISIBLE);

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
                float corePrice = fare.getPrice();

                calcPrice = corePrice + (float) (Math.random() * 100)-50;
                calcPrice = Math.round(calcPrice * 100.0f) / 100.0f;
                // simulate a random variation in the price
                price.setPrice(calcPrice);

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

                binding.priceView.setText("Best Price: $" + calcPrice);
                binding.buttonSubmit.setText("RESET");
            } else {
                // Navigate to main activity
                Intent intent = new Intent(TravelDateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.whatsapp.setOnClickListener(v -> {
            Intent whatsappIntent = new Intent();
            whatsappIntent.setAction(Intent.ACTION_SEND);
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Best Return Ticket Price: $" + String.valueOf(calcPrice) + "Between:" + originId + " and " + destinationId);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");

            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}