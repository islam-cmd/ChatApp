package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Model.Doctor;
import com.example.chatapp.Model.User;
import com.example.chatapp.Model.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserInfoDisplay extends AppCompatActivity {
    TextView mFirstName;
    TextView mLastName;
    TextView mDOB;
    TextView mPersonalInFo;
    TextView mFName;
    CircleImageView profile_image;
    TextView mLName;
    TextView mDate;
    TextView mPInfo;
    Button mInput;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference refrence;

    boolean check;
    DatabaseReference DataRef;
    String UID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_display);
        username= findViewById(R.id.username);
        profile_image= findViewById(R.id.profile_image) ;
        mFirstName=(TextView) findViewById(R.id.FirstName);
        mLastName=(TextView) findViewById(R.id.LastName);
        mDOB=(TextView) findViewById(R.id.DOB);
        mPersonalInFo=(TextView) findViewById(R.id.PersonalInfo);
        mInput=(Button)findViewById(R.id.Input);
        mFName= (TextView)findViewById(R.id.firstName2);
        mLName=(TextView)findViewById(R.id.lastname2);
        mDate=(TextView)findViewById(R.id.date);
        mPInfo=(TextView)findViewById(R.id.pInfo);


        UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DataRef=FirebaseDatabase.getInstance().getReference("user_info");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        refrence = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                } else {
                    Glide.with(UserInfoDisplay.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        mInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toInput();
            }
        });
        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserInfo user = snapshot.getValue(UserInfo.class);

                            if (user.getUID().equals(UID)) {
                                String first = user.getFirstName();
                                String last = user.getLastName();
                                String DOB = user.getDateOfBirth();
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
    Intent intent = new Intent(UserInfoDisplay.this,UserInfoInputActivity.class);
    startActivity(intent);

}
}