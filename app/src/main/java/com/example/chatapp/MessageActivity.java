package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.chatapp.Adapter.MessageAdapter;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.google.android.gms.common.internal.DialogRedirect;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
//import com.sinch.verification.SinchVerification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference refrence;
    ImageButton email;
    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;

    RecyclerView recyclerView;
    Intent intent;

    SinchClient sinchClient;
    Call call;
    ImageButton calling ;
    ImageButton Videocall;
    List<Chat> mchat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
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
        Log.d("mytag", "onCreate: "+ userid );
        fuser = FirebaseAuth.getInstance().getCurrentUser();


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(), userid, msg);
                } else {

                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        // sending an email of the chat
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"i.tookhy@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MessageActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refrence = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(profile_image);
                }
                readMessage(fuser.getUid(), userid, user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Videocall = findViewById(R.id.VideoCall);
//        Videocall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MessageActivity.this, FirstTimeActivity.class);
//                String strName = userid;
//                i.putExtra("userid", strName);
//                startActivity(i);
//            }
//        });
        calling = findViewById(R.id.call);
        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallUser(userid);
//                Intent intent = new Intent(getApplicationContext(), FirstTimeActivity.class);
//                intent.putExtra("callerId", fuser.getUid());
//                intent.putExtra("recipientId", userid);
//                startActivity(intent);
//                startActivity(new Intent(MessageActivity.this, FirstTimeActivity.class));
            }
        });
        // Sinch for the call
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(fuser.getUid())
                .applicationKey("2b7f52f2-8d67-423a-91ac-aedbac787a2e")
                .applicationSecret("shuCuCqMFkaAOiTTHNiuRQ==")
                .environmentHost("clientapi.sinch.com")
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();

        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());

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
                startActivity(new Intent(MessageActivity.this, StartActivity.class));
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
                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private class SinchCallListener implements CallListener {


        @Override
        public void onCallProgressing(com.sinch.android.rtc.calling.Call call) {
            Toast.makeText(MessageActivity.this, "Ringing...", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallEstablished(com.sinch.android.rtc.calling.Call call) {
            Toast.makeText(MessageActivity.this, "Call estblished", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCallEnded(com.sinch.android.rtc.calling.Call endedcall) {
            Toast.makeText(MessageActivity.this, "Call ended", Toast.LENGTH_SHORT).show();
            call = null;
            endedcall.hangup();
        }

        @Override
        public void onShouldSendPushNotification(com.sinch.android.rtc.calling.Call call, List<PushPair> list) {

        }
    }

    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, final com.sinch.android.rtc.calling.Call Incomingcall ) {
            AlertDialog alertDialog = new AlertDialog.Builder(MessageActivity.this).create();
            alertDialog.setTitle("Calling");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reject", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    call.hangup();
                }

            });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Pick", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                call = Incomingcall;
                call.answer();
                call.addCallListener(new SinchCallListener());
                Toast.makeText(getApplicationContext(), "Call has started", Toast.LENGTH_LONG).show();

                    AlertDialog alertDialog1 = new AlertDialog.Builder(MessageActivity.this).create();
                    alertDialog1.setTitle("Calling");
                    alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "Hang Up", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            call.hangup();
                        }
                    });
                    alertDialog1.show();
                }
            });
            alertDialog.show();
        }

    }


public void CallUser(String id){
        if(call ==null){
            call = sinchClient.getCallClient().callUser(id);

            call.addCallListener(new SinchCallListener());
        openCallerDialog(call);
        }
}

    private void openCallerDialog(final Call call) {
    AlertDialog alertDialog = new AlertDialog.Builder(MessageActivity.this).create();
    alertDialog.setTitle("ALERT");
    alertDialog.setMessage("CALLING");
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Hang up", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            call.hangup();
        }
    });
    alertDialog.show();
    }

}
