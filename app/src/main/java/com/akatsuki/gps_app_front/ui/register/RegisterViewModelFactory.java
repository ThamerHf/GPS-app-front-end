package com.akatsuki.gps_app_front.ui.register;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.akatsuki.gps_app_front.data.LoginDataSource;
import com.akatsuki.gps_app_front.data.RegisterDataSource;
import com.akatsuki.gps_app_front.data.database.databaseClient.AuthTokenDataBaseClient;
import com.akatsuki.gps_app_front.data.repositories.repository.AuthenTokenRepository;
import com.akatsuki.gps_app_front.data.repositories.repository.LoginRepository;
import com.akatsuki.gps_app_front.data.repositories.repository.RegisterRepository;
import com.akatsuki.gps_app_front.ui.login.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class RegisterViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public RegisterViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(RegisterRepository.getInstance(
                    new RegisterDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}