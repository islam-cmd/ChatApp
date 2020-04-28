package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.nio.file.Path;

public class PatientDashboard extends AppCompatActivity {
    Button chatActivity;
    Button OnlineConsultation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patient_dashboard);

        chatActivity = findViewById(R.id.ChatActivity);
        OnlineConsultation = findViewById(R.id.OnlineConsultation);
        chatActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboard.this, MainActivity.class));
                finish();
            }
        });
        OnlineConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboard.this, OnlineConsultationActivity.class));

            }
        });
    }
}
