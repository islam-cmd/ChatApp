package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RequestConsultationActivity extends AppCompatActivity {

    Spinner areaSpinner, clinicSpinner, specialitySpinner, doctorSpinner;
    ImageButton back_btn;
    Button btn_request;

    FirebaseUser fuser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_consultation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Request Consultation");

        addItemsOnClinicSpinner();

        addListenerOnButton();

        addListenerOnSpinnerItemSelection();

    }

    // get the selected dropdown list value
    public void addListenerOnSpinnerItemSelection() {

        areaSpinner = (Spinner) findViewById(R.id.areaSpinner);

        areaSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }



    // add items into clinic spinner dynamically
    public void addItemsOnClinicSpinner() {

        clinicSpinner = (Spinner) findViewById(R.id.clinicSpinner);

        List<String> list = new ArrayList<String>();

            list.add("Barangaroo Clinic, 200 Barangaroo Avenue");

            list.add("Chippendale Clinic, 29 Broardway Street");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,

                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clinicSpinner.setAdapter(dataAdapter);

    }




    public void addListenerOnButton() {

    }
}