package com.example.client_in_time.models;

import java.util.ArrayList;
import java.util.List;



public class RestaurantsService {
    private List<Restaurant> restaurants;

    static private RestaurantsService instance;

    enum RC{
        SUCCESS,
        ERROR
    }

    interface LoadListener{
        void invoke(RC rc);
    }

    static public RestaurantsService getInstance(){
        if (RestaurantsService.instance == null)
            RestaurantsService.instance = new RestaurantsService();
        return RestaurantsService.instance;
    }
    private RestaurantsService(){
        restaurants = new ArrayList<>();
        String[] names = {"qwerty", "asdfgh", "zxcvbn"};
        for (int i = 0; i < names.length; i++){
            restaurants.add(new Restaurant(i, names[i]));
        }
    }

    public void loadRestaurants(LoadListener listener){
        RC rc = RC.SUCCESS;
    }
    public List<Restaurant> getRestaurants(){
        if (restaurants == null)
            return new ArrayList<>();

        return restaurants;
        //return restaurants == null ? new ArrayList<>() : restaurants;
    }

    public List<Dish> getDishes(Restaurant rest){
        return null;
    }

}
