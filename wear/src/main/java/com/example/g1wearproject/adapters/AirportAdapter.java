package com.example.g1wearproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1wearproject.databinding.AirportItemBinding;
import com.example.g1wearproject.databinding.PriceItemBinding;
import com.example.g1wearproject.models.Airport;
import com.example.g1wearproject.models.Price;

import java.util.List;

public class AirportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<Airport> airportList;
    AirportItemBinding binding;

    public AirportAdapter(List<Airport> airportList, Context applicationContext) {
        super();
        this.airportList = airportList;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AirportItemBinding rowBinding;
        public ViewHolder(AirportItemBinding binding) {
            super(binding.getRoot());
            this.rowBinding = binding;
        }

        public void bindView(Airport airport) {
            rowBinding.textAirportName.setText(airport.getName());
            rowBinding.textAirportIataCode.setText(airport.getIataCode());
        }
    }
}
