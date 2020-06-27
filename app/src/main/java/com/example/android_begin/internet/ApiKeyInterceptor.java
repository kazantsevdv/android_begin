package com.example.android_begin.internet;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {
    public static final String UNITS_METRIC = "metric";
    private static final String OPEN_WEATHER_API_KEY = "e1c7641d2f37202b27e9d0e06852f460";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url().newBuilder()
                .addQueryParameter("appid", OPEN_WEATHER_API_KEY)
                .addQueryParameter("units", UNITS_METRIC)
                .build();
        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }
}
