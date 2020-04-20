package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class DocResetActivity extends AppCompatActivity {
    FirebaseAuth auth;
    MaterialEditText email;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_reset);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Doctor Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.email);
        reset = findViewById(R.id.Reset);
        auth = FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String send_email = email.getText().toString();
                if (send_email.equals("")) {
                    Toast.makeText(DocResetActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(send_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DocResetActivity.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DocResetActivity.this, LoginActivity.class));
                            }else{
                                String error =  task.getException().getMessage();
                                Toast.makeText(DocResetActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
