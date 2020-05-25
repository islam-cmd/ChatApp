package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.chatapp.Model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doc_Accept_Request_Activity extends AppCompatActivity {
    Button accept_btn;
    Button deny_btn;
    FirebaseUser fuser;
    DatabaseReference currentUser;
    public static String toReturn;
    TextView username;
    CircleImageView profile_image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_accept_request);
        deny_btn = findViewById(R.id.btn_denyRequest);
        accept_btn = findViewById(R.id.btn_acceptRequest);

        deny_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doc_Accept_Request_Activity.this, DoctorDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeAppointment();
                Intent intent = new Intent(Doc_Accept_Request_Activity.this, DoctorDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        currentUser = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                } else {
                    Glide.with(Doc_Accept_Request_Activity.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void writeAppointment() {

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("patientId", "43yVs4HDd6NTqE7JoOCQtyIrKWb2");
        hashMap.put("clinic", "Chippendale Clinic, 29 Broardway Street");
        hashMap.put("doctorId", "81DpTfchqhgAJQj8waKwT1uSudC2");
        hashMap.put("date", "23-06-2020");
        hashMap.put("appointmentTime", "1:00PM - 2:00PM");
        hashMap.put("status", 1);
        refrence.child("RequestConsultation").push().setValue(hashMap);

    }

}


