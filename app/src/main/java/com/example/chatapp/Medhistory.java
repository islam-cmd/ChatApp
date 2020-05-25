package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatapp.Model.OnlineConsultation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Medhistory extends AppCompatActivity {
    TextView opinion, description, subject;
    ImageView imageView;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medhistory);
        opinion = findViewById(R.id.opinion);
        description = findViewById(R.id.description);
        subject = findViewById(R.id.subject);
        imageView = findViewById(R.id.imageView);
        reference = FirebaseDatabase.getInstance().getReference("OnlineConsultation");
        Intent i = getIntent();
        String uri = i.getStringExtra("uri");
        String id = i.getStringExtra("userid");
        String sub = i.getStringExtra("subject");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OnlineConsultation onlineConsultation = snapshot.getValue(OnlineConsultation.class);
                    if (onlineConsultation.getSubject().equals(sub)) {
                        description.setText(onlineConsultation.getDescription());
                        subject.setText(onlineConsultation.getSubject());
//                        username.setText(onlineConsultation.getPatientName());
//                        profile_image.setImageResource(R.mipmap.ic_launcher);
                        Picasso.with(getApplicationContext()).load(onlineConsultation.getURI()).fit().into(imageView);
//                        Glide.with(getApplicationContext()).load(onlineConsultation.getURI()).into(imageView);
//                        key = snapshot.getKey();
//                        Log.d("TAG", "onDataChange: +key" + key);
                        opinion.setText(onlineConsultation.getDocopinion());
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
