package com.akatsuki.gps_app_front.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.data.model.entity.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private Marker selectedMarker;
    private SearchView searchView;
    private GoogleMap googleMap;
    private MapView mapView;

    private Location location;

    public HomeFragment() {
    }

    public HomeFragment(Location location) {
        this.location = location;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    enableMyLocation();
                } else {
                    Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
                }
            });


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        searchView = (SearchView) rootView.findViewById(R.id.mapSearch);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location = searchView.getQuery().toString();

                List<Address> addressList = null;

                if (location == null) {
                    Toast.makeText(requireContext(), "Location Not Found", Toast.LENGTH_SHORT).show();

                } else {

                    Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                        assert addressList != null;
                        if(addressList.size() > 0) {
                            Address address = addressList.get(0);

                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            googleMap.addMarker(new MarkerOptions().position(latLng).title(location));

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapView.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        // Request location permission
        requestLocationPermission();


        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);

        googleMap.getUiSettings().setZoomGesturesEnabled(false);

        googleMap.setOnMapClickListener(this);
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request location permission
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            // enable location if permission already granted
            enableMyLocation();
        }
    }

    private void enableMyLocation() {
        if (googleMap != null && ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Enable the My Location layer if permissions are granted.
            googleMap.setMyLocationEnabled(true);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // Remove previous marker if exists
        if (selectedMarker != null) {
            selectedMarker.remove();
        }

        // Add a marker at the tapped location
        selectedMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));

        // Move camera to the tapped location
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        // Perform additional actions as needed
        Toast.makeText(requireContext(), "Location selected", Toast.LENGTH_SHORT).show();

    }



}