package com.example.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Adapter.DoctorAdapter;
import com.example.chatapp.Adapter.UserAdapter;
import com.example.chatapp.Model.Doctor;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DoctorsFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Doctor> mUsers;
    private DoctorAdapter doctorAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsers = new ArrayList<>();
        readDoctors();
        return view;
    }
    public void readDoctors() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference("Doctors");
//        DatabaseReference docRef  = FirebaseDatabase.getInstance().getReference();
    refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctor user = snapshot.getValue(Doctor.class);
//                    assert user != null;
//                    assert firebaseUser != null;
//                    if (!user.getId().equals(firebaseUser.getUid())) {
                    mUsers.add(user);
//                    }
                }
             doctorAdapter = new DoctorAdapter(getContext(), mUsers);
                recyclerView.setAdapter(doctorAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
