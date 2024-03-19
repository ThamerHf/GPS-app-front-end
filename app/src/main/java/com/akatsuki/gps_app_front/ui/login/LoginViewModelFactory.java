package com.akatsuki.gps_app_front.ui.login;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.akatsuki.gps_app_front.data.LoginDataSource;
import com.akatsuki.gps_app_front.data.repositories.repository.LoginRepository;
import com.akatsuki.gps_app_front.data.database.databaseClient.AuthTokenDataBaseClient;
import com.akatsuki.gps_app_front.data.repositories.repository.AuthenTokenRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public LoginViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource(),
                    new AuthenTokenRepository(AuthTokenDataBaseClient
                            .getInstance(context).authenTokenDao())));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}