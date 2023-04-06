package com.example.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    ImageButton add_btn;

    FirebaseAuth mAuth;
    ImageButton del_btn;
    ImageButton open_btn;
    ImageButton fill_btn;

    ImageButton log_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_btn = (ImageButton)findViewById(R.id.add_button);
        del_btn = (ImageButton)findViewById(R.id.delete_button);
        open_btn = (ImageButton)findViewById(R.id.open_button);
        fill_btn = (ImageButton)findViewById(R.id.fill_button);
        mAuth = FirebaseAuth.getInstance();

        log_btn = (ImageButton)findViewById(R.id.imageMenu);
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),loginRegister.class));
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),qrscanner.class));
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),qrdelete.class));
            }
        });

        open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),OpenDustbin.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null)
        {
            startActivity(new Intent(getApplicationContext(),loginRegister.class));
        }
    }
}