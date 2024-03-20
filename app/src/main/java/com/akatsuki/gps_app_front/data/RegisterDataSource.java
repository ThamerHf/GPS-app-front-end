package com.akatsuki.gps_app_front.data;

import com.akatsuki.gps_app_front.api.AuthenticationApi;
import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.model.dto.request.RegisterRequestDto;
import com.akatsuki.gps_app_front.data.model.dto.response.GenericResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.RegisterMessage;
import com.akatsuki.gps_app_front.data.network.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDataSource {


    public void register(String firstName, String lastName, String username,
                         String email, String password,
                         final AppCallback<RegisterMessage> callback) {
        AuthenticationApi authenticationApi = RetrofitClient.getClient()
                .create(AuthenticationApi.class);

        Call<GenericResponseDto> call = authenticationApi.register(
                new RegisterRequestDto(firstName, lastName,
                        username, email, password));

        call.enqueue(new Callback<GenericResponseDto>() {
            @Override
            public void onResponse(Call<GenericResponseDto> call, Response<GenericResponseDto> response) {
                if (response.isSuccessful()) {
                    GenericResponseDto responseDto = response.body();
                    if (responseDto != null && responseDto.getMessage() != null) {
                        // Création de l'utilisateur connecté avec le token reçu
                        RegisterMessage registerMessage = new RegisterMessage(
                                responseDto.getMessage());
                        callback.onCallBackSuccess(registerMessage);
                    } else {
                        // Gestion d'une réponse inattendue du serveur
                        callback.onCallBackError(new IOException("EXISTS"));
                    }
                } else {
                    // Gérer les réponses d'erreur ici
                    callback.onCallBackError(new IOException("EXISTS"));
                }
            }

            @Override
            public void onFailure(Call<GenericResponseDto> call, Throwable t) {
                callback.onCallBackError(new IOException("Error during register", t));
            }
        });

    }
}
