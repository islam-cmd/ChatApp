package com.example.chatapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestConsultationActivity extends AppCompatActivity {

    Button btn_request;
    DatabaseReference databaseRef;
    Spinner areaSpinner, clinicSpinner, specialitySpinner, doctorSpinner, appointmentSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_consultation);
        databaseRef = FirebaseDatabase.getInstance().getReference("request appointment");

        areaSpinner =(Spinner) findViewById(R.id.areaSpinner);
        clinicSpinner =(Spinner) findViewById(R.id.clinicSpinner);
        specialitySpinner =(Spinner) findViewById(R.id.specialitySpinner);
        doctorSpinner =(Spinner) findViewById(R.id.doctorSpinner);
        appointmentSpinner =(Spinner) findViewById(R.id.appointmentSpinner);
        btn_request = (Button) findViewById(R.id.btn_request);

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addAcceptConsultation();

            }
        });
    }

    private void addAcceptConsultation(){
        String clinic = clinicSpinner.getSelectedItem().toString();
        String doctor = doctorSpinner.getSelectedItem().toString();
        String appointmentTime = appointmentSpinner.getSelectedItem().toString();
        if (TextUtils.isEmpty(clinic) || TextUtils.isEmpty(doctor)  || TextUtils.isEmpty(appointmentTime)) {
            Toast.makeText(RequestConsultationActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
        } else {
            String id =databaseRef.push().getKey();
           // need help to push to the database
        }
}}
