package com.example.client_in_time.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.client_in_time.databinding.FragmentMainBinding;
import com.example.client_in_time.models.Restaurant;
import com.example.client_in_time.models.RestaurantsAdapter;

import java.util.List;

public class MainFragment extends BaseFragment {

    FragmentMainBinding binding;
    MainViewModel mainViewModel;
    RestaurantsAdapter restaurantsAdapter;

    enum State{
        INIT,
        LOADING,
        SHOW_LIST,
        EMPTY_LIST,
        LOAD_ERROR
    }
    State state = State.INIT;

    MainViewModel.LoadListener loadListener = new MainViewModel.LoadListener() {
        @Override
        public void onLoaded(MainViewModel.LoadRC rc, List<Restaurant> restaurants) {
            if (binding != null)
                goneAll(binding);
            if (rc == MainViewModel.LoadRC.SUCCESS){
                if (restaurants.size() == 0) {
                    if (binding != null)
                        binding.mainEmptyListText.setVisibility(View.VISIBLE);
                    state = State.EMPTY_LIST;
                }
                else {
                    if (binding != null)
                        binding.mainRestaurantList.setVisibility(View.VISIBLE);
                    state = State.SHOW_LIST;
                }
            }
            else if (rc == MainViewModel.LoadRC.ERROR){
                if (binding != null)
                    binding.mainListLoadError.setVisibility(View.VISIBLE);
                state = State.LOAD_ERROR;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = FragmentMainBinding.inflate(inflater, container, false);
        goneAll(binding);
        binding.mainLoadTryAgainBtn.setOnClickListener(view -> {
            goneAll(binding);
            binding.mainProgress.setVisibility(View.VISIBLE);
            state = State.LOADING;
            mainViewModel.loadRestaurants(loadListener);
        });
        restaurantsAdapter = new RestaurantsAdapter(new RestaurantsAdapter.Listener() {
            @Override
            public void invoke(Restaurant restaurant) {
                Toast.makeText(getContext(), restaurant.name, Toast.LENGTH_SHORT).show();
            }
        });

        binding.mainRestaurantList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.mainRestaurantList.setAdapter(restaurantsAdapter);

        mainViewModel.getRestaurants().observe(getViewLifecycleOwner(), restaurants -> {
            restaurantsAdapter.setRestaurants(restaurants);
        });

        switch (state){
            case INIT:
                binding.mainProgress.setVisibility(View.VISIBLE);
                mainViewModel.loadRestaurants(loadListener);
                state = State.LOADING;
                break;
            case LOADING:
                binding.mainProgress.setVisibility(View.VISIBLE);
                break;
            case SHOW_LIST:
                binding.mainRestaurantList.setVisibility(View.VISIBLE);
                break;
            case EMPTY_LIST:
                binding.mainEmptyListText.setVisibility(View.VISIBLE);
                break;
            case LOAD_ERROR:
                binding.mainListLoadError.setVisibility(View.VISIBLE);
                break;
        };
        Toast.makeText(getContext(), "main", Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
