package com.example.client_in_time.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.client_in_time.R;
import com.example.client_in_time.api_in_time.apitypes.Restaurant;
import com.example.client_in_time.databinding.FragmentRestaurantsBinding;
import com.example.client_in_time.models.MainViewModel;
import com.example.client_in_time.models.RestaurantsAdapter;

import java.util.List;

public class RestaurantsFragment extends BaseFragment {

    FragmentRestaurantsBinding binding;
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
        binding = FragmentRestaurantsBinding.inflate(inflater, container, false);
        goneAll(binding);
        binding.mainLoadTryAgainBtn.setOnClickListener(view -> {
            goneAll(binding);
            binding.mainProgress.setVisibility(View.VISIBLE);
            state = State.LOADING;
            mainViewModel.loadRestaurants(loadListener);
        });
        restaurantsAdapter = new RestaurantsAdapter(restaurant -> {
            Toast.makeText(getContext(), restaurant.description, Toast.LENGTH_SHORT).show();
            goToDishes(restaurant);
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
        }
        Toast.makeText(getContext(), "main", Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void goToDishes(Restaurant restaurant){
//        DishesFragment dishesFragment = new DishesFragment(restaurant);
//        FragmentManager fragmentManager = getParentFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.fragment_main, dishesFragment, restaurant.description);
//        goneAll(binding);
//        fragmentTransaction.commit();
        Bundle bundle = new Bundle();
        bundle.putInt("restaurantId", restaurant.id);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_main);
        if (navController.getCurrentDestination() != navController.findDestination(R.id.fragment_dishes)) {
            navController.navigate(R.id.action_navigation_main_to_dishesFragment, bundle);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
