package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.chatapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UrgentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    MaterialEditText title, issue;
    TextView username;
    CircleImageView profile_image;
    Button send_btn, back_btn;
    FirebaseUser fuser;
    String doctorid;
    DatabaseReference currentUser;
    public static String toReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgentrequest);

        send_btn = findViewById(R.id.btn_sendissue);
        back_btn = findViewById(R.id.btn_back);
        title = findViewById(R.id.title);
        issue = findViewById(R.id.issue);
        username = findViewById(R.id.username);
        profile_image = findViewById(R.id.profile_image);

        final Spinner userSpinner = findViewById(R.id.userSpinner);
        userSpinner.setOnItemSelectedListener(this);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        send_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titlemsg = title.getText().toString();
                String issuemsg = issue.getText().toString();
                String selectedDoctor = userSpinner.getSelectedItem().toString();
                doctorid = runEventListener(selectedDoctor);
                if (!titlemsg.equals("") || !issuemsg.equals("")) {
                    sendIssue(fuser.getUid(), doctorid, titlemsg, issuemsg, "open");
                } else {
                    Toast.makeText(UrgentActivity.this, "Please fill out all of the text boxes", Toast.LENGTH_SHORT).show();
                }
                title.setText("");
                issue.setText("");
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UrgentActivity.this, UrgentActivityList.class);
                startActivity(intent);
                finish();
            }
        });
        currentUser = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                } else {
                    Glide.with(UrgentActivity.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final List<String> doctorsList = new ArrayList<String>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference doctorsRef = rootRef.child("Doctors");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("username").getValue(String.class);
                    doctorsList.add(name);
                }
                final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, doctorsList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                userSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        doctorsRef.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void sendIssue(String sender, String reciever, String titlemsg, String issuemsg, String status) {

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", reciever);
        hashMap.put("titlemsg", titlemsg);
        hashMap.put("issuemsg", issuemsg);
        hashMap.put("status", status);
        refrence.child("Issues").push().setValue(hashMap);

    }

    public String runEventListener(final String selectedDoctor)
    {
        FirebaseDatabase.getInstance().getReference().child("Doctors")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String name = snapshot.child("username").getValue(String.class);
                            if (name.compareTo(selectedDoctor) == 0)
                            {
                                toReturn = snapshot.child("id").getValue(String.class);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return toReturn;
    }
}
