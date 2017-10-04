package Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.projectfirebase.soen341.root.R;

public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView email;
    private TextView phoneNumber;
    private ImageView photo;
    private ImageButton addPhoto;

    FirebaseAuth authRef = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name =  (TextView) view.findViewById(R.id.profile_name);
        email = (TextView) view.findViewById(R.id.profile_email);
        phoneNumber = (TextView) view.findViewById(R.id.profile_phone_number);
        photo = (ImageView) view.findViewById(R.id.profile_photo);
        addPhoto = (ImageButton) view.findViewById(R.id.profile_add_photo);

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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // TODO: Set listener for add_photo ImageButton to call addPhoto()

        // TODO: Set listener for edit_email, edit_phone_number

        // Add Auth State Listener
        authRef.addAuthStateListener(authListener);
    }
}
