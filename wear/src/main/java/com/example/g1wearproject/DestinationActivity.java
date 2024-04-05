package com.example.g1wearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.g1wearproject.databinding.ActivityDestinationBinding;
import com.example.g1wearproject.databinding.ActivityOriginBinding;

import java.util.List;
import java.util.stream.Collectors;

public class DestinationActivity extends AppCompatActivity {

    // Declaring variables and objects
    private ActivityDestinationBinding binding;
    private LinearLayoutManager layoutManager;
    private AirportAdapter airportAdapter;
    private List<Airport> airportList;

    String Origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_destination);

        // Instantiating View using View Binding
        binding = ActivityDestinationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieving Selected Origin
        Origin = retrieveOrigin();

        // Initializing View
        init(Origin);
    }

    private String retrieveOrigin() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedIataCode")) {
            return intent.getStringExtra("selectedIataCode");
        } else {
            Toast.makeText(this, "Origin not found", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void init(String Origin){
        // Hiding action bar onCreate
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Accessing Recycler View
        binding.destinationRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        // Preparing array to Initialize RecyclerView (filtering out origin)
        airportList = Airport.getAirportList().stream()
                .filter(airport -> !airport.getIataCode().equals(Origin))
                .collect(Collectors.toList());

        // Set adapter
        airportAdapter = new AirportAdapter(this, airportList);
        airportAdapter.setIsOriginAdapter(false);
        binding.destinationRecyclerView.setAdapter(airportAdapter);

        // RecyclerView Setup
        recyclerViewSetup();
    }

    private void recyclerViewSetup() {

        // Step1: Setting layout manager
        layoutManager = new LinearLayoutManager(this);
        binding.destinationRecyclerView.setLayoutManager(layoutManager);

        // Step2: Attaching Scroll Listener & Reading Item Positions (For Item Selection Logic)
        binding.destinationRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    // Get the position of the center item
                    int topRowItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int bottomRowItemPosition = layoutManager.findLastVisibleItemPosition();
                    int centerItemPosition = (topRowItemPosition + bottomRowItemPosition) / 2;

                    // Set the selected item
                    airportAdapter.setSelectedItem(centerItemPosition);
                }
            }
        });

        // Step3: Swipe Logic to close Destination Selection and intent to TravelDate Activity
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDestCallback(airportAdapter));
        itemTouchHelper.attachToRecyclerView(binding.destinationRecyclerView);
    }

    // Implementing Call Back on Selected Item Swipe
    private class SwipeToDestCallback extends ItemTouchHelper.SimpleCallback {
        private AirportAdapter adapter;
        // Constructor
        public SwipeToDestCallback(AirportAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.adapter = adapter;
        }

        // onMove default function Not Used as per swipe logic
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        // onSwiped Intent to TravelDate Activity and Pass Origin + Destination Code
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            // Check if the swiped item is the selected item
            if (adapter.isSelected(position)) {
                Airport airport = airportList.get(position);
                Intent intent = new Intent(DestinationActivity.this, TravelDateActivity.class);
                intent.putExtra("destinationCode", airport.getIataCode());
                intent.putExtra("originCode", Origin);
                startActivity(intent);
            }
        }

        // Allowing swipe only for selected item in recycler view

        @Override
        public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int position = viewHolder.getAdapterPosition();

            // Allow swipe only for the selected item
            if (adapter.isSelected(position)) {
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            return 0;  // Disable swipe for unselected items
        }
    }
}