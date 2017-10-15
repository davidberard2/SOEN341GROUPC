package Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.projectfirebase.soen341.root.R;

import Tasks.DownloadImageTask;

public class ProfileFragment extends Fragment {

    View view;

    // TODO:  Refactor to abide by name conventions
    private TextView name_tv;
    private TextView email_tv;
    private TextView phoneNumber_tv;
    private ImageView photo_iv;

    private ImageButton updatePhoto_ib;

    private String name;
    private String email;
    private Uri photoUrl;

    // Login button

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        name_tv = (TextView) view.findViewById(R.id.profile_name);
        email_tv = (TextView) view.findViewById(R.id.profile_email);
        phoneNumber_tv = (TextView) view.findViewById(R.id.profile_phone_number);
        photo_iv = (ImageView) view.findViewById(R.id.profile_photo);
        updatePhoto_ib = (ImageButton) view.findViewById(R.id.profile_update_photo);

        if (user != null) {
            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();

            if (name != null) {
                name_tv.setText(name);
            }

            // TODO: Check if user's email is verified?
            email_tv.setText(email);

            if (photoUrl != null) {
                new DownloadImageTask(photo_iv).execute(photoUrl.toString());
            }

            name_tv.setVisibility(View.VISIBLE);
            email_tv.setVisibility(View.VISIBLE);
            phoneNumber_tv.setVisibility(View.VISIBLE);
            photo_iv.setVisibility(View.VISIBLE);
            updatePhoto_ib.setVisibility(View.VISIBLE);
        } else {
            name_tv.setVisibility(View.GONE);
            email_tv.setVisibility(View.GONE);
            phoneNumber_tv.setVisibility(View.GONE);
            photo_iv.setVisibility(View.GONE);
            updatePhoto_ib.setVisibility(View.GONE);

            // TODO: Display message telling user that they are currently not logged in. Suggest signing up or logging in.
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        updatePhoto_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePhoto();
            }
        });
        // TODO: Add a single listener for text field modifications
    }

    private void updatePhoto() {
        // TODO: Select photo from user's local storage after Issue #26
        UserProfileChangeRequest updatePhoto = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectfirebase-9323d.appspot.com/o/test_profile_photo.jpg?alt=media&token=8653a2a4-37e4-4534-a9b0-3de3c54f14c2"))
                .build();

        user.updateProfile(updatePhoto).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("USER_UPDATE", "User profile photo updated.");
                }
            }
        });
    }

    private void updateName() {
        if(name_tv.getText().toString().trim().equals("")) {
            Toast.makeText(view.getContext(), "This is not a valid name", Toast.LENGTH_SHORT).show();
        }
        else {
            name = name_tv.getText().toString();
            UserProfileChangeRequest updateName = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(updateName).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("USER_UPDATE", "User profile name updated.");
                    }
                }
            });
        }

    }

    private void updateEmail() {
        if(name_tv.getText().toString().trim().equals("")) {
            Toast.makeText(view.getContext(), "This is not a valid name", Toast.LENGTH_SHORT).show();
        }
        else {
            email = name_tv.getText().toString();
            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("USER_UPDATE", "User email updated.");
                            }
                        }
                    });
        }
    }
}
