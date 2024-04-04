package com.example.g1wearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

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
    }
}