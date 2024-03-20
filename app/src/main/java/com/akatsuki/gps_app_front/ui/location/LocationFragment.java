package com.akatsuki.gps_app_front.ui.location;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.akatsuki.gps_app_front.LoginRegisterActivity;
import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.data.model.entity.Location;
import com.akatsuki.gps_app_front.databinding.FragmentLocationBinding;
import com.akatsuki.gps_app_front.ui.home.HomeFragment;
import com.akatsuki.gps_app_front.ui.login.LoginFragment;

public class LocationFragment extends Fragment {

    private LocationViewModel mViewModel;
    private Location location;

    public LocationFragment() {
    }

    public LocationFragment(Location location) {
        this.location = location;
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentLocationBinding binding;
        binding = FragmentLocationBinding.inflate(inflater,container, false);
        View view =binding.getRoot();

        ImageButton deleteButton = view.findViewById(R.id.deleteButton);
        ImageButton shareButton = view.findViewById(R.id.shareButton);
        ImageButton downloadButton = view.findViewById(R.id.downloadButton);
        ImageButton editButton = view.findViewById(R.id.editButton);
        Button consultingButton = view.findViewById(R.id.consultingButton);
        EditText editText = view.findViewById(R.id.locationTitle);


        consultingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchToFragment(new HomeFragment());
            }
        });


        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @SuppressLint("ResourceType")
    private void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.layout.fragment_location, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}