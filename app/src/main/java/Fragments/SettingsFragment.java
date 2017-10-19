package Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.projectfirebase.soen341.root.R;

public class SettingsFragment extends Fragment {


    Button logInB;
    Button logOutB;
    Button signUpB;

    FirebaseAuth authRef = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener;

    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        logInB = (Button)getView().findViewById(R.id.logInB);
        logOutB = (Button)getView().findViewById(R.id.logOutB);

        // SET Auth State Listener
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(authRef.getCurrentUser() != null) {
                    logInB.setVisibility(View.GONE);
                    logOutB.setVisibility(View.VISIBLE);
                }
                else {
                    logInB.setVisibility(View.VISIBLE);
                    logOutB.setVisibility(View.GONE);
                }
            }
        };

        // ADD Auth State Listener
        authRef.addAuthStateListener(authListener);
    }
}
