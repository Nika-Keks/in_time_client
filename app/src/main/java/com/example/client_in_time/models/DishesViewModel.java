package com.example.client_in_time.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_in_time.api_in_time.HttpAPI;
import com.example.client_in_time.api_in_time.apitypes.Dish;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DishesViewModel extends ViewModel {

    private MutableLiveData<List<Dish>> dishes = new MutableLiveData<>(new ArrayList<>());
    private final Scheduler scheduler = Schedulers.single();
    private final HttpAPI api = HttpAPI.getInstance();

    public enum LoadRC{
        SUCCESS,
        ERROR
    }
    public interface LoadListener{
        void onLoad(LoadRC rc, List<Dish> dishes);
    }

    public void loadDishes(int page, int per_page, int restaurantId, LoadListener listener){
        Single<List<Dish>> single = Single.create(emitter -> {
            emitter.onSuccess(api.getDishes(page, per_page, restaurantId));
        });
        Disposable d = single
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dishes1 -> {
                    dishes.setValue(dishes1);
                    listener.onLoad(LoadRC.SUCCESS, dishes1);
                }, throwable -> {
                    Log.e("DEB", throwable.toString());
                    listener.onLoad(LoadRC.ERROR, new ArrayList<>());
                });
    }

    public LiveData<List<Dish>> getDishes(){
        return dishes;
    }
}
