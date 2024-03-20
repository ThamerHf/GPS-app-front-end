package com.akatsuki.gps_app_front.api;

import com.akatsuki.gps_app_front.data.model.entity.Collection;
import com.akatsuki.gps_app_front.data.model.entity.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface CollectionApi {
    @Headers({ "Content-Type: application/json"})
    @GET("tags")
    Call<List<Collection>> getCollections(@Header("Authorization") String authToken);
}
