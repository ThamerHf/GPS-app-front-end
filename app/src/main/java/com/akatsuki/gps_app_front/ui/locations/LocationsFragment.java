package com.akatsuki.gps_app_front.ui.locations;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.akatsuki.gps_app_front.CollectionListItemAdapter;
import com.akatsuki.gps_app_front.LocationListItemAdapter;
import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.database.databaseClient.AuthTokenDataBaseClient;
import com.akatsuki.gps_app_front.data.model.entity.Location;
import com.akatsuki.gps_app_front.data.repositories.dao.AuthenTokenDao;
import com.akatsuki.gps_app_front.data.repositories.repository.AuthenTokenRepository;
import com.akatsuki.gps_app_front.databinding.FragmentCollectionBinding;
import com.akatsuki.gps_app_front.databinding.FragmentLocationsBinding;
import com.akatsuki.gps_app_front.ui.collection.CollectionViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class LocationsFragment extends Fragment {

    private FragmentLocationsBinding binding;


    public LocationsFragment() {
    }

    public LocationsFragment(Location location) {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AuthenTokenRepository authenTokenRepository = new AuthenTokenRepository(AuthTokenDataBaseClient
                .getInstance(requireContext()).authenTokenDao());
        LocationsViewModel locationsViewModel = new LocationsViewModel(authenTokenRepository);

        binding = FragmentLocationsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ListView list = binding.getRoot().findViewById(R.id.listLocations);
        Log.d("location", "test");

        List<Location> locations =  new ArrayList<>();


        locationsViewModel.getLocations(new AppCallback<List<Location>>() {
            @Override
            public void onCallBackSuccess(List<Location> locationsreceived) {
                Log.d("location", "Debug callback succesful");
                locations.addAll(locationsreceived);
                LocationListItemAdapter adapter = new LocationListItemAdapter(requireContext(),
                        R.layout.location_list_item,
                        locations);
                list.setAdapter(adapter);

                EditText editText = view.findViewById(R.id.searchLocation);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            @Override
            public void onCallBackError(IOException exception) {
                Log.d("location", "callback unseccessful");
            }
        });
        // Ajoutez autant d'éléments que nécessaire

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
