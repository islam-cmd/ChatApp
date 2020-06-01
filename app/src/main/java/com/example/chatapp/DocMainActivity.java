package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Fragments.ChatsFragment;
import com.example.chatapp.Fragments.DoctorsFragment;
import com.example.chatapp.Fragments.UsersFragment;
import com.example.chatapp.Model.Doctor;
import com.example.chatapp.Model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocMainActivity extends AppCompatActivity {
    CircleImageView profile_image;
    FirebaseUser firebaseUser;
    DatabaseReference refrence;
    TextView username;
    Button updateLocation;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        refrence = FirebaseDatabase.getInstance().getReference("Doctors").child(firebaseUser.getUid()); //set reference to logged-in doctor

        //get Last known location of user----------------------------------------------------------------------
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //permissions check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            String provider = new String();
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                provider = LocationManager.NETWORK_PROVIDER;
            }
            else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                provider = LocationManager.GPS_PROVIDER;
            }

            locationManager.requestLocationUpdates(provider, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    refrence.child("longitude").setValue(longitude);
                    refrence.child("latitude").setValue(latitude);
                }

                @Override public void onStatusChanged(String s, int i, Bundle bundle) {}
                @Override public void onProviderEnabled(String s) {}
                @Override public void onProviderDisabled(String s) {

                }
            });
        }

        //-----------------------------------------------------------------------------------------------------

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Doctor doctor = dataSnapshot.getValue(Doctor.class);
                username.setText(doctor.getUsername());
                if (doctor.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                } else {
                    Glide.with(DocMainActivity.this).load(doctor.getImageURL()).into(profile_image);
                }
            }
//
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

//        viewPagerAdapter.addFragment(new ChatsFragment(), "Chats");

        viewPagerAdapter.addFragment(new UsersFragment(), "Users");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DocMainActivity.this, StartActivity.class));
                finish();
                return true;
        }
        return false;

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
            private ArrayList<Fragment> fragments;
            private ArrayList<String> titles;

            ViewPagerAdapter(FragmentManager fm) {
                super(fm);
                this.fragments = new ArrayList<>();
                this.titles = new ArrayList<>();
            }

        @NonNull
        @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

        @Override
            public int getCount() {
                return fragments.size();
            }

            public void addFragment(Fragment fragment, String title) {
                fragments.add(fragment);
                titles.add(title);
            }

        @Nullable
        @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        }
    }
//
