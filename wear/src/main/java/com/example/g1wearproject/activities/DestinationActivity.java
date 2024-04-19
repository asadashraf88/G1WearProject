package com.example.g1wearproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.wear.widget.WearableLinearLayoutManager;

import com.example.g1wearproject.adapters.AirportAdapter;
import com.example.g1wearproject.databinding.ActivityDestinationBinding;
import com.example.g1wearproject.models.Airport;
import com.example.g1wearproject.models.Price;
import com.example.g1wearproject.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class DestinationActivity extends AppCompatActivity {

    // Declaring variables and objects
    private ActivityDestinationBinding binding;
    private AirportAdapter adapter;
    private List<Airport> airportList;

    private int temporaryOriginId = -1; // Initialize with an invalid ID
    private int temporaryDestinationId = -1; // Initialize with an invalid ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiating View using View Binding
        binding = ActivityDestinationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the origin ID from the intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("origin_id")) {
            temporaryOriginId = extras.getInt("origin_id");
        } else {
            // Handle the case where origin ID is not passed
            Toast.makeText(this, "Origin ID not found", Toast.LENGTH_SHORT).show();
            // You may want to finish the activity or handle this case appropriately
        }
        // Initializing View
        init();
    }

    private void startDateActivity() {
        if (temporaryOriginId != -1) { // Check if origin ID is valid
            Intent intent = new Intent(this, TravelDateActivity.class);
            // Pass the origin ID to DestinationActivity
            intent.putExtra("origin_id", temporaryOriginId);
            intent.putExtra("destination_id", temporaryDestinationId);
            startActivity(intent);
        } else {
            // Handle the case where origin ID is not selected
            Toast.makeText(this, "Please select an origin airport first", Toast.LENGTH_SHORT).show();
        }
    }
    private void init() {
        binding.destinationRecyclerView.setHasFixedSize(true);
        binding.destinationRecyclerView.setEdgeItemsCenteringEnabled(true);
        binding.destinationRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        // Initialize workout tasks list (load from SharedPreferences)

        airportList = Helper.loadOriginList();

        // If the list is null or empty, create a new list
        if (airportList == null) {
            airportList = new ArrayList<>();
        }

        if (!airportList.isEmpty()) {
            // get the max id from the list
            Price.nextId = airportList.get(airportList.size() - 1).getId() + 1;
        }

        adapter = new AirportAdapter(airportList, getApplicationContext(), airport -> {
            // if destination equals origin
            if (airport.getId() == temporaryOriginId) {
                Toast.makeText(this, "Destination airport cannot be the same as origin airport", Toast.LENGTH_SHORT).show();
                return;
            }
            // Store the ID of the clicked origin airport temporarily
            temporaryDestinationId = airport.getId();
            startDateActivity();
            // Proceed to the destination selection process...
        });
        binding.destinationRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}