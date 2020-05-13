package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

public class UserInfoDisplay extends AppCompatActivity {
    TextView mFirstName;
    TextView mLastName;
    TextView mDOB;
    TextView mPersonalInFo;
    Button mInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_display);


        mFirstName=(TextView) findViewById(R.id.firstName);
        mLastName=(TextView) findViewById(R.id.LastName);
        mDOB=(TextView) findViewById(R.id.DOB);
        mPersonalInFo=(TextView) findViewById(R.id.PersonalInfo);
        mInput=(Button)findViewById(R.id.Input);

        mInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toInput();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query UserIdQuery= FirebaseDatabase.getInstance().getReference().child("user info").orderByChild("UID").equalTo(UID);


        UserIdQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String first=dataSnapshot.child("firstName").getValue().toString();
                String last= dataSnapshot.child("lastName").getValue().toString();
                String DOB= dataSnapshot.child("dateOfBirth").getValue().toString();
                String personalInfo = dataSnapshot.child("PersonalInfo").getValue().toString();

                mFirstName.setText(first);
                mLastName.setText(last);
                mDOB.setText(DOB);
                mPersonalInFo.setText(personalInfo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
    });
}
public void toInput(){
    Intent intent = new Intent(this,UserInfoInputActivity.class);
    startActivity(intent);

}
}