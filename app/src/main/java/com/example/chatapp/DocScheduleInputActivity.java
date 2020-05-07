package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chatapp.Model.DoctorSchedule;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DocScheduleInputActivity extends AppCompatActivity {
    Button mStoreButton;
    EditText mFirst;
    EditText mLast;
    DatabaseReference databaseRef;
    Spinner mStart;
    Spinner mEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedule_input);
        databaseRef = FirebaseDatabase.getInstance().getReference("doctor schedule");

        mFirst = (EditText) findViewById(R.id.firstName);
        mLast = (EditText) findViewById(R.id.lastName);
        mStart =(Spinner) findViewById(R.id.start);
        mEnd =(Spinner) findViewById(R.id.end);
        mStoreButton = (Button) findViewById(R.id.save);

        mStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDoctorSchedule();

            }
        });
    }
    // adds doctor schedule
    private void addDoctorSchedule(){
        String firstname = mFirst.getText().toString();
        String lastname = mLast.getText().toString();
        String start = mStart.getSelectedItem().toString();
        String end = mEnd.getSelectedItem().toString();
        if (TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname)) {
            Toast.makeText(DocScheduleInputActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
        } else {
        String id =databaseRef.push().getKey();
        DoctorSchedule doctor = new DoctorSchedule(id,firstname,lastname,start,end);
        databaseRef.child(id).setValue(doctor);
            Toast.makeText(DocScheduleInputActivity.this, "schedule added", Toast.LENGTH_SHORT).show();
    }
}
}
