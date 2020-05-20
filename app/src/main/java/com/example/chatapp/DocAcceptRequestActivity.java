package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DocAcceptRequestActivity extends AppCompatActivity {
    private Button btn_denyRequest,btn_acceptRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_accept_request);
        btn_denyRequest = findViewById(R.id.btn_denyRequest);
        btn_acceptRequest = findViewById(R.id.btn_acceptRequest);

        btn_acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocAcceptRequestActivity.this, DocScheduleDisplayActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
