package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatapp.Model.Doctor;
import com.example.chatapp.Model.DoctorSchedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class DocProfile extends AppCompatActivity {

    TextView mFirstName;
    TextView mLastName;
    TextView mStart;
    TextView mEnd;

    Button mInput;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference refrence;
    CircleImageView profile_image;

    DatabaseReference DataRef;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_view_sched);

        profile_image= findViewById(R.id.profile_image) ;
        mFirstName = (TextView) findViewById(R.id.FirstName);
        mLastName = (TextView) findViewById(R.id.LastName);
        mStart = (TextView) findViewById(R.id.start);
        mEnd = (TextView) findViewById(R.id.end);
        mInput = (Button) findViewById(R.id.Input);

        UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DataRef= FirebaseDatabase.getInstance().getReference("DoctorSchedule");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        refrence = FirebaseDatabase.getInstance().getReference("Doctors").child(firebaseUser.getUid());
        mInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toInput();
            }
        });

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Doctor doctor = dataSnapshot.getValue(Doctor.class);
                username.setText(doctor.getUsername());
                if (doctor.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                } else {
                    Glide.with(DocProfile.this).load(doctor.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DoctorSchedule doctor = snapshot.getValue(DoctorSchedule.class);
                        if (doctor.getUID().equals(UID)) {
                            String first=doctor.getFirstName();
                            String last= doctor.getLastName();
                            String start= doctor.getStartTime();
                            String end = doctor.getEndTime();
                            mFirstName.setText(first);
                            mLastName.setText(last);
                            mStart.setText(start);
                            mEnd.setText(end);
                            break;


                        }


                    }


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void toInput(){
        Intent intent = new Intent(DocProfile.this,DocScheduleInputActivity.class);
        startActivity(intent);

    }
}
