package com.projectfirebase.soen341.root;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity{

    TextView name;
    TextView email;
    TextView phoneNumber;
    ImageView photo;
    ImageButton addPhoto;

    FirebaseAuth authRef = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name =  (TextView) findViewById(R.id.profile_name);
        email = (TextView) findViewById(R.id.profile_email);
        phoneNumber = (TextView) findViewById(R.id.profile_phone_number);
        photo = (ImageView) findViewById(R.id.profile_photo);
        addPhoto = (ImageButton) findViewById(R.id.profile_add_photo);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(authRef.getCurrentUser() != null) {
                    name.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    phoneNumber.setVisibility(View.VISIBLE);
                    photo.setVisibility(View.VISIBLE);
                    addPhoto.setVisibility(View.VISIBLE);
                }
                else {
                    name.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                    phoneNumber.setVisibility(View.GONE);
                    photo.setVisibility(View.GONE);
                    addPhoto.setVisibility(View.GONE);

                    // TODO: Display message telling user that they are currently not logged in. Suggest signing up or logging in.
                }

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO: Set listener for add_photo ImageButton to call addPhoto()

        // TODO: Set listener for edit_email, edit_phone_number

        // Add Auth State Listener
        authRef.addAuthStateListener(authListener);
    }
}
