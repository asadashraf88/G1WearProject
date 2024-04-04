package com.example.g1wearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.g1wearproject.databinding.ActivityOriginBinding;

import java.util.List;

public class OriginActivity extends AppCompatActivity {

    // Declaring variables and objects
    private ActivityOriginBinding binding;
    private LinearLayoutManager layoutManager;
    private AirportAdapter airportAdapter;

    private List<Airport> airportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_origin);

        // Instantiating View using View Binding
        binding = ActivityOriginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initializing View
        init();
    }

    private void init(){
        // Hiding action bar onCreate
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Accessing Recycler View
        binding.originRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        // Initialize RecyclerView
        airportList = Airport.getAirportList();

        // Set adapter
        airportAdapter = new AirportAdapter(this, airportList);
        airportAdapter.setIsOriginAdapter(true);
        binding.originRecyclerView.setAdapter(airportAdapter);

        // RecyclerView Setup
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
                    airportAdapter.setSelectedItem(centerItemPosition);
                }
            }
        });

        // Step3: Swipe Logic to close Origin Selection and intent to Destination Activity
        onSelectedItemSwipe();
    }

    private void onSelectedItemSwipe() {

        // Step 1: Attach ItemTouchHelper for Swipe Callback
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDestCallback(airportAdapter));
        itemTouchHelper.attachToRecyclerView(binding.originRecyclerView);

    }

    // SwipeToDestCallback inner class
    private class SwipeToDestCallback extends ItemTouchHelper.SimpleCallback {

        private AirportAdapter adapter;

        public SwipeToDestCallback(AirportAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            // Check if the swiped item is the selected item
            if (adapter.isSelected(position)) {
                Airport airport = airportList.get(position);
                Intent intent = new Intent(OriginActivity.this, DestinationActivity.class);
                intent.putExtra("selectedIataCode", airport.getIataCode());
                startActivity(intent);
            }
        }

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