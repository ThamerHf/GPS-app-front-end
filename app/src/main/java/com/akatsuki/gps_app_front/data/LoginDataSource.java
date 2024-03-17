package com.akatsuki.gps_app_front.data;

import android.util.Log;

import com.akatsuki.gps_app_front.api.AuthenticationApi;
import com.akatsuki.gps_app_front.callback.LoginCallback;
import com.akatsuki.gps_app_front.config.AppInterceptor;
import com.akatsuki.gps_app_front.data.model.dto.request.LoginRequestDto;
import com.akatsuki.gps_app_front.data.model.dto.response.AuthenticatedUserResponseDto;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;
import com.akatsuki.gps_app_front.data.model.entity.LoggedInUser;
import com.akatsuki.gps_app_front.data.network.RetrofitClient;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public void login(String username, String password, final LoginCallback callback) {
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
                        LoggedInUser loggedInUser = new LoggedInUser(tokenResponse.getToken(), username);
                        callback.onLoginSuccess(loggedInUser);
                    } else {
                        // Gestion d'une réponse inattendue du serveur
                        callback.onLoginError(new IOException("Unexpected response from server"));
                    }
                } else {
                    // Gérer les réponses d'erreur ici
                    Log.e("aaaaaaaaaa " , "failllllllllllllllll");
                    callback.onLoginError(new IOException("Login failed"));
                }
            }

            @Override
            public void onFailure(Call<TokenResponseDto> call, Throwable t) {
                // Gérer les échecs de la requête ici
                Log.e("bbbbbbbbbbbb " , "bbbbbbbbbbbbbbbbbbbb ");
                callback.onLoginError(new IOException("Error during login", t));
            }
        });
    }

    /*public void getAuthenticatedUser(String token, final LoginCallback<LoggedInUser> callback) {
        AuthenticationApi authenticationApi = RetrofitClient.getClient()
                .create(AuthenticationApi.class);

        Call<AuthenticatedUserResponseDto> call = authenticationApi.getAuthenticatedUser(
                token);
        call.enqueue(new Callback<AuthenticatedUserResponseDto>() {
            @Override
            public void onResponse(Call<AuthenticatedUserResponseDto> call,
                                   Response<AuthenticatedUserResponseDto> response) {
                if (response.isSuccessful()) {
                    AuthenticatedUserResponseDto authenticatedUser = response.body();
                    if (authenticatedUser != null) {
                        LoggedInUser loggedInUser = new LoggedInUser(authenticatedUser.getUserName(),
                                authenticatedUser.getFirstName(),
                                authenticatedUser.getLastName(),
                                authenticatedUser.getEmail());
                        callback.onLoginSuccess(loggedInUser);
                    } else {
                        // Gestion d'une réponse inattendue du serveur
                        callback.onLoginError(new IOException("Unexpected response from server"));
                    }
                } else {
                    // Gérer les réponses d'erreur ici
                    callback.onLoginError(new IOException("Login failed"));
                }
            }

            @Override
            public void onFailure(Call<AuthenticatedUserResponseDto> call, Throwable t) {
                // Gérer les échecs de la requête ici
                callback.onLoginError(new IOException("Error during login", t));
            }
        });
    }*/

    public void logout() {
        // TODO: révoquer l'authentification
    }

}
