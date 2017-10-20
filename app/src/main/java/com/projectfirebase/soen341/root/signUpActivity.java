package com.projectfirebase.soen341.root;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Fragments.HomeFragment;

public class signUpActivity extends AppCompatActivity {

    TextView editTextSUFirstName;
    TextView editTextSULastName;
    TextView editTextSUEmail;
    TextView editTextSUPhone;
    TextView editTextSUZIP;
    TextView editTextSUPassword;
    TextView editTextSUConfPassword;
    Button buttonSUSignUp;

    FirebaseAuth authRef = FirebaseAuth.getInstance();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = rootRef.child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextSUFirstName = (TextView)findViewById(R.id.editTextSUFirstName);
        editTextSULastName = (TextView)findViewById(R.id.editTextSULastName);
        editTextSUEmail = (TextView)findViewById(R.id.editTextSUEmail);
        editTextSUPhone = (TextView)findViewById(R.id.editTextSUPhone);
        editTextSUZIP = (TextView)findViewById(R.id.editTextSUZIP);
        editTextSUPassword = (TextView)findViewById(R.id.editTextSUPassword);
        editTextSUConfPassword = (TextView)findViewById(R.id.editTextSUConfPassword);

        buttonSUSignUp = (Button)findViewById(R.id.buttonSUSignUp);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Login
        buttonSUSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }

            ;
        });
    }

    private void signUp() {
        final String email = editTextSUEmail.getText().toString().trim();
        final String firstName = editTextSUFirstName.getText().toString().trim();
        final String lastName = editTextSULastName.getText().toString().trim();
        final String phoneNumber = editTextSUPhone.getText().toString().trim();
        final String ZIPCode = editTextSUZIP.getText().toString().trim();

        final String password = editTextSUPassword.getText().toString().trim();
        final String confPassword = editTextSUConfPassword.getText().toString().trim();

    // Validate all fields
        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || ZIPCode.isEmpty()) {
            Toast.makeText(signUpActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confPassword)) {
            Toast.makeText(signUpActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
        } else {

        // Create user
            authRef.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(signUpActivity.this, "Sign up successful!", Toast.LENGTH_LONG).show();
                        FirebaseUser user = task.getResult().getUser();
                        String UID = user.getUid();
                        User newUser = new User(firstName, lastName, phoneNumber, ZIPCode);
                        usersRef.child(UID).setValue(newUser);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });
        }
    }
}
