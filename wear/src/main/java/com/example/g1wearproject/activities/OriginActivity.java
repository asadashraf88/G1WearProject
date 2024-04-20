package com.example.g1wearproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.g1wearproject.adapters.AirportAdapter;

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

    private LinearLayoutManager layoutManager;

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

        adapter = new AirportAdapter(airportList, this);

        adapter.setIsOriginAdapter(true);
        binding.originRecyclerView.setAdapter(adapter);

        recyclerViewSetup();

    }

    private void recyclerViewSetup() {
        // Step1: Setting layout manager
        layoutManager = new LinearLayoutManager(this);
        binding.originRecyclerView.setLayoutManager(layoutManager);

        // Step2: Attaching Scroll Listener & Reading Item Positions (For Item Selection Logic)
        binding.originRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        // Step3: Swipe Logic to close Origin Selection and intent to Destination Activity
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDestCallback(adapter));
        itemTouchHelper.attachToRecyclerView(binding.originRecyclerView);
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

            // onSwiped Intent to Destination Activity and Pass Origin Destination Code
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                // Check if the swiped item is the selected item
                if (adapter.isSelected(position)) {
                    Airport airport = airportList.get(position);
                    Intent intent = new Intent(OriginActivity.this, DestinationActivity.class);
                    intent.putExtra("origin_id", airport.getIataCode());
                    startActivity(intent);
                }
            }

            // Allowing swipe only for selected item in recyler view

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