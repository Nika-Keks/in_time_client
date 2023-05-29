package com.example.client_in_time.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.client_in_time.api_in_time.apitypes.Restaurant;
import com.example.client_in_time.databinding.FragmentDishesBinding;
import com.example.client_in_time.models.DishesAdapter;
import com.example.client_in_time.models.DishesViewModel;

public class DishesFragment extends BaseFragment{
    FragmentDishesBinding binding;
    DishesViewModel dishesViewModel;
    DishesAdapter dishesAdapter;
    Integer restaurantId;
    enum State{
        INIT,
        LOADING,
        SHOW_LIST,
        EMPTY_LIST,
        LOAD_ERROR
    }
    private State state = State.INIT;
    DishesViewModel.LoadListener loadListener = (rc, dishes) -> {
        if (binding != null) goneAll(binding);
        if (rc == DishesViewModel.LoadRC.SUCCESS){
            if (dishes.size() == 0) {
                if (binding != null) binding.mainEmptyListText.setVisibility(View.VISIBLE);
                state = DishesFragment.State.EMPTY_LIST;
            }
            else {
                if (binding != null) binding.dishesList.setVisibility(View.VISIBLE);
                state = DishesFragment.State.SHOW_LIST;
            }
        }
        else if (rc == DishesViewModel.LoadRC.ERROR){
            if (binding != null) binding.mainListLoadError.setVisibility(View.VISIBLE);
            state = DishesFragment.State.LOAD_ERROR;
        }
    };

    public DishesFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (restaurantId == null)
            restaurantId = getArguments().getInt("restaurantId");
        binding = FragmentDishesBinding.inflate(inflater, container, false);
        dishesViewModel = new ViewModelProvider(this).get(DishesViewModel.class);

        goneAll(binding);
        binding.mainLoadTryAgainBtn.setOnClickListener(view -> {
            goneAll(binding);
            binding.mainProgress.setVisibility(View.VISIBLE);
            state = DishesFragment.State.LOADING;
            dishesViewModel.loadDishes(0, 10, restaurantId, loadListener);
        });
        dishesAdapter = new DishesAdapter(dishes -> {
            Toast.makeText(getContext(), dishes.description, Toast.LENGTH_SHORT).show();
        });

        binding.dishesList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.dishesList.setAdapter(dishesAdapter);

        dishesViewModel.getDishes().observe(getViewLifecycleOwner(), dishes -> {
            dishesAdapter.setDishes(dishes);
        });

        switch (state){
            case INIT:
                binding.mainProgress.setVisibility(View.VISIBLE);
                dishesViewModel.loadDishes(0,10, restaurantId, loadListener);
                state = DishesFragment.State.LOADING;
                break;
            case LOADING:
                binding.mainProgress.setVisibility(View.VISIBLE);
                break;
            case SHOW_LIST:
                binding.dishesList.setVisibility(View.VISIBLE);
                break;
            case EMPTY_LIST:
                binding.mainEmptyListText.setVisibility(View.VISIBLE);
                break;
            case LOAD_ERROR:
                binding.mainListLoadError.setVisibility(View.VISIBLE);
                break;
        };
        Toast.makeText(getContext(), "dishes", Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}
