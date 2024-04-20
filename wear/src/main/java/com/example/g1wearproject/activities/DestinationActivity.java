package com.example.g1wearproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.activity.ConfirmationActivity;
import androidx.wear.widget.WearableLinearLayoutManager;

import com.example.g1wearproject.adapters.AirportAdapter;
import com.example.g1wearproject.databinding.ActivityDestinationBinding;
import com.example.g1wearproject.models.Airport;
import com.example.g1wearproject.models.Price;
import com.example.g1wearproject.utils.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DestinationActivity extends AppCompatActivity {

    // Declaring variables and objects
    private ActivityDestinationBinding binding;
    private AirportAdapter adapter;
    private List<Airport> airportList;

    private LinearLayoutManager layoutManager;

    String temporaryOriginId;
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
            temporaryOriginId = extras.getString("origin_id");
        } else {
            // Handle the case where origin ID is not passed
            Toast.makeText(this, "Origin ID not found", Toast.LENGTH_SHORT).show();
        }
        // Initializing View
        init();
    }

    private void init() {
        binding.destinationRecyclerView.setHasFixedSize(true);
        binding.destinationRecyclerView.setEdgeItemsCenteringEnabled(true);
        binding.destinationRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        // Preparing array to Initialize RecyclerView (filtering out origin)
        airportList = Helper.loadOriginList(this).stream()
                .filter(airport -> !airport.getIataCode().equals(temporaryOriginId))
                .collect(Collectors.toList());

        // If the list is null or empty, create a new list
        if (airportList == null) {
            airportList = new ArrayList<>();
        }

        if (!airportList.isEmpty()) {
            // get the max id from the list
            Price.nextId = airportList.get(airportList.size() - 1).getId() + 1;
        }

        adapter = new AirportAdapter(airportList, this);
        adapter.setIsOriginAdapter(false);

        binding.destinationRecyclerView.setAdapter(adapter);

        recyclerViewSetup();
        //adapter.notifyDataSetChanged();

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
                    adapter.setSelectedItem(centerItemPosition);
                }
            }
        });

        // Step3: Swipe Logic to close Destination Selection and intent to TravelDate Activity
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDestCallback(adapter));
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
                intent.putExtra("destination_id", airport.getIataCode());
                intent.putExtra("origin_id", temporaryOriginId);
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
