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
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.chatapp.Model.Patient;
import com.example.chatapp.Model.User;
import com.example.chatapp.Model.ViewPatientList;
import com.example.chatapp.Model.Patient;
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
    ListView viewpat;
    Toolbar patToolbar;
    List<Patient> viewPatientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pat_history);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        viewpat = findViewById(R.id.pat_view);
        viewPatHistoryBtn = findViewById(R.id.history);
        //viewPatHistoryBtn.setEnabled(false);
        //atToolbar = findViewById(R.id.patient_bar);
        TabLayout docTab = findViewById(R.id.tab_patients);
        viewPatientList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
               {   viewPatientList.clear();
                   Patient patient = dataSnapshot.getValue(Patient.class);
                   viewPatientList.add(patient);
               }
                ViewPatientList adapter = new ViewPatientList(Viewing_Patients_activity.this, viewPatientList);
               viewpat.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        dashboard_backbtn = findViewById(R.id.dashboard_back);
        dashboard_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Viewing_Patients_activity.this, DoctorDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        viewPatHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Viewing_Patients_activity.this, View.class);
                startActivity(intent);
                finish();

            }


    });

}}









