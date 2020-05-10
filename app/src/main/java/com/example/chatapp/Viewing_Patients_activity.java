package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.chatapp.Model.User;
import com.example.chatapp.Model.View_Patient;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Viewing_Patients_activity extends AppCompatActivity {


    Button viewPatHistoryBtn;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Button dashboard_backbtn;
    ViewPager viewpat;
    Toolbar patToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        viewpat = findViewById(R.id.pat_view);
        //atToolbar = findViewById(R.id.patient_bar);
        TabLayout docTab = findViewById(R.id.tab_patients);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                View_Patient patient = dataSnapshot.getValue(View_Patient.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpat.setAdapter(viewPagerAdapter);
        docTab.setupWithViewPager(viewpat);

        dashboard_backbtn = findViewById(R.id.dashboard_back);
        dashboard_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Viewing_Patients_activity.this, Doctor_dashboard.class);
                startActivity(intent);
                finish();
            }
        });

    }

}

class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> patientFragments;
    private ArrayList<String> patientNames;


    ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.patientFragments = new ArrayList<>();
        this.patientNames = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return patientFragments.get(position);
    }

    @Override
    public int getCount() {
        return patientFragments.size();
    }

    public void addFragment(Fragment fragment, String title) {
        patientFragments.add(fragment);
        patientNames.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return patientNames.get(position);
    }
}







