package com.example.client_in_time.models;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.client_in_time.api_in_time.HttpAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SigninViewModel extends ViewModel {
    public HttpAPI httpAPI = HttpAPI.getInstance();
    private final Scheduler scheduler = Schedulers.single();

    public enum LoginRC{
        SUCCESS,
        INVALID,
        ERROR
    }

    public interface SigninListener{
        void onSignin(SigninViewModel.LoginRC rc);
    }

    public void signin(String name, String email, String password, SigninViewModel.SigninListener listener){
        Single<Integer> single = Single.create(emitter -> {
            Integer rc = httpAPI.signin(name, email, password);
            emitter.onSuccess(rc);
        });
        Disposable d = single
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    Log.e("DEB", integer.toString());
                    SigninViewModel.LoginRC rc;
                    switch (integer) {
                        case HTTP_OK:
                            rc = SigninViewModel.LoginRC.SUCCESS;
                            break;
                        case HTTP_FORBIDDEN:
                            rc = SigninViewModel.LoginRC.INVALID;
                            break;
                        default:
                            rc = SigninViewModel.LoginRC.ERROR;
                    }
                    listener.onSignin(rc);
                });
    }

}
