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

public class OriginActivity extends AppCompatActivity {

    // Declaring variables and objects
    private ActivityOriginBinding binding;
    private AirportAdapter adapter;
    private List<Airport> airportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiating View using View Binding
        binding = ActivityOriginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initializing View
        init();
    }

    private void init() {
        binding.originRecyclerView.setHasFixedSize(true);
        binding.originRecyclerView.setEdgeItemsCenteringEnabled(true);
        binding.originRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        // Initialize workout tasks list (load from SharedPreferences)

        airportList = Helper.loadOriginList(getSharedPreferences(Helper.PRICES_DB, MODE_PRIVATE));

        // If the list is null or empty, create a new list
        if (airportList == null) {
            airportList = new ArrayList<>();
        }

        if (!airportList.isEmpty()) {
            // get the max id from the list
            Price.nextId = airportList.get(airportList.size() - 1).getId() + 1;
        }

        adapter = new AirportAdapter(airportList, getApplicationContext());
        binding.originRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        binding.buttonNewSeach.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, OriginActivity.class);
//            startActivity(intent);
//        });
    }

}