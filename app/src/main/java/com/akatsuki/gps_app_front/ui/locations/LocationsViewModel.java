package com.akatsuki.gps_app_front.ui.location;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akatsuki.gps_app_front.api.LocationApi;
import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;
import com.akatsuki.gps_app_front.data.model.entity.Location;
import com.akatsuki.gps_app_front.data.model.entity.LoggedInUser;
import com.akatsuki.gps_app_front.data.network.RetrofitClient;
import com.akatsuki.gps_app_front.data.repositories.repository.AuthenTokenRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final AuthenTokenRepository authenTokenRepository;
    public LocationsViewModel(AuthenTokenRepository authenTokenRepository) {
        this.authenTokenRepository = authenTokenRepository;
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }
    public void getLocations(final AppCallback<List<Location>> callback) {
        Log.d("location", "test");
        this.authenTokenRepository.getAuthToken(new AppCallback<AuthenToken>() {

            @Override
            public void onCallBackSuccess(AuthenToken authenToken) {
                System.out.println("1");
                LocationApi locationApi = RetrofitClient.getClient()
                        .create(LocationApi.class);
                Call<List<Location>> call = locationApi.getLocations("Bearer " +
                        authenToken.getToken());
                call.enqueue(new Callback<List<Location>>() {

                    @Override
                    public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                        if (response.isSuccessful()) {
                            Log.d("location", "onResponse");
                            List<Location> locations = response.body();
                            if (locations != null) {
                                callback.onCallBackSuccess(locations);
                            } else {
                                // Gestion d'une réponse inattendue du serveur
                                callback.onCallBackError(new IOException("Unexpected response from server"));
                            }
                        } else {
                            // Gérer les réponses d'erreur ici
                            callback.onCallBackError(new IOException("Login failed"));
                            Log.d("location", "response unseccesful");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Location>> call, Throwable t) {
                        Log.d("location", "onFailure");
                    }

                });
            }

            @Override
            public void onCallBackError(IOException exception) {

            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }
}