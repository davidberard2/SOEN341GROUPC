package com.projectfirebase.soen341.root;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class signUpActivity extends AppCompatActivity {

    TextView editTextSUFirstName;
    TextView editTextSULastName;
    TextView editTextSUEmail;
    TextView editTextSUPhone;
    TextView editTextSUZIP;
    TextView editTextSUPassword;
    TextView editTextSUConfPassword;
    Button buttonSUSignUp;

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

    }
}
