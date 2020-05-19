package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.provider.Contacts;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Model.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UserInfoInputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button mStoreButton;
    EditText mFirst;
    EditText mLast;
    Button mPickDate;
    TextView mDate;
    int day,month,year;
    String date;
   EditText mPersonalInfo;


    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_input);
        databaseRef = FirebaseDatabase.getInstance().getReference("user_info");


        mPersonalInfo=(EditText) findViewById(R.id.PersonalInfo);
        mPickDate=(Button) findViewById(R.id.PickDate);
        mFirst = (EditText) findViewById(R.id.FirstName);
        mLast = (EditText) findViewById(R.id.LastName);
        mDate = (TextView) findViewById(R.id.Date);
        mStoreButton = (Button) findViewById(R.id.Save);


        mStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserInfo();

            }
        });
        mPickDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datepicker = new DatePickerDialog(UserInfoInputActivity.this,UserInfoInputActivity.this,year,month,day);
                datepicker.show();

            }
        });

    }
    private void addUserInfo() {
     final   String firstname = mFirst.getText().toString();
      final   String lastname = mLast.getText().toString();
      final   String DOB = mDate.getText().toString();
     final    String PersonalInfo= mPersonalInfo.getText().toString();
     final    String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(DOB)) {
            Toast.makeText(UserInfoInputActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
        } else {
            final Query query = FirebaseDatabase.getInstance().getReference("user_info").orderByChild("uid").equalTo(UID);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserInfo user = snapshot.getValue(UserInfo.class);
                            if (user.getUID().equals(UID)) {
                                String id =user.getId();
                                updateArtist(id, firstname,lastname,DOB,PersonalInfo,UID);
                                break;


                            }


                        }


                    }
                    else{


                        String id = databaseRef.push().getKey();

                        UserInfo user = new UserInfo(id, firstname, lastname, DOB, PersonalInfo, UID);
                        databaseRef.child(id).setValue(user);
                        Toast.makeText(UserInfoInputActivity.this, "User added", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }}
    private boolean updateArtist(String id, String firstname, String lastname ,String DOB,String personalInfo,String UID){


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user_info").child(id);
        UserInfo user = new UserInfo(id, firstname,lastname,DOB,personalInfo,UID);
        databaseReference.setValue(user);
        return true;}
    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        m=m+1;
        date = d + "/"+m+"/"+y;
        mDate.setText(date);

    }
}
