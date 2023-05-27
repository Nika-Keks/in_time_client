package com.example.client_in_time.models;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_in_time.R;
import com.example.client_in_time.api_in_time.apitypes.Dish;
import com.example.client_in_time.databinding.DishItemBinding;

import java.util.ArrayList;
import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishesViewHolder> {
    private List<Dish> dishes;
    DishesAdapter.Listener listener;

    static class DishesViewHolder extends RecyclerView.ViewHolder {
        public DishItemBinding binding;
        public DishesViewHolder(DishItemBinding _binding){
            super(_binding.getRoot());
            binding = _binding;
        }
    }

    public interface Listener {
        void invoke(Dish dish);
    }

    public DishesAdapter(DishesAdapter.Listener listener){
        this.listener = listener;
        this.dishes = new ArrayList<>();
    }

    public void setDishes(List<Dish> dishes){
        this.dishes.clear();
        this.dishes = dishes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DishesAdapter.DishesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DishItemBinding binding = DishItemBinding.inflate(inflater, parent, false);

        return new DishesAdapter.DishesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DishesAdapter.DishesViewHolder holder, int position) {
        Dish dish = dishes.get(position);
        if (dish == null)
            return;
        holder.binding.dishName.setText(Integer.toString(dish.id));
        holder.binding.dishDesc.setText(dish.description);
        holder.binding.dishImage.setImageResource(R.drawable.baseline_fastfood_64);
        holder.binding.getRoot().setOnClickListener(view -> listener.invoke(dish));
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }
}
