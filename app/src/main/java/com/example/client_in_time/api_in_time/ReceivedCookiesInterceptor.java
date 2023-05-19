package com.example.client_in_time.api_in_time;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    HashSet<String> preferences;

    public ReceivedCookiesInterceptor(HashSet<String> preferences){
        this.preferences = preferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            this.preferences.addAll(originalResponse.headers("Set-Cookie"));
        }

        return originalResponse;
    }
}
