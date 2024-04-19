package com.example.g1wearproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1wearproject.databinding.AirportItemBinding;
import com.example.g1wearproject.models.Airport;

import java.util.List;

public class AirportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<Airport> airportList;
    private final OnAirportClickListener clickListener;
    AirportItemBinding binding;

    public interface OnAirportClickListener {
        void onAirportClick(Airport airport);
    }

    public AirportAdapter(List<Airport> airportList, Context applicationContext, OnAirportClickListener clickListener) {
        super();
        this.airportList = airportList;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = AirportItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Airport airport = airportList.get(position);
        ((ViewHolder) holder).bindView(airport);
    }

    @Override
    public int getItemCount() {
        // check if the list is null or empty
        if (airportList == null || airportList.isEmpty()) {
            return 0;
        }
        return airportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final AirportItemBinding binding;


        public ViewHolder(AirportItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bindView(Airport airport) {
            binding.textAirportName.setText(airport.getName());
            binding.textAirportIataCode.setText(airport.getIataCode());
        }

        @Override
        public void onClick(View v) {
            // Retrieve the position of the clicked item
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Get the clicked airport
                Airport clickedAirport = airportList.get(position);
                // Call the onAirportClick method of the clickListener interface
                clickListener.onAirportClick(clickedAirport);
            }
        }

    }
}
