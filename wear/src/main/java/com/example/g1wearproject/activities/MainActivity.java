package com.example.g1wearproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.wear.widget.WearableLinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.g1wearproject.adapters.PriceAdapter;
import com.example.g1wearproject.databinding.ActivityMainBinding;
import com.example.g1wearproject.models.Price;
import com.example.g1wearproject.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    PriceAdapter adapter;
    List<Price> priceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiating View using View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.pricesRecyclerView.setHasFixedSize(true);
        binding.pricesRecyclerView.setEdgeItemsCenteringEnabled(true);
        binding.pricesRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        // Initialize workout tasks list (load from SharedPreferences)

        priceList = Helper.loadRecordedPrices(getSharedPreferences(Helper.PRICES_DB, MODE_PRIVATE));

        // If the list is null or empty, create a new list
        if (priceList == null) {
            priceList = new ArrayList<>();
        }


        if (!priceList.isEmpty()) {
            // get the max id from the list
            Price.nextId = priceList.get(priceList.size() - 1).getId() + 1;
        }

        adapter = new PriceAdapter(priceList, getApplicationContext());
        binding.pricesRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.buttonNewSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OriginActivity.class);
            startActivity(intent);
        });
    }
}