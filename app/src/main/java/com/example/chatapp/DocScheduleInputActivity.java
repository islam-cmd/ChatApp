package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chatapp.Model.Doctor;
import com.example.chatapp.Model.DoctorSchedule;
import com.example.chatapp.Model.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

        mFirst = (EditText) findViewById(R.id.FirstName);
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
      final  String firstname = mFirst.getText().toString();
      final  String lastname = mLast.getText().toString();
      final  String start = mStart.getSelectedItem().toString();
      final  String end = mEnd.getSelectedItem().toString();
        final    String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname)) {
            Toast.makeText(DocScheduleInputActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
        } else {
            final Query query = FirebaseDatabase.getInstance().getReference("DoctorSchedule").orderByChild("uid").equalTo(UID);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 0) {
                   for( DataSnapshot snapshot: dataSnapshot.getChildren()){
                   DoctorSchedule doctor = snapshot.getValue(DoctorSchedule.class);
                   if (doctor.getUID().equals(UID)){
                       String id= doctor.getUID();

                       updateSchedule(id, firstname,lastname, start, end,UID);

                       break;

                   }
                    }

                    }
                    else{
                        String id =databaseRef.push().getKey();

                        DoctorSchedule doctor = new DoctorSchedule(id,firstname,lastname,start,end,UID);
                        databaseRef.child(id).setValue(doctor);
                        Toast.makeText(DocScheduleInputActivity.this, "schedule added", Toast.LENGTH_SHORT).show();



                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




}
    }
    private boolean updateSchedule(String id, String firstname, String lastname ,String start, String end,String UID){


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("DoctorSchedule").child(id);
        DoctorSchedule doctor = new DoctorSchedule(id, firstname,lastname,start, end,UID);
        databaseReference.setValue(doctor);
        return true;}
}
