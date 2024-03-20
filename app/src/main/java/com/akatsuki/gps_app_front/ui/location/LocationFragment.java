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
import android.widget.TextView;

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

        FragmentLocationBinding binding = FragmentLocationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ImageButton deleteButton = binding.deleteButton;
        ImageButton shareButton = binding.shareButton;
        ImageButton downloadButton = binding.downloadButton;
        ImageButton editButton = binding.editButton;
        Button openInMap = binding.consultingButton;
        Button consultingButton = binding.consultingButton;
        EditText locationTitle = binding.locationTitle;
        EditText locationTags = binding.locationTags;
        EditText locationDescription = binding.locationDescription;

        // Assurez-vous que la location n'est pas nulle
        if (location != null) {
            // Fixez le texte des EditText avec les valeurs de la location
            locationTitle.setText(location.getTitle());
            locationTags.setText(location.getDescription());

            // Si la liste des tags n'est pas nulle, la concaténer et la fixer au EditText
            if (location.getTags() != null) {
                StringBuilder tagsBuilder = new StringBuilder();
                for (String tag : location.getTags()) {
                    tagsBuilder.append(tag).append(", ");
                }
                locationDescription.setText(tagsBuilder.toString());
            }
        }

        consultingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchToFragment(new HomeFragment(location));
            }
        });

        return view;
    }

    @SuppressLint("ResourceType")
    private void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}