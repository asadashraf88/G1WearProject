package com.example.g1wearproject.adapters;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1wearproject.databinding.PriceItemBinding;
import com.example.g1wearproject.models.Airport;
import com.example.g1wearproject.models.Price;
import com.example.g1wearproject.utils.Helper;

import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<Price> priceList;
    PriceItemBinding binding;

    public PriceAdapter(List<Price> priceList, Context applicationContext) {
        super();
        this.priceList = priceList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = PriceItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Price price = priceList.get(position);
        ((ViewHolder) holder).bindView(price);
    }

    @Override
    public int getItemCount() {
        // check if the list is null or empty
        if (priceList == null || priceList.isEmpty()) {
            return 0;
        }
        return priceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PriceItemBinding rowBinding;
        public ViewHolder(PriceItemBinding binding) {
            super(binding.getRoot());
            this.rowBinding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Price price) {
            Context context = rowBinding.getRoot().getContext();
            // get airport name from the object and set it to the text view
            Airport orginAirport = Helper.getAirportById(context,price.getOrigin());
            Airport destinationAirport = Helper.getAirportById(context,price.getDestination());
            if (orginAirport == null || destinationAirport == null) {
                Log.e("PriceAdapter", "Airport not found");
                return;
            }
            rowBinding.textOriginAndDest.setText(orginAirport.getIataCode() + " - " + destinationAirport.getIataCode());

            float dispPrice = Math.round(price.getPrice() * 100.0f) / 100.0f;

            rowBinding.textPrice.setText("Best Price: $" + String.valueOf(dispPrice));
            // get just day and month from the date
            rowBinding.textDepartureDate.setText("Departure: " + Helper.getFormattedDate(price.getDepartureDate()));
            rowBinding.textReturnDate.setText("Return: " + Helper.getFormattedDate(price.getReturnDate()));
        }
    }
}
