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

    private void signUp(){
        String email = editTextSUEmail.getText().toString();
        String password = editTextSUPassword.getText().toString();
        authRef.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(signUpActivity.this, "Sign up successful!", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
