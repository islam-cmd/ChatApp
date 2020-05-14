package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class UrgentActivityView extends AppCompatActivity {
    TextView username, title, desc, reply;
    CircleImageView profile_image;
    Button back_btn;
    FirebaseUser fuser;
    DatabaseReference currentUser;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgentrequestview);

        back_btn = findViewById(R.id.btn_back);
        title = findViewById(R.id.titletext);
        desc = findViewById(R.id.desctext);
        reply = findViewById(R.id.replytext);
        username = findViewById(R.id.username);
        profile_image = findViewById(R.id.profile_image);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        title.setText(intent.getStringExtra("Title"));
        desc.setText(intent.getStringExtra("Issue"));
        reply.setText(intent.getStringExtra("Reply"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        currentUser = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                } else {
                    Glide.with(UrgentActivityView.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UrgentActivityView.this, UrgentActivityList.class);
                startActivity(i);
                finish();
            }
        });
    }
}
