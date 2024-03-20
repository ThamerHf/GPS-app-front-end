package com.akatsuki.gps_app_front.ui.collection;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akatsuki.gps_app_front.api.CollectionApi;
import com.akatsuki.gps_app_front.api.LocationApi;
import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;
import com.akatsuki.gps_app_front.data.model.entity.Collection;
import com.akatsuki.gps_app_front.data.model.entity.Location;
import com.akatsuki.gps_app_front.data.network.RetrofitClient;
import com.akatsuki.gps_app_front.data.repositories.repository.AuthenTokenRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final AuthenTokenRepository authenTokenRepository;
    public CollectionViewModel(AuthenTokenRepository authenTokenRepository) {
        this.authenTokenRepository = authenTokenRepository;
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }
    public void getCollections(final AppCallback<List<Collection>> callback) {
        Log.d("Collection", "test Bennai Meziane");
        this.authenTokenRepository.getAuthToken(new AppCallback<AuthenToken>() {

            @Override
            public void onCallBackSuccess(AuthenToken authenToken) {
                System.out.println("1");
                CollectionApi collectionApi = RetrofitClient.getClient()
                        .create(CollectionApi.class);
            Call<List<Collection>> call = collectionApi.getCollections("Bearer " +
                        authenToken.getToken());
                call.enqueue(new Callback<List<Collection>>() {

                    @Override
                    public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                        if (response.isSuccessful()) {
                            Log.d("location", "onResponse");
                            List<Collection> collections = response.body();
                            if (collections != null) {
                                callback.onCallBackSuccess(collections);
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
                    public void onFailure(Call<List<Collection>> call, Throwable t) {

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