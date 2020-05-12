package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class DocReadUrgentRequest extends AppCompatActivity {

    MaterialEditText reply;
    TextView username, title, desc, nametext;
    CircleImageView profile_image;
    Button send_btn, back_btn;
    FirebaseUser fuser;
    DatabaseReference currentUser;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_read_request);

        send_btn = findViewById(R.id.btn_sendreply);
        back_btn = findViewById(R.id.btn_back);
        title = findViewById(R.id.titletext);
        desc = findViewById(R.id.desctext);
        reply = findViewById(R.id.reply);
        username = findViewById(R.id.username);
        nametext = findViewById(R.id.nametext);
        profile_image = findViewById(R.id.profile_image);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        title.setText(intent.getStringExtra("Title"));
        desc.setText(intent.getStringExtra("Issue"));
        nametext.setText(intent.getStringExtra("SenderID"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replytext = reply.getText().toString();
                if (!replytext.equals("")) {
                    sendReply(fuser.getUid(), intent.getStringExtra("SenderID"), replytext);
                    deleteIssue(intent.getStringExtra("IssueID"));
                } else {
                    Toast.makeText(DocReadUrgentRequest.this, "Please fill out all of the text boxes", Toast.LENGTH_SHORT).show();
                }
                reply.setText("");
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DocReadUrgentRequest.this, DocReceiveUrgentActivity.class);
                startActivity(i);
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
                    Glide.with(DocReadUrgentRequest.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendReply(String sender, String reciever, String replymsg) {

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", reciever);
        hashMap.put("replymsg", replymsg);
        refrence.child("ResolvedIssues").push().setValue(hashMap);

    }

    public void deleteIssue(final String issueID2)
    {

        final DatabaseReference issuesRef = FirebaseDatabase.getInstance().getReference().child("Issues");

        issuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String issueID = snapshot.getKey();
                    Log.v("snapshotkey", issueID);
                    Log.v("inputkey", issueID2);
                    Log.v("comparison", String.valueOf(issueID.compareTo(issueID2)));
                    if (issueID.compareTo(issueID2) == 0)
                    {
                        HashMap<String, Object> issueUpdates = new HashMap<>();
                        issueUpdates.put(issueID + "/status", "closed");
                        issuesRef.updateChildren(issueUpdates);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
