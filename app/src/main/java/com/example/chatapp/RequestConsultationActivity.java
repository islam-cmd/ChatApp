package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RequestConsultationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner areaSpinner, clinicSpinner, specialitySpinner, doctorSpinner, appointmentSpinner;
    ImageButton back_btn;
    Button btn_request;
    String doctorid;
    public static String toReturn;

    FirebaseUser fuser;
    DatabaseReference refrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_consultation);

        areaSpinner = (Spinner) findViewById(R.id.areaSpinner);
        clinicSpinner = (Spinner) findViewById(R.id.clinicSpinner);
        specialitySpinner = (Spinner) findViewById(R.id.specialitySpinner);
        doctorSpinner = (Spinner) findViewById(R.id.doctorSpinner);

        areaSpinner.setOnItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Request Consultation");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        doctorsRef.addListenerForSingleValueEvent(eventListener);

        btn_request.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Area = areaSpinner.getSelectedItem().toString();
                String Clinic = clinicSpinner.getSelectedItem().toString();
                String Doctor = doctorSpinner.getSelectedItem().toString();
                String Appointment = appointmentSpinner.getSelectedItem().toString();
                doctorid = runEventListener(Doctor);

                if (!Clinic.equals("") || !Doctor.equals("")) {

                    sendAppointment(fuser.getUid(), Area, Clinic, doctorid, Appointment);
                } else {
                    Toast.makeText(RequestConsultationActivity.this, "Please fill out all of the text boxes", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        String Area = String.valueOf(areaSpinner.getSelectedItem());
        Toast.makeText(this, Area, Toast.LENGTH_SHORT).show();
        if(Area.contentEquals("Sydney CBD")) {
            List<String> list = new ArrayList<String>();
            list.add("Barangaroo Clinic, 200 Barangaroo Avenue");
            list.add("Chippendale Clinic, 29 Broardway Street");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            clinicSpinner.setAdapter(dataAdapter);
        }
        if(Area.contentEquals("Western Sydney")) {
            List<String> list = new ArrayList<String>();
            list.add("Lakemba Clinic, 27 Railway Parade");
            list.add("Cabramatta Clinic, 117 John Street");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            clinicSpinner.setAdapter(dataAdapter2);
        }

        String Clinic = String.valueOf(clinicSpinner.getSelectedItem());

        String Speciality = String.valueOf(specialitySpinner.getSelectedItem());
        Toast.makeText(this, Area, Toast.LENGTH_SHORT).show();
        if(Speciality.contentEquals("General") && Clinic.contentEquals("Lakemba Clinic, 27 Railway Parade")) {
            List<String> list = new ArrayList<String>();
            list.add("Dr Sam Smith");
            list.add("Dr Alexandra Jones");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            doctorSpinner.setAdapter(dataAdapter);
        }
        if(Speciality.contentEquals("Pediatrics") && Clinic.contentEquals("Lakemba Clinic, 27 Railway Parade")) {
            List<String> list = new ArrayList<String>();
            list.add("Dr John Johnston");
            list.add("Dr Adelaide White");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            doctorSpinner.setAdapter(dataAdapter2);
        }

        String Doctor = String.valueOf(doctorSpinner.getSelectedItem());


    }

    final List<String> appointmentList = new ArrayList<String>();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference doctorsRef = rootRef.child("Doctors");
    ValueEventListener eventListener = new ValueEventListener() {

        //need to set avaiable appointment times still
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                String timeslot = ds.child("time available").getValue(String.class);
                appointmentList.add(timeslot);
            }
            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, appointmentList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            appointmentSpinner.setAdapter(dataAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    private void sendAppointment(String sender, String area, String clinic, String doctor, String appointmemt) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Sender", sender);
        hashMap.put("Area", area);
        hashMap.put("Clinic", clinic);
        hashMap.put("Doctor", doctor);
        hashMap.put("Appointment", appointmemt);
        reference.child("Issues").push().setValue(hashMap);

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public String runEventListener(final String spDoctor)
    {
        FirebaseDatabase.getInstance().getReference().child("Doctors")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String name = snapshot.child("username").getValue(String.class);
                            if (name.compareTo(spDoctor) == 0)
                            {
                                toReturn = snapshot.child("id").getValue(String.class);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return toReturn;
    }


}