package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.projectfirebase.soen341.root.R;

import static com.projectfirebase.soen341.root.R.id.logInB;
import static com.projectfirebase.soen341.root.R.id.signUpB;

public class LoggedOutFragment extends Fragment {
    private TextView loggedOut_tv;
    private Button login_b;
    private Button signup_b;

    FirebaseAuth authRef = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener;

    public LoggedOutFragment() {
        // Required empty default constructor
    }

    public static LoggedOutFragment newInstance() {
        return new LoggedOutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logged_out, container, false);
        setAuthStateListener(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void setAuthStateListener(View view) {
        loggedOut_tv = (TextView) view.findViewById(R.id.logged_out);
        login_b = (Button)view.findViewById(logInB);
        signup_b = (Button)view.findViewById(signUpB);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(authRef.getCurrentUser() != null) {
                    loggedOut_tv.setVisibility(View.GONE);
                    login_b.setVisibility(View.GONE);
                    signup_b.setVisibility(View.GONE);
                }
                else {
                    loggedOut_tv.setVisibility(View.VISIBLE);
                    signup_b.setVisibility(View.VISIBLE);
                    login_b.setVisibility(View.VISIBLE);
                }
            }
        };

        authRef.addAuthStateListener(authListener);
    }
}