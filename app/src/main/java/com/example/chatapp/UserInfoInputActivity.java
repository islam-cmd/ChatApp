package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Model.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UserInfoInputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button mStoreButton;
    EditText mFirst;
    EditText mLast;
    Button mPickDate;
    TextView mDate;
    int day,month,year;
    String date;
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_input);
        databaseRef = FirebaseDatabase.getInstance().getReference("user info");



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
        String firstname = mFirst.getText().toString();
        String lastname = mLast.getText().toString();
        String DOB = mDate.getText().toString();
        if (TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(DOB)) {
            Toast.makeText(UserInfoInputActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
        } else {
     //    String id = FirebaseAuth.getInstance().getCurrentUser().getUid().trim();
            String id = databaseRef.push().getKey();

            UserInfo user = new UserInfo(id, firstname, lastname, DOB);
            databaseRef.child(id).setValue(user);
            Toast.makeText(UserInfoInputActivity.this, "User added", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        m=m+1;
        date = d + "/"+m+"/"+y;
        mDate.setText(date);

    }
}
