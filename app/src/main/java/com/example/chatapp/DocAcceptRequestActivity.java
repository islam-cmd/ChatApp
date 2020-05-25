package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Adapter.AppointmentAdapter;
import com.example.chatapp.Model.Appointment;
import com.example.chatapp.Model.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DocAcceptRequestActivity extends AppCompatActivity {
    private Button btn_denyRequest,btn_acceptRequest;
    List<Appointment> mappointment;
    DatabaseReference reference;
    String doctorName, doctorId;
    AppointmentAdapter appointmentAdapter;
    RecyclerView recyclerView;
    Intent intent;


    //make a line that doctorId from the authorisation get current user id, once u get id go over all doctors and get doctor name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_accept_request);
        btn_denyRequest = findViewById(R.id.btn_denyRequest);
        btn_acceptRequest = findViewById(R.id.btn_acceptRequest);

        btn_denyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocAcceptRequestActivity.this, DocScheduleDisplayActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_denyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocAcceptRequestActivity.this, DocScheduleDisplayActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void viewAppoinments() {

        mappointment = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("BookAppointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mappointment.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    if (appointment.getDoctor().equals("Annabel") && appointment.getStatus() == 0) {
                        mappointment.add(appointment);
                    }
                    appointmentAdapter = new AppointmentAdapter (DocAcceptRequestActivity.this, mappointment);
                    recyclerView.setAdapter(appointmentAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}

