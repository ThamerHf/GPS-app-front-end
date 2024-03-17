package com.akatsuki.gps_app_front.config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

public class AppInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Ajouter l'en-tête Content-Type si ce n'est pas déjà présent
        Request modifiedRequest = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(originalRequest.method(), originalRequest.body())
                .build();

        return chain.proceed(modifiedRequest);
    }
}

