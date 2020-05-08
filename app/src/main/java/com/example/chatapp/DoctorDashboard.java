package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoctorDashboard extends AppCompatActivity {

    Button viewMes_btn;
    Button logout_btn;
    Button schedule;
    Button view_doctors_btn;
    Button view_appointment_btn;
    Button contactSupport_btn;
    Button emergency_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        viewMes_btn = findViewById(R.id.ChatActivity);
        logout_btn = findViewById(R.id.log_out);
        view_doctors_btn = findViewById(R.id.view_doc);
        emergency_btn = findViewById(R.id.Urgent_Case);

        viewMes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorDashboard.this, DocMainActivity.class);
                startActivity(intent);
                finish(); }
        });
        schedule = findViewById(R.id.Schedule_appointment);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(PatientDashboard.this, RequestConsultationActivity.class);
                //startActivity(intent);
//                finish();
            }
        });
        logout_btn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDashboard.this, DocLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }));
    }
}
