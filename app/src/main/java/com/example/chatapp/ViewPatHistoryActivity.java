package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewPatHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pat_history);

            Button returntoviewbtn;
            TextView patienthistory;
                returntoviewbtn = findViewById(R.id.go_back);
                patienthistory = findViewById(R.id.patient_history);
               //patienthistory.setText(profile.getMedicalHistory);
                returntoviewbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewPatHistoryActivity.this, Viewing_Patients_activity.class);
                        startActivity(intent);
                        finish();


                    }
                });
            }}

