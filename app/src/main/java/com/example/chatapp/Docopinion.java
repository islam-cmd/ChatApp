package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatapp.Model.OnlineConsultation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Docopinion extends AppCompatActivity {
    ImageView imageView;
    TextView subject, description, username;
    Button send;
    CircleImageView profile_image;
    EditText opinion;
    DatabaseReference refrence;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docopinion);
        imageView = findViewById(R.id.imageView);
        subject = findViewById(R.id.subject);
        description = findViewById(R.id.description);
        username = findViewById(R.id.username);
        send = findViewById(R.id.send);
        profile_image = findViewById(R.id.profile_image);
        opinion = findViewById(R.id.opinion);
        refrence = FirebaseDatabase.getInstance().getReference("OnlineConsultation");
        Intent i = getIntent();
        String id = i.getStringExtra("userid");
        String sub = i.getStringExtra("subject");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OnlineConsultation onlineConsultation = snapshot.getValue(OnlineConsultation.class);
                    if (onlineConsultation.getSubject().equals(sub)) {
                        description.setText(onlineConsultation.getDescription());
                        subject.setText(onlineConsultation.getSubject());
                        username.setText(onlineConsultation.getPatientName());
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                        Picasso.with(getApplicationContext()).load(onlineConsultation.getURI()).fit().into(imageView);
//                        Glide.with(getApplicationContext()).load(onlineConsultation.getURI()).into(imageView);
                        key = snapshot.getKey();
                        Log.d("TAG", "onDataChange: +key" + key);
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = opinion.getText().toString();
                if (text.length() > 0) {

                    setvalue(text, key);
                    send.setText("Sent!");
                } else {
                    Toast.makeText(Docopinion.this, "You can not send an empty text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setvalue(String s, String key) {
        refrence.child(key).child("docopinion").setValue(s);
    }
}
