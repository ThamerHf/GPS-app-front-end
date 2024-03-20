package com.akatsuki.gps_app_front.api;

import com.akatsuki.gps_app_front.data.model.dto.request.LoginRequestDto;
import com.akatsuki.gps_app_front.data.model.dto.response.AuthenticatedUserResponseDto;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LocationApi {
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("locations")
    Call<List<Location>> getLocations();


}
