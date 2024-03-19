package com.akatsuki.gps_app_front.data;

import com.akatsuki.gps_app_front.api.AuthenticationApi;
import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.model.dto.request.LoginRequestDto;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.LoggedInUser;
import com.akatsuki.gps_app_front.data.network.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDataSource {


    public void register(String username, String password, final AppCallback<TokenResponseDto> callback) {
        AuthenticationApi authenticationApi = RetrofitClient.getClient()
                .create(AuthenticationApi.class);

        Call<TokenResponseDto> call = authenticationApi.login(
                new LoginRequestDto(username, password));

        call.enqueue(new Callback<TokenResponseDto>() {
            @Override
            public void onResponse(Call<TokenResponseDto> call, Response<TokenResponseDto> response) {
                if (response.isSuccessful()) {
                    TokenResponseDto tokenResponse = response.body();
                    if (tokenResponse != null && tokenResponse.getToken() != null) {
                        // Création de l'utilisateur connecté avec le token reçu
                        LoggedInUser loggedInUser = new LoggedInUser(
                                tokenResponse.getToken(), username);
                        callback.onCallBackSuccess(tokenResponse);
                    } else {
                        // Gestion d'une réponse inattendue du serveur
                        callback.onCallBackError(new IOException("Unexpected response from server"));
                    }
                } else {
                    // Gérer les réponses d'erreur ici
                    callback.onCallBackError(new IOException("Login failed"));
                }
            }

            @Override
            public void onFailure(Call<TokenResponseDto> call, Throwable t) {
                callback.onCallBackError(new IOException("Error during login", t));
            }
        });

    }
}
