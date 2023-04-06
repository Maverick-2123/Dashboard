package com.example.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    Button button;

    String user_id;

    Button signup_btn;
    FirebaseAuth mAuth;

    EditText email;
    EditText name;

    EditText password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //FirebaseFirestore db = FirebaseFirestore.getInstance();

        button = (Button)findViewById(R.id.button4);
        mAuth = FirebaseAuth.getInstance();
        signup_btn = (Button)findViewById(R.id.button3);
        email = (EditText)findViewById(R.id.editTextPassword);
        name = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();

                //String user_id = email.getText().toString();

            }
        });
    }

    private void createUser()
    {
        String email_user = email.getText().toString();
        String password_user = password.getText().toString();

        if (TextUtils.isEmpty(email_user))
        {
            email.setError("Email cannot be empty");
        } else if (TextUtils.isEmpty(password_user)) {
            password.setError("Password cannot be empty");
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email_user,password_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        user_id = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("user_login").document(user_id);
                        Map<String,Object> user = new HashMap<>();
                        user.put("fname",name.getText().toString());
                        user.put("email",email.getText().toString());
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                        startActivity(new Intent(getApplicationContext(),login.class));
                    }
                    else
                    {
                        Toast.makeText(signup.this, "Registration error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}