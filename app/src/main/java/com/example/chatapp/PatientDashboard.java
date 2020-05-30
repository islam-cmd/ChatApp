package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.nio.file.Path;


public class PatientDashboard extends AppCompatActivity {

    Button viewMes_btn;
    Button logout_btn;
    Button schedule;
    Button view_doctors_btn;
    Button view_appointment_btn;
    Button contactSupport_btn;
    Button emergency_btn;
    Button view_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        viewMes_btn = findViewById(R.id.ChatActivity);
        logout_btn = findViewById(R.id.log_out);
        view_doctors_btn = (Button) findViewById(R.id.view_doc);
        schedule = findViewById(R.id.Schedule_appointment);
        view_appointment_btn = findViewById(R.id.view_appointment);
        view_profile=findViewById(R.id.view_profile);
        view_doctors_btn = findViewById(R.id.view_doc);
        emergency_btn = findViewById(R.id.Urgent_Case);
        //view_doctors_btn.setOnClickListener(new View.OnClickListener(){
            //@Override
          //  public void onClick(View v) {
        //        Intent intent = new Intent(PatientDashboard.this, ViewDoctors.class);
              //  startActivity(intent);
            //}
        //});

        //view_appointment_btn.setOnClickListener(view -> {
          //  Intent intent = new Intent(PatientDashboard.this, ViewingAppointmentActivity.class);
            //startActivity(intent);
            //finish();
        //});

        viewMes_btn.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDashboard.this, MainActivity.class);
            startActivity(intent);
            finish(); });

        //logout_btn.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
              //  Intent intent = new Intent(PatientDashboard.this, LoginActivity.class);
                //startActivity(intent);
                //finish();
            //}
        //});

        schedule = findViewById(R.id.Schedule_appointment);
      //  schedule.setOnClickListener(view -> {
        //   Intent intent = new Intent(PatientDashboard.this, RequestConsultationActivity.class);
          //  startActivity(intent);
            //finish();
        //});
        emergency_btn.setOnClickListener(view -> {
             Intent intent = new Intent(PatientDashboard.this, UrgentActivity.class);
            startActivity(intent);
            finish();


       // view_profile.setOnClickListener(v -> {
     //       Intent intent1 = new Intent(PatientDashboard.this, UserInfoDisplay.class);
         //   startActivity(intent1);
           // finish(); });
    //});}
});
    }}
