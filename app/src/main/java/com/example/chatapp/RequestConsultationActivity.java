package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RequestConsultationActivity extends AppCompatActivity {

    Button btn_request;
    DatabaseReference databaseRef;
    Spinner areaSpinner, clinicSpinner, specialitySpinner, doctorSpinner, appointmentSpinner;
    FirebaseAuth auth;
    DatabaseReference reference;


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
            String patientId  =databaseRef.push().getKey();
            WriteAppointment(patientId, clinic, doctor, appointmentTime);

           // need help to push to the database
        }
}
private void WriteAppointment(final String patientId, final String clinic, final String doctor, final String appointmentTime){
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        reference = FirebaseDatabase.getInstance().getReference();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(appointmentTime).exists())){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("patient ID", patientId);
                    hashMap.put("clinic", clinic);
                    hashMap.put("doctor", doctor);
                    hashMap.put("appointment time",appointmentTime);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RequestConsultationActivity.this, "Your appointment has been requested", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RequestConsultationActivity.this, PatientDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });


                } else {

                    Toast.makeText(RequestConsultationActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                }

                }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        };



    }

