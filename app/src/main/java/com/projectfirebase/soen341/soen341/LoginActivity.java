package com.projectfirebase.soen341.soen341;

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

public class LoginActivity extends AppCompatActivity {

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

    // Login
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
                }
                else {
                    buttonLogout.setVisibility(View.GONE);
                }

            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();

    // Login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            };
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(LoginActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
            }
        });
    // ADD Auth State Listener
        authRef.addAuthStateListener(authListener);
    }


    private void login() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        authRef.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Error with login", Toast.LENGTH_SHORT).show();
                }
                // else Get Auth State
            }
        });
    }

}
