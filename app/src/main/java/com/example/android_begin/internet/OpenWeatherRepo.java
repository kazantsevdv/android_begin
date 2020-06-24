package com.example.android_begin.internet;

import com.example.android_begin.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherRepo {
    private static final String OPEN_WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/";
    private static OkHttpClient client;
    private static Retrofit retrofit;
    private static OpenWeatherApi api;

    private static OkHttpClient getAuthClient() {

        if (client == null) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder
                    .addInterceptor(new ApiKeyInterceptor())
                    .addInterceptor(logInterceptor);
            client = builder.build();
        }
        return client;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(OPEN_WEATHER_API_URL)
                    .client(getAuthClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static OpenWeatherApi getApiService() {
        if (api == null) {
            api = getRetrofit().create(OpenWeatherApi.class);
        }
        return api;
    }
}
