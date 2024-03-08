package com.akatsuki.gps_app_front.ui.location;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.akatsuki.gps_app_front.databinding.FragmentCollectionBinding;
import com.akatsuki.gps_app_front.databinding.FragmentLocationsBinding;
import com.akatsuki.gps_app_front.ui.collection.CollectionViewModel;

import java.util.ArrayList;
import java.util.List;


public class LocationsFragment extends Fragment {

    private FragmentLocationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocationsViewModel locationsViewModel =
                new ViewModelProvider(this).get(LocationsViewModel.class);

        binding = FragmentLocationsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ListView list = binding.getRoot().findViewById(R.id.listLocations);

        List<String> Locations = new ArrayList<>();

        for(int i = 0; i <= 16; i ++) {
            Locations.add("titi" + i);
        }

        // Ajoutez autant d'éléments que nécessaire

        LocationListItemAdapter adapter = new LocationListItemAdapter(requireContext(), R.layout.location_list_item,
                Locations);
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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}