package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatapp.Model.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoInputActivity extends AppCompatActivity {
    Button mStoreButton;
    EditText mFirst;
    EditText mLast;

    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_input);
        databaseRef = FirebaseDatabase.getInstance().getReference("users");




        mFirst = (EditText) findViewById(R.id.FirstName);
        mLast = (EditText) findViewById(R.id.LastName);

        mStoreButton = (Button) findViewById(R.id.Save);


        mStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserInfo();

            }
        });

    }
    private void addUserInfo(){
        String firstname = mFirst.getText().toString().trim();
        String lastname = mLast.getText().toString().trim();
        String id =databaseRef.push().getKey();
        UserInfo user = new UserInfo(id,firstname,lastname);
        databaseRef.child(id).setValue(user);

    }
}
