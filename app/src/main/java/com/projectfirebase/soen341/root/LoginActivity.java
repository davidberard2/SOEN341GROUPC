package com.projectfirebase.soen341.root;

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

public class LoginActivity extends AppCompatActivity  {

    TextView editTextEmail;
    TextView editTextPassword;
    Button buttonLogin;
    Button buttonLogout;

    FirebaseAuth authRef = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    // View References
        editTextEmail = (TextView)findViewById(R.id.editTextEmail);
        editTextPassword = (TextView)findViewById(R.id.editTextPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogout = (Button)findViewById(R.id.buttonLogout);

    // SET Auth State Listener
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(authRef.getCurrentUser() != null) {
                    Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                    buttonLogout.setVisibility(View.VISIBLE);
                    buttonLogin.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                    buttonLogout.setVisibility(View.GONE);
                    buttonLogin.setVisibility(View.VISIBLE);
                }

            }
        };

    // ADD Auth State Listener
        authRef.addAuthStateListener(authListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

    // Login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            };
        });

    // Logout button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authRef.signOut();
            }
        });
    }

    private void login() {
    // Handle Empty Fields
        if(editTextEmail.getText().toString().trim().equals("") || editTextPassword.getText().toString().trim().equals("") ) {
            Toast.makeText(LoginActivity.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
        }
        else {
        // Get Field Values
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

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
                    }
                    else{
                        finish();
                    }
                }
            });
        }
    }
}
