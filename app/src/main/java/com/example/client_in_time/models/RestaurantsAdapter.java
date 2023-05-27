package com.example.client_in_time.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_in_time.R;
import com.example.client_in_time.api_in_time.apitypes.Restaurant;
import com.example.client_in_time.databinding.RestaurantItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder> {
    private List<Restaurant> restaurants;
    Listener listener;

    static class RestaurantsViewHolder extends RecyclerView.ViewHolder {
        public RestaurantItemBinding binding;
        public RestaurantsViewHolder(RestaurantItemBinding _binding){
            super(_binding.getRoot());
            binding = _binding;
        }
    }

    public interface Listener {
        void invoke(Restaurant restaurant);
    }

    public RestaurantsAdapter(Listener listener){
        this.listener = listener;
        this.restaurants = new ArrayList<>();
    }

    public void setRestaurants(List<Restaurant> restaurants){
        this.restaurants.clear();
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RestaurantItemBinding binding = RestaurantItemBinding.inflate(inflater, parent, false);

        return new RestaurantsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder holder, int position) {
        Restaurant rest = restaurants.get(position);
        if (rest == null)
            return;
        holder.binding.restName.setText(Integer.toString(rest.id));
        holder.binding.restDesc.setText(rest.description);
        holder.binding.restImage.setImageResource(R.drawable.baseline_restaurant_menu_24);
        holder.binding.getRoot().setOnClickListener(view -> listener.invoke(rest));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
