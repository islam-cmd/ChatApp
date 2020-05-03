package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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


public class RequestConsultationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner areaSpinner, clinicSpinner, specialitySpinner, doctorSpinner, appointmentSpinner;
    ImageButton back_btn;
    Button btn_request;

    FirebaseUser fuser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_consultation);

        areaSpinner = (Spinner) findViewById(R.id.areaSpinner);
        clinicSpinner = (Spinner) findViewById(R.id.clinicSpinner);
        specialitySpinner = (Spinner) findViewById(R.id.specialitySpinner);
        doctorSpinner = (Spinner) findViewById(R.id.doctorSpinner);

        areaSpinner.setOnItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Request Consultation");

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spArea = String.valueOf(areaSpinner.getSelectedItem());
                String spClinic = String.valueOf(clinicSpinner.getSelectedItem());
                String spSpeciality = String.valueOf(specialitySpinner.getSelectedItem());
                String spDoctor = String.valueOf(doctorSpinner.getSelectedItem());

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        String spArea = String.valueOf(areaSpinner.getSelectedItem());
        Toast.makeText(this, spArea, Toast.LENGTH_SHORT).show();
        if(spArea.contentEquals("Sydney CBD")) {
            List<String> list = new ArrayList<String>();
            list.add("Barangaroo Clinic, 200 Barangaroo Avenue");
            list.add("Chippendale Clinic, 29 Broardway Street");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            clinicSpinner.setAdapter(dataAdapter);
        }
        if(spArea.contentEquals("Western Sydney")) {
            List<String> list = new ArrayList<String>();
            list.add("Lakemba Clinic, 27 Railway Parade");
            list.add("Cabramatta Clinic, 117 John Street");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            clinicSpinner.setAdapter(dataAdapter2);
        }

        String spClinic = String.valueOf(clinicSpinner.getSelectedItem());

        String spSpeciality = String.valueOf(specialitySpinner.getSelectedItem());
        Toast.makeText(this, spArea, Toast.LENGTH_SHORT).show();
        if(spSpeciality.contentEquals("General") && spClinic.contentEquals("Lakemba Clinic, 27 Railway Parade")) {
            List<String> list = new ArrayList<String>();
            list.add("Dr Sam Smith");
            list.add("Dr Alexandra Jones");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            doctorSpinner.setAdapter(dataAdapter);
        }
        if(spSpeciality.contentEquals("Pediatrics") && spClinic.contentEquals("Lakemba Clinic, 27 Railway Parade")) {
            List<String> list = new ArrayList<String>();
            list.add("Dr John Johnston");
            list.add("Dr Adelaide White");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            doctorSpinner.setAdapter(dataAdapter2);
        }

        String spDoctor = String.valueOf(doctorSpinner.getSelectedItem());


    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}