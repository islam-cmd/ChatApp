package com.example.chatapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Adapter.ViewDoctorAdapter;
import com.example.chatapp.Model.Doctor;
import com.example.chatapp.R;
import com.example.chatapp.ViewDoctors;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private static final String TAG = "MapFragment";

    double latitude;
    double longitude;
    String marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String doctor_Id = ViewDoctorAdapter.doctor_Id;
        reference = FirebaseDatabase.getInstance().getReference("Doctors").child(doctor_Id);

        //create listener for latitude
        reference.child("latitude").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    latitude= (Double) dataSnapshot.getValue();
                    Log.d(TAG, "latitude: " + latitude );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //create listener for longitude
        reference.child("longitude").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    longitude= (Double) dataSnapshot.getValue();
                    Log.d(TAG, "longitude: " + longitude );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "successfully generated map");

        MapsInitializer.initialize((getContext()));

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng latLng = new LatLng(latitude, longitude);
        Log.d(TAG, "created new latlng object with: " + latitude + ", " + longitude );

        if (latitude > 0 && longitude > 0){
            marker = "No Location Provided";
        }
        else {
            marker = latitude + ", " + longitude;
        }
        googleMap.addMarker(new MarkerOptions().position(latLng).title(marker));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f));

    }
}
