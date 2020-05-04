package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatapp.Adapter.MessageAdapter;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.Doctor;
import com.example.chatapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocMessageActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference refrence;

    ImageButton email;
    DatabaseReference myrefrence;
    String body;
    String userto;
    String usermail;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_message);
//        setContentView(R.layout.activity_message);
//        Toolbar toolbar = findViewById(R.id.toolbar);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        email = findViewById(R.id.email);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        intent = getIntent();
        final String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(), userid, msg);
                } else {

                    Toast.makeText(DocMessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        // sending chat as email
        refrence = FirebaseDatabase.getInstance().getReference("Doctors").child(userid);
        myrefrence = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myrefrence.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        User user = dataSnapshot.getValue(User.class);
////                        userto = user.getUsername();
//                        usermail = user.getEmail();
//
////                        body = Emailchat(fuser.getUid(), userid, user.getImageURL());
//                    body = "ana sh3'al";
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                refrence.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Doctor doc = dataSnapshot.getValue(Doctor.class);
//                    userto  = doc.getUsername();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("message/rfc822");
//                i.putExtra(Intent.EXTRA_EMAIL, new String[]{usermail});
//                i.putExtra(Intent.EXTRA_SUBJECT, "Chat with "+ userto);
//                i.putExtra(Intent.EXTRA_TEXT, body);
//                try {
//                    startActivity(Intent.createChooser(i, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(DocMessageActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"i.tookhy@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DocMessageActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        refrence = FirebaseDatabase.getInstance().getReference("Doctors").child(userid);

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(DocMessageActivity.this).load(user.getImageURL()).into(profile_image);
                }
                readMessage(fuser.getUid(), userid, user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String sender, String reciever, String message) {


        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", reciever);
        hashMap.put("message", message);
        refrence.child("Chats").push().setValue(hashMap);


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DocMessageActivity.this, StartActivity.class));
                finish();
                return true;
        }
        return false;

    }


    private void readMessage(final String myid, final String userid, final String imageurl) {

        mchat = new ArrayList<>();
        refrence = FirebaseDatabase.getInstance().getReference("Chats");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReciever().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReciever().equals(userid) && chat.getSender().equals(myid)) {
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(DocMessageActivity.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String Emailchat(final String myid, final String userid, final String imageurl) {

        mchat = new ArrayList<>();
        refrence = FirebaseDatabase.getInstance().getReference("Chats");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReciever().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReciever().equals(userid) && chat.getSender().equals(myid)) {
                        mchat.add(chat);
                    }
//                    messageAdapter = new MessageAdapter(DocMessageActivity.this, mchat, imageurl);
//                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return mchat.toString();
    }
}
