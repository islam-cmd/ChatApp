package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.chatapp.Adapter.MessageAdapter;
import com.example.chatapp.Adapter.Onlineconadapter;
import com.example.chatapp.Adapter.UserAdapter;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.OnlineConsultation;
import com.example.chatapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OnlineConsultationdoc extends AppCompatActivity {
    RecyclerView mRecyclerView;
    Onlineconadapter onlineconadapter;
    List<OnlineConsultation> back;
    DatabaseReference refrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_consultationdoc);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        getmyList();

    }

    private void getmyList() {
        back = new ArrayList<>();
        refrence = FirebaseDatabase.getInstance().getReference("OnlineConsultation");
        refrence.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               back.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OnlineConsultation onlineConsultation = snapshot.getValue(OnlineConsultation.class);
                    if(onlineConsultation.getDocopinion().equals("")){
                    back.add(onlineConsultation);
                    }
                }
                onlineconadapter = new Onlineconadapter(OnlineConsultationdoc.this, (ArrayList<OnlineConsultation>) back);
                mRecyclerView.setAdapter(onlineconadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
