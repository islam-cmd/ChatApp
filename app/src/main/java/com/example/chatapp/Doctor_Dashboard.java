package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Doctor_Dashboard extends AppCompatActivity {
    Button profilebtn;
    private Button view_patbtn;
    private Button create_prescribbtn;
    private Button location_btn;
    private Button view_messagesbtn;
    private Button Contact_supportbtn;
    private Button log_outbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_dashboard);

        log_outbtn = (Button) findViewById(R.id.log_out);
        log_outbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doctor_Dashboard.this, StartActivity.class);
                finish(); }
        });

    }

}
