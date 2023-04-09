package com.example.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MainActivity extends AppCompatActivity {
    public static String act_name;
    public static String fname[];
    ImageButton add_btn;

    FirebaseAuth mAuth;

    TextView txtview;
    ImageButton del_btn;
    ImageButton open_btn;
    ImageButton fill_btn;

    ImageButton log_btn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_btn = (ImageButton)findViewById(R.id.add_button);
        del_btn = (ImageButton)findViewById(R.id.delete_button);
        open_btn = (ImageButton)findViewById(R.id.open_button);
        fill_btn = (ImageButton)findViewById(R.id.fill_button);
        txtview = (TextView)findViewById(R.id.textUsername);
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.nearby:
                        startActivity(new Intent(getApplicationContext(),nearby.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),setting.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //txtview.setText(name);

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

        // Code for fetch data to Firestore

        String user_id = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("user_login").document(user_id);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String name = value.getString("fname").toString();
                fname = name.split("\\s");
                act_name = fname[0];
                txtview.setText(fname[0]);
            }
        });
    }
}