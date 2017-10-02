package com.projectfirebase.soen341.root;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import Fragments.HomeFragment;
import Fragments.ProfileFragment;
import Fragments.SearchFragment;
import Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

// Status
//    TextView textViewStatus;
//    Button buttonGood;
//    Button buttonBad;

// Login
//    Button buttonLoginPage;
//
//    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference statusRef = rootRef.child("Status");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.action_search:
                                selectedFragment = SearchFragment.newInstance();
                                break;
                            case R.id.action_profile:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                            case R.id.action_settings:
                                selectedFragment = SettingsFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();

        // Status
//        textViewStatus = (TextView)findViewById(R.id.textViewStatus);
//        buttonGood = (Button)findViewById(R.id.buttonGood);
//        buttonBad = (Button)findViewById(R.id.buttonBad);

        // Login
 //       buttonLoginPage = (Button)findViewById(R.id.buttonLoginPage);

    }

    @Override
    protected void onStart() {
        super.onStart();

//        // Status
//        statusRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String text = dataSnapshot.getValue(String.class);
//                textViewStatus.setText(text);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        buttonGood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                statusRef.setValue("Good!");
//            }
//        });
//
//        buttonBad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                statusRef.setValue("Bad!");
//            }
//        });
//
//        buttonLoginPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });

    }

}
