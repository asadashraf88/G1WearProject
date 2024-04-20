package com.example.g1wearproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1wearproject.R;
import com.example.g1wearproject.databinding.AirportItemBinding;
import com.example.g1wearproject.databinding.ItemAirportBinding;
import com.example.g1wearproject.models.Airport;

import java.util.List;

public class AirportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<Airport> airportList;
    ItemAirportBinding binding;
    private int selectedItem = RecyclerView.NO_POSITION;
    private boolean isOriginAdapter;

    public AirportAdapter(List<Airport> airportList, Context applicationContext) {
        super();
        this.airportList = airportList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemAirportBinding.inflate(inflater, parent, false);
        return new AirportViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Airport airport = airportList.get(position);
        ((AirportViewHolder) holder).bind(airport, position == selectedItem, isOriginAdapter);
    }

    public void setIsOriginAdapter(boolean isOriginAdapter) {
        this.isOriginAdapter = isOriginAdapter;
    }

    public void setSelectedItem(int position) {
        if (position != selectedItem) {
            selectedItem = position;
            notifyDataSetChanged(); // Manage Swipeable Item upon item selection
        }
    }

    @Override
    public int getItemCount() {
        // check if the list is null or empty
        if (airportList == null || airportList.isEmpty()) {
            return 0;
        }
        return airportList.size();
    }

    public boolean isSelected(int position) {
        return position == selectedItem;
    }

    static class AirportViewHolder extends RecyclerView.ViewHolder {

        private final ItemAirportBinding binding;

        public AirportViewHolder(ItemAirportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Airport airport, boolean isSelected, boolean isOriginAdapter) {
            binding.getRoot().setBackgroundResource(R.drawable.item_list_background);

            if (!airport.getName().isEmpty()) {
                binding.getRoot().setVisibility(View.VISIBLE);
            }

            // Implementing View (UI) Logic for Selected Airport
            if (isSelected) {
                binding.airportName.setText(airport.getIataCode());
                binding.userGuide.setText(airport.getName());
                binding.getRoot().setBackgroundResource(R.drawable.item_selected_background);
                binding.getRoot().setElevation(16f);
            } else {
                binding.airportName.setText(airport.getIataCode());
                binding.userGuide.setText(isOriginAdapter ? "Scroll to Select Origin" : "Scroll to Select Destination");
                binding.getRoot().setBackgroundResource(R.drawable.item_list_background);
                binding.getRoot().setElevation(8f);

                // Hiding Empty Rows: Empty Rows are required for View (UI) Logic
                if (airport.getName().isEmpty()) {
                    binding.getRoot().setVisibility(View.GONE);
                }
            }
        }
    }

}
