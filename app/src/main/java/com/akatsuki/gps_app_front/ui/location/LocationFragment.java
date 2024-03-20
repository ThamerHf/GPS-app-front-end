package com.akatsuki.gps_app_front.ui.location;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.databinding.FragmentLocationBinding;

public class LocationFragment extends Fragment {

    private LocationViewModel mViewModel;
    private Location location;
    Button deleteButton;
    Button shareButton;
    Button downloadButton;
    Button editButton;
    public static LocationFragment newInstance() {
        return new LocationFragment();

    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentLocationBinding binding;
        binding = FragmentLocationBinding.inflate(inflater,container, false);
        View view =binding.getRoot();

        deleteButton = view.findViewById(R.id.deleteButton);
        shareButton = view.findViewById(R.id.shareButton);
        downloadButton = view.findViewById(R.id.downloadButton);
        editButton = view.findViewById(R.id.editButton);

        return inflater.inflate(R.layout.fragment_location, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        // TODO: Use the ViewModel
    }

}