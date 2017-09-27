package com.projectfirebase.soen341.root;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

//TEST LOGGING

// Status
    TextView textViewStatus;
    Button buttonGood;
    Button buttonBad;

// Login
    Button buttonLoginPage;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference statusRef = rootRef.child("Status");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Status
        textViewStatus = (TextView)findViewById(R.id.textViewStatus);
        buttonGood = (Button)findViewById(R.id.buttonGood);
        buttonBad = (Button)findViewById(R.id.buttonBad);

        // Login
        buttonLoginPage = (Button)findViewById(R.id.buttonLoginPage);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Status
        statusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                textViewStatus.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusRef.setValue("Good!");
            }
        });

        buttonBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusRef.setValue("Bad!");
            }
        });

        buttonLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

}
