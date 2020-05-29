package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class DocReceiveUrgentActivity extends AppCompatActivity {

    TextView username;
    CircleImageView profile_image;
    Button back_btn;
    ListView listView;
    FirebaseUser fuser;
    ArrayList<String[]> issuesList;
    ArrayList<String> issuesNamesList;
    DatabaseReference currentUser, rootRef, issuesRef;
    ValueEventListener eventListener;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_urgent);

        back_btn = findViewById(R.id.btn_back);
        username = findViewById(R.id.username);
        profile_image = findViewById(R.id.profile_image);
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        issuesList = new ArrayList<String[]>();
        issuesNamesList  = new ArrayList<String>();
        rootRef = FirebaseDatabase.getInstance().getReference();
        issuesRef = rootRef.child("Issues");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String issueID = ds.getKey();
                    String titlemsg = ds.child("titlemsg").getValue(String.class);
                    String issuemsg = ds.child("issuemsg").getValue(String.class);
                    String sender = ds.child("sender").getValue(String.class);
                    String reciever = ds.child("reciever").getValue(String.class);
                    String status = ds.child("status").getValue(String.class);
                    if (status.compareTo("open") == 0)
                        issuesNamesList.add(titlemsg);
                        issuesList.add(new String[]{titlemsg, issuemsg, sender, reciever, issueID});
                }
                adapter = new ArrayAdapter<String>(DocReceiveUrgentActivity.this ,android.R.layout.simple_list_item_1, issuesNamesList);
                listView = findViewById(R.id.urgentList);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String issueName = issuesList.get(position)[0];
                        String issueDesc = issuesList.get(position)[1];
                        String issueSender = issuesList.get(position)[2];
                        String issueReceiver = issuesList.get(position)[3];
                        String issueID = issuesList.get(position)[4];
                        Toast.makeText(getApplicationContext(),  "Click ListItem Number " + issueName, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DocReceiveUrgentActivity.this, DocReadUrgentRequest.class);
                        intent.putExtra("Title", issueName);
                        intent.putExtra("Issue", issueDesc);
                        intent.putExtra("SenderID", issueSender);
                        intent.putExtra("ReceiverID", issueReceiver);
                        intent.putExtra("IssueID", issueID);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        issuesRef.addListenerForSingleValueEvent(eventListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocReceiveUrgentActivity.this, PatientDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        currentUser = FirebaseDatabase.getInstance().getReference("Doctors").child(fuser.getUid());

        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                } else {
                    Glide.with(DocReceiveUrgentActivity.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
