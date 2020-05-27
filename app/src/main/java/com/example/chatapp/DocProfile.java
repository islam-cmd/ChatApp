package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatapp.Model.DoctorProfile;
import com.example.chatapp.Model.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DocProfile extends AppCompatActivity {
    TextView mFirstName;
    TextView mLastName;

    CircleImageView profile_image;

    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference refrence;
    Button mInput;
    TextView mEnd;
    TextView mStart;


    DatabaseReference DataRef;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile);
        username = findViewById(R.id.username);
        profile_image = findViewById(R.id.profile_image);
        mFirstName = (TextView) findViewById(R.id.firstName);
        mLastName = (TextView) findViewById(R.id.lastName);
        mInput = findViewById(R.id.input);
        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DataRef = FirebaseDatabase.getInstance().getReference("DoctorProfile");
        mStart = findViewById(R.id.end);
        mEnd = findViewById(R.id.start);
        mInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toInput();
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        refrence = FirebaseDatabase.getInstance().getReference("Doctors").child(firebaseUser.getUid());
        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DoctorProfile user = snapshot.getValue(DoctorProfile.class);
                        if (user.getUID().equals(UID)) {
                            String first = user.getFirstName();
                            String last = user.getLastName();
                            String start = user.getStartTime();
                            String end = user.getEndTime();
                            mFirstName.setText(first);
                            mLastName.setText(last);
                            mEnd.setText(end);
                            mStart.setText(start);
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

    public void toInput() {
        Intent intent = new Intent(DocProfile.this, DocScheduleInputActivity.class);
        startActivity(intent);

    }
}