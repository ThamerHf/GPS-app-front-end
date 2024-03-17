package com.akatsuki.gps_app_front.data.network;

import com.akatsuki.gps_app_front.config.AppInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:9000/app/v1/";

    public static Retrofit getClient() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        // Si retrofit est déjà initialisé, définissez le client OkHttpClient
        if (retrofit != null) {
            retrofit = retrofit.newBuilder()
                    .client(okHttpClient)
                    .build();
        }
    }

}
