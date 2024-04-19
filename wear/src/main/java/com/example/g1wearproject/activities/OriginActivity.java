package com.example.g1wearproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.wear.widget.WearableLinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.g1wearproject.adapters.AirportAdapter;
import com.example.g1wearproject.adapters.PriceAdapter;
import com.example.g1wearproject.databinding.ActivityOriginBinding;
import com.example.g1wearproject.models.Airport;
import com.example.g1wearproject.models.Price;
import com.example.g1wearproject.utils.Helper;

import java.util.ArrayList;
import java.util.List;
// import toast
import android.widget.Toast;
public class OriginActivity extends AppCompatActivity {

    // Declaring variables and objects
    private ActivityOriginBinding binding;
    private AirportAdapter adapter;
    private List<Airport> airportList;

    private int temporaryOriginId = -1; // Initialize with an invalid ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiating View using View Binding
        binding = ActivityOriginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initializing View
        init();
    }

    private void startDestinationActivity() {
        if (temporaryOriginId != -1) { // Check if origin ID is valid
            Intent intent = new Intent(this, DestinationActivity.class);
            // Pass the origin ID to DestinationActivity
            intent.putExtra("origin_id", temporaryOriginId);
            startActivity(intent);
        } else {
            // Handle the case where origin ID is not selected
            Toast.makeText(this, "Please select an origin airport first", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        binding.originRecyclerView.setHasFixedSize(true);
        binding.originRecyclerView.setEdgeItemsCenteringEnabled(true);
        binding.originRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        // Initialize workout tasks list (load from SharedPreferences)

        airportList = Helper.loadOriginList(this);

        // If the list is null or empty, create a new list
        if (airportList == null) {
            airportList = new ArrayList<>();
        }

        if (!airportList.isEmpty()) {
            // get the max id from the list
            Price.nextId = airportList.get(airportList.size() - 1).getId() + 1;
        }

        adapter = new AirportAdapter(airportList, getApplicationContext(), airport -> {
            // Store the ID of the clicked origin airport temporarily
            temporaryOriginId = airport.getId();

            Toast.makeText(this, "Selected origin airport: " + airport.getName(), Toast.LENGTH_SHORT).show();
            startDestinationActivity();
            // Proceed to the destination selection process...
        });
        binding.originRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}