package com.example.g1wearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1wearproject.databinding.ItemAirportBinding;

import java.util.List;

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.AirportViewHolder> {

    private final List<Airport> airportList;
    private int selectedItem = RecyclerView.NO_POSITION;
    private Context mContext;
    private boolean isOriginAdapter;

    public AirportAdapter(Context context, List<Airport> airportList) {
        this.mContext = context;
        this.airportList = airportList;
    }

    public void setIsOriginAdapter(boolean isOriginAdapter) {
        this.isOriginAdapter = isOriginAdapter;
    }

    @NonNull
    @Override
    public AirportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAirportBinding binding = ItemAirportBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AirportViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportViewHolder holder, int position) {
        Airport airport = airportList.get(position);
        holder.bind(airport, position == selectedItem, isOriginAdapter);
    }

    @Override
    public int getItemCount() {
        return airportList.size();
    }

    public void setSelectedItem(int position) {
        if (position != selectedItem) {
            selectedItem = position;
            notifyDataSetChanged(); // Manage Swipeable Item upon item selection
        }
    }

    // Return Selected Item Position for Swipe Logic
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
