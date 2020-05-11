package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.chatapp.Adapter.ViewDoctorAdapter;
import com.example.chatapp.Model.Doctor;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDoctorInfoActivity extends AppCompatActivity {

    DatabaseReference reference;
    private static final String TAG = "ViewDoctorInfo";
    private Doctor doctor;

    TextView doc_name;
    TextView doc_email;
    TextView doc_spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_info);

        String doctor_Id = ViewDoctorAdapter.doctor_Id;
        reference = FirebaseDatabase.getInstance().getReference("Doctors").child(doctor_Id);
        doctor = new Doctor();

        doc_name = (TextView) findViewById(R.id.doc_name);
        doc_email = (TextView) findViewById(R.id.doc_email);
        doc_spec = (TextView) findViewById(R.id.doc_spec);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    dataSnapshot.getValue();
                    Log.d(TAG, "fetched data snapshot for username: " + dataSnapshot.child("username").getValue());
                    doctor.setUsername(dataSnapshot.child("username").getValue().toString());
                    doctor.setEmail(dataSnapshot.child("email").getValue().toString());

                    if (dataSnapshot.child("specialisation").exists()){
                        doctor.setSpecialisation(dataSnapshot.child("specialisation").getValue().toString());
                    }

                    doc_name.setText(doctor.getUsername());
                    doc_email.setText(doctor.getEmail());
                    doc_spec.setText(doctor.getSpecialisation());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
}
