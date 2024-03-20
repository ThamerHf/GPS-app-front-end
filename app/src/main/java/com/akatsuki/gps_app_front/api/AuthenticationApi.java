package com.akatsuki.gps_app_front.api;

import com.akatsuki.gps_app_front.data.model.dto.request.LoginRequestDto;
import com.akatsuki.gps_app_front.data.model.dto.request.RegisterRequestDto;
import com.akatsuki.gps_app_front.data.model.dto.response.AuthenticatedUserResponseDto;
import com.akatsuki.gps_app_front.data.model.dto.response.GenericResponseDto;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthenticationApi {

    @POST("auth/login")
    Call<TokenResponseDto> login(@Body LoginRequestDto requestDto);

    @POST("auth/register")
    Call<GenericResponseDto> register(@Body RegisterRequestDto requestDto);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("auth/auhtenticatedUser")
    Call<AuthenticatedUserResponseDto> getAuthenticatedUser(
            @Header("Authorization") String authToken);

}
