package com.akatsuki.gps_app_front.ui.location;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.akatsuki.gps_app_front.data.repositories.repository.AuthenTokenRepository;
import com.akatsuki.gps_app_front.ui.locations.LocationsViewModel;

public class LocationsViewModelFactory implements ViewModelProvider.Factory {

    private final AuthenTokenRepository authenTokenRepository;

    public LocationsViewModelFactory(AuthenTokenRepository authenTokenRepository) {
        this.authenTokenRepository = authenTokenRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LocationsViewModel.class)) {
            return (T) new LocationsViewModel(authenTokenRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
