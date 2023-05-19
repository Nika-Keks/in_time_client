package com.example.client_in_time.api_in_time;

import android.util.Log;

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
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
            Response<LoginResponse> lr = restAPI.login("qwe", "qwe").execute();
            Response<RestaurantsResponse> response = restAPI.getRestaurants(page, per_page).execute();

            return Arrays.asList(response.body().items);
        }
        catch (Throwable e) {
            Log.e("DEB", e.toString());
            return new ArrayList<>();
        }
    }
}


