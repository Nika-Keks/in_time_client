package com.example.client_in_time.models;

import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_in_time.api_in_time.HttpAPI;
import com.example.client_in_time.api_in_time.apitypes.Restaurant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<Restaurant>> restaurants;
    private final Scheduler scheduler = Schedulers.single();
    private final HttpAPI restaurantsAPI = HttpAPI.getInstance();
    public enum LoadRC{
        SUCCESS,
        ERROR
    }
    public interface LoadListener{
        void onLoaded(LoadRC rc, List<Restaurant> restaurants);
    }

    public MainViewModel() {
        restaurants = new MutableLiveData<>();
        restaurants.setValue(new ArrayList<Restaurant>());
    }

    public void loadRestaurants(LoadListener listener){
        Disposable d = getSingle()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
                .subscribe(restaurants1 -> {
                    restaurants.setValue(restaurants1);
                    listener.onLoaded(LoadRC.SUCCESS, restaurants1);
                }, throwable -> {
                    Log.e("DEG", throwable.toString());
                    listener.onLoaded(LoadRC.ERROR, new ArrayList<>());
                });
    }
    public LiveData<List<Restaurant>> getRestaurants() {
        return restaurants;
    }

    private Single<List<Restaurant>> getSingle(){
        Single<List<Restaurant>> out = Single.create(emitter -> {
            List<Restaurant> rests = restaurantsAPI.getRestaurants(0, 10);
            emitter.onSuccess(rests);
        });
        return out;
    }
}
