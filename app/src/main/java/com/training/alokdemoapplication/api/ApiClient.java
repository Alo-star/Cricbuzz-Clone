package com.training.alokdemoapplication.api;

import com.training.alokdemoapplication.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Builds a singleton Retrofit/OkHttp client for the cricket API.
 * The RapidAPI key/host headers are injected here via an OkHttp interceptor
 * so individual endpoint calls never need to deal with auth.
 */
public final class ApiClient {

    private static final String BASE_URL = "https://free-cricbuzz-cricket-api.p.rapidapi.com/";
    private static final String API_HOST = "free-cricbuzz-cricket-api.p.rapidapi.com";

    private static volatile Retrofit retrofit;
    private static volatile CricketApiService apiService;

    private ApiClient() {}

    public static CricketApiService getService() {
        if (apiService == null) {
            synchronized (ApiClient.class) {
                if (apiService == null) {
                    apiService = getRetrofit().create(CricketApiService.class);
                }
            }
        }
        return apiService;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(buildOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    private static OkHttpClient buildOkHttpClient() {
        Interceptor authInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .header("x-rapidapi-key", BuildConfig.RAPIDAPI_KEY)
                        .header("x-rapidapi-host", API_HOST);
                return chain.proceed(builder.build());
            }
        };

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }
}
