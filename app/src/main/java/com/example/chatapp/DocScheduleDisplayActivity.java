package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.chatapp.Model.DoctorProfile;

import com.example.chatapp.Model.DoctorScheduleList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DocScheduleDisplayActivity extends AppCompatActivity {
    DatabaseReference dataRef ;
    ListView listViewDoctor;
    List<DoctorProfile> docList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_schedule_display);
        listViewDoctor =(ListView)findViewById(R.id.doctorList);
        docList = new ArrayList<>();

        dataRef= FirebaseDatabase.getInstance().getReference("DoctorProfile");
    }
    @Override
    protected void onStart(){
        super.onStart();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                docList.clear();

                for (DataSnapshot doctorSnapShot : dataSnapshot.getChildren()) {
                    DoctorProfile doctor = doctorSnapShot.getValue(DoctorProfile.class);
                    docList.add(doctor);
                }
                DoctorScheduleList adapter = new DoctorScheduleList(DocScheduleDisplayActivity.this, docList);
                listViewDoctor.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
