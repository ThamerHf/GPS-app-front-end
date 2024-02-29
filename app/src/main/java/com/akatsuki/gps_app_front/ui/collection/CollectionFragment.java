package com.akatsuki.gps_app_front.ui.collection;

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
import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.databinding.FragmentCollectionBinding;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends Fragment {

    private FragmentCollectionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CollectionViewModel collectionViewModel =
                new ViewModelProvider(this).get(CollectionViewModel.class);

        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ListView list = binding.getRoot().findViewById(R.id.listCollections);

        List<String> collections = new ArrayList<>();

        for(int i = 0; i <= 16; i ++) {
            collections.add("toto" + i);
        }

        // Ajoutez autant d'éléments que nécessaire

        CollectionListItemAdapter adapter = new CollectionListItemAdapter(requireContext(), R.layout.fragment_collection_list_item,
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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}