package com.example.g1wearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.g1wearproject.activities.OriginActivity;
import com.example.g1wearproject.databinding.ActivityTravelDateBinding;

public class TravelDateActivity extends AppCompatActivity implements View.OnClickListener {

    private @NonNull ActivityTravelDateBinding binding;
    String Origin;
    String Destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_travel_date);

        // Instantiating View using View Binding
        binding = ActivityTravelDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieving Selected Origin
        retrieveOriginAndDestination();

        // Initializing View
        init();
    }

    private void retrieveOriginAndDestination() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("originCode") && intent.hasExtra("destinationCode")) {
            Origin = intent.getStringExtra("originCode");
            Destination = intent.getStringExtra("destinationCode");
        } else {
            Toast.makeText(this, "Origin and Destination not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void init(){

        // Hiding Price View
        binding.priceView.setVisibility(View.GONE);

        // Attaching On Click Listener to Submit Button
        binding.btnSubmit.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View view) {
        if(binding.btnSubmit.getText()!="RESET") {
            binding.priceView.setVisibility(View.VISIBLE);
            binding.btnSubmit.setText("RESET");
        } else {
            Intent intent = new Intent(TravelDateActivity.this, OriginActivity.class);
            startActivity(intent);
        }
    }
}