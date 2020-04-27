package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class PatientDashboard extends AppCompatActivity {
Button chatActivity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patient_dashboard);
    chatActivity = findViewById(R.id.ChatActivity);
    }
}
