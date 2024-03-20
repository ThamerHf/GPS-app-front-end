package com.akatsuki.gps_app_front.ui.collection;

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
import com.akatsuki.gps_app_front.data.model.entity.Collection;
import com.akatsuki.gps_app_front.data.model.entity.Location;
import com.akatsuki.gps_app_front.data.repositories.repository.AuthenTokenRepository;
import com.akatsuki.gps_app_front.databinding.FragmentCollectionBinding;
import com.akatsuki.gps_app_front.databinding.FragmentLocationsBinding;
import com.akatsuki.gps_app_front.ui.locations.LocationsViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends Fragment {

    private FragmentCollectionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AuthenTokenRepository authenTokenRepository = new AuthenTokenRepository(AuthTokenDataBaseClient
                .getInstance(requireContext()).authenTokenDao());
        CollectionViewModel collectionViewModel = new CollectionViewModel(authenTokenRepository);

        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ListView list = binding.getRoot().findViewById(R.id.listCollections);
        Log.d("location", "test");

        List<Collection> collections =  new ArrayList<>();


        collectionViewModel.getCollections(new AppCallback<List<Collection>>() {
            @Override
            public void onCallBackSuccess(List<Collection> collectionsReceived) {
                Log.d("collection", "Debug callback succesful");
                collections.addAll(collectionsReceived);
                CollectionListItemAdapter adapter = new CollectionListItemAdapter(requireContext(),
                        R.layout.fragment_collection_list_item,
                        collections);
                list.setAdapter(adapter);

                EditText editText = view.findViewById(R.id.search_collection);
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
                Log.d("collection", "callback unseccessful");
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