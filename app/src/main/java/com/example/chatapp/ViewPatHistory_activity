package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewPatHistory extends AppCompatActivity {

    Button returntoviewbtn;
    TextView patienthistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);
        returntoviewbtn = findViewById(R.id.go_back);
        patienthistory = findViewById(R.id.patient_history);
        //patienthistory.setText(profile.getMedicalHistory);
        returntoviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPatHistory.this, Viewing_Patients_activity.class);
                startActivity(intent);
                finish();


            }
        });
    }}
