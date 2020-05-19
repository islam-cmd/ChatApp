package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Request_ConsultationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //MaterialEditText editDate;
    TextView username;
    CircleImageView profile_image;
    Button send_btn, back_btn;
    //Spinner areaSpinner, clinicSpinner, specialitySpinner, doctorSpinner, appointmentSpinner;
    FirebaseUser fuser;
    String doctorid;
    DatabaseReference currentUser;
    public static String toReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestconsultation);

        send_btn = findViewById(R.id.btn_sendRequest);
        back_btn = findViewById(R.id.btn_back);
        username = findViewById(R.id.username);
        profile_image = findViewById(R.id.profile_image);


        final Spinner areaSpinner = findViewById(R.id.areaSpinner);
        areaSpinner.setOnItemSelectedListener(this);

        final Spinner clinicSpinner =findViewById(R.id.clinicSpinner);
        clinicSpinner.setOnItemSelectedListener(this);

        final Spinner specialitySpinner = findViewById(R.id.specialitySpinner);
        specialitySpinner.setOnItemSelectedListener(this);

        final Spinner appointmentSpinner = findViewById(R.id.appointmentSpinner);
        appointmentSpinner.setOnItemSelectedListener(this);

        final Spinner doctorSpinner = findViewById(R.id.doctorSpinner);
        doctorSpinner.setOnItemSelectedListener(this);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        send_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String clinic = clinicSpinner.getSelectedItem().toString();
                String doctor = doctorSpinner.getSelectedItem().toString();
                String appointmentTime = appointmentSpinner.getSelectedItem().toString();
                doctorid = runEventListener(doctor);
                if (TextUtils.isEmpty(clinic) || TextUtils.isEmpty(doctor)  || TextUtils.isEmpty(appointmentTime)){
                    Toast.makeText(Request_ConsultationActivity.this, "Please fill out all of the text boxes", Toast.LENGTH_SHORT).show();
                } else {
                    String patientId =currentUser.push().getKey();
                    writeAppointment(patientId, clinic, doctorid, appointmentTime);
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Request_ConsultationActivity.this, PatientDashboard.class);
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
                    Glide.with(Request_ConsultationActivity.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void writeAppointment(final String patientId, final String clinic, final String doctorid, final String appointmentTime) {

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("patientId", patientId);
        hashMap.put("clinic", clinic);
        hashMap.put("doctorId", doctorid);
        hashMap.put("appointmentTime", appointmentTime);
        hashMap.put("status", 0);
        refrence.child("RequestConsultation").push().setValue(hashMap);

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

