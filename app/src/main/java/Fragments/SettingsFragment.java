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
    Button logOutB;
    Button aboutB;
    Button notificationsB;

    FirebaseAuth authRef = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setAuthStateListener(view);
        return view;
    }

    public void setAuthStateListener(View view) {
        logOutB = (Button) view.findViewById(R.id.logOutB);
        aboutB = (Button) view.findViewById(R.id.aboutB);
        notificationsB = (Button) view.findViewById(R.id.notificationsB);

//        postItemButton.setOnClickListener(new View.OnClickListener() {}


        // SET Auth State Listener
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                logOutB.setVisibility(View.VISIBLE);
                notificationsB.setVisibility(View.VISIBLE);
                aboutB.setVisibility(View.VISIBLE);
            }
        };

        // ADD Auth State Listener
        authRef.addAuthStateListener(authListener);
    }
}
