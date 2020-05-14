package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatapp.Model.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;


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
        final String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
    Query UserIdQuery= FirebaseDatabase.getInstance().getReference().child("user_info").orderByChild("uid").equalTo(UID);

     UserIdQuery.addValueEventListener(new ValueEventListener() {
            @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if(dataSnapshot.getChildrenCount() > 0){
                  for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     UserInfo user = snapshot.getValue(UserInfo.class);
                     if (user.getUID().equals(UID)) {
                         String first=user.getFirstName();
                          String last= user.getLastName();
                          String DOB= user.getDateOfBirth();
                         String personalInfo = user.getPersonalInfo();
                         mFirstName.setText(first);
                         mLastName.setText(last);
                          mDOB.setText(DOB);
                          mPersonalInFo.setText(personalInfo);
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
    Intent intent = new Intent(this,UserInfoInputActivity.class);
    startActivity(intent);

}
}