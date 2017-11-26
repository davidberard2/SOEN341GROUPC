package com.projectfirebase.soen341.root;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import Fragments.LoggedOutFragment;

public class LoginActivity extends AppCompatActivity {
    TextView email_et;
    TextView password_et;
    Button login_b;
    Button back_b;

    FirebaseAuth authRef = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // View References
        email_et = (TextView) findViewById(R.id.editTextEmail);
        password_et = (TextView) findViewById(R.id.editTextPassword);
        login_b = (Button) findViewById(R.id.buttonLogin);
        back_b = (Button) findViewById(R.id.buttonBack);
    }

    @Override
    protected void onStart() {
        super.onStart();

        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        back_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void login() {
        // Handle Empty Fields
        if (email_et.getText().toString().trim().equals("") || password_et.getText().toString().trim().equals("")) {
            Toast.makeText(LoginActivity.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
        } else {
            // Get Field Values
            String email = email_et.getText().toString();
            String password = password_et.getText().toString();

            // Login with Email
            authRef.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Toast.makeText(LoginActivity.this, R.string.error_invalid_email, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }
}
