package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatapp.Model.DoctorSchedule;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorScheduleInputActivity extends AppCompatActivity {
    Button mStoreButton;
    EditText mFirst;
    EditText mLast;
    DatabaseReference databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedule_input);
        databaseRef = FirebaseDatabase.getInstance().getReference("doctor schedule");

        mFirst = (EditText) findViewById(R.id.firstName);
        mLast = (EditText) findViewById(R.id.lastName);

        mStoreButton = (Button) findViewById(R.id.save);

        mStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDoctorInfo();

            }
        });
    }
    private void addDoctorInfo(){
        String firstname = mFirst.getText().toString().trim();
        String lastname = mLast.getText().toString().trim();
        String id =databaseRef.push().getKey();
        DoctorSchedule doctor = new DoctorSchedule(id,firstname,lastname);
        databaseRef.child(id).setValue(doctor);
    }
}
