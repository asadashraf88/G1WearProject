package com.example.g1wearproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1wearproject.databinding.PriceItemBinding;
import com.example.g1wearproject.models.Price;

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

        public void bindView(Price price) {
            Log.i("PriceAdapter", "bindView: " + price.getId());
            rowBinding.textOrigin.setText(price.getOrigin() + "");
            rowBinding.textDestination.setText(price.getDestination() + "");
            rowBinding.textDepartureDate.setText(price.getDepartureDate().toString());
            rowBinding.textReturnDate.setText(price.getReturnDate().toString());
            rowBinding.textPrice.setText(price.getPrice() + "");
        }
    }
}
