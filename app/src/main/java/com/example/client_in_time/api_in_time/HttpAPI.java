package com.example.client_in_time.api_in_time;

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_OK;

import android.util.Log;

import com.example.client_in_time.api_in_time.apitypes.Dish;
import com.example.client_in_time.api_in_time.apitypes.DishesResponse;
import com.example.client_in_time.api_in_time.apitypes.LoginResponse;
import com.example.client_in_time.api_in_time.apitypes.Restaurant;
import com.example.client_in_time.api_in_time.apitypes.RestaurantsResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;



public class HttpAPI {

    HashSet<String> preferences = new HashSet<>();
    OkHttpClient.Builder clientBuilder;
    private final String url;
    private final Retrofit retrofit;
    public final RestAPI restAPI;
    public final Gson gson = new Gson();

    private static final HttpAPI instance = new HttpAPI();

    public static HttpAPI getInstance(){
        return instance;
    }

    public interface RestAPI{
        @Headers("accept: application/json")
        @POST("Authentication/login")
        Call<LoginResponse> login(@Query("email") String email, @Query("password") String password);

        @Headers("accept: application/json")
        @GET("Restaurant/restaurants")
        Call<RestaurantsResponse> getRestaurants(@Query("page") int page, @Query("per_page") int per_page);

        @Headers("accept: application/json")
        @GET("Dish/dishes/{restaurantId}")
        Call<DishesResponse> getDishes(@Path("restaurantId") int restaurantId, @Query("page") int page, @Query("per_page") int per_page);

        @Headers("accept: application/json")
        @POST("Authentication/signup")
        Call<LoginResponse> signin(@Query("name") String name, @Query("email") String email, @Query("password") String password);
    }
    private HttpAPI(){
        url = "http://10.0.2.2:5000";
        clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(preferences))
                .addInterceptor(new ReceivedCookiesInterceptor(preferences));
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientBuilder.build())
                .build();
        restAPI = retrofit.create(RestAPI.class);
    }

    public List<Restaurant> getRestaurants(int page, int per_page){
        try {
            Response<RestaurantsResponse> response = restAPI.getRestaurants(page, per_page).execute();

            return Arrays.asList(response.body().items);
        }
        catch (Throwable e) {
            Log.e("DEB", e.toString());
            return new ArrayList<>();
        }
    }

    public List<Dish> getDishes(int page, int per_page, int restaurantId){
        try{
            Response<DishesResponse> dishesResponse = restAPI.getDishes(restaurantId, page, per_page).execute();
            if (dishesResponse.code() == HTTP_OK)
                return Arrays.asList(dishesResponse.body().items);
        }
        catch (Throwable e){
            Log.e("DEB", e.toString());
        }
        return new ArrayList<>();
    }

    public int login(String email, String password){
        try {
            Response<LoginResponse> loginResponse = restAPI.login(email, password).execute();
            return loginResponse.code();
        }
        catch (Throwable e){
            Log.e("DEB", e.toString());
        }
        return 400;
    }

    public int signin(String name, String email, String password){
        try {
            Response<LoginResponse> loginResponse = restAPI.signin(name, email, password).execute();
            if (loginResponse.code() == HTTP_OK)
                return login(email, password);
            return loginResponse.code();
        }
        catch (Throwable e){
            Log.e("DEB", e.toString());
        }
        return 400;
    }
}


