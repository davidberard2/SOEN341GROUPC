package Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< Updated upstream
=======
import android.view.WindowManager;
>>>>>>> Stashed changes
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.projectfirebase.soen341.root.R;

import Tasks.DownloadImageTask;

public class ProfileFragment extends Fragment {

    private TextView name_tv;
    private TextView email_tv;
    private TextView phoneNumber_tv;
    private TextView loggedOut_tv;
    private ImageView photo_iv;
<<<<<<< Updated upstream
    private ImageButton addPhoto_ib;
=======
    private TextView loggedOut_tv;

    private ImageButton updatePhoto_ib;
    private Button login_b;
    private Button signup_b;
>>>>>>> Stashed changes

    private String name;
    private String email;
    private Uri photoUrl;

    // Login button
    private Button login_b;
    private Button signup_b;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name_tv = (TextView) view.findViewById(R.id.profile_name);
        email_tv = (TextView) view.findViewById(R.id.profile_email);
        phoneNumber_tv = (TextView) view.findViewById(R.id.profile_phone_number);
        photo_iv = (ImageView) view.findViewById(R.id.profile_photo);
<<<<<<< Updated upstream
        addPhoto_ib = (ImageButton) view.findViewById(R.id.profile_add_photo);

=======
        updatePhoto_ib = (ImageButton) view.findViewById(R.id.profile_update_photo);
>>>>>>> Stashed changes
        loggedOut_tv = (TextView) view.findViewById(R.id.logged_out);
        login_b = (Button)view.findViewById(R.id.logInB);
        signup_b = (Button)view.findViewById(R.id.signUpB);


        if (user != null) {
            name = user.getDisplayName();
            email = user.getEmail();
//            photoUrl = user.getPhotoUrl();
            // tmp
            photoUrl = Uri.parse("http://i0.kym-cdn.com/photos/images/original/000/692/145/49c.png");

            // TODO: Check if user's email is verified?

            if (name != null) {
                name_tv.setText(name);
            }
            else {
                name_tv.setText("Evan Mateo");
//                editName();
//                name = user.getDisplayName();
//                name_tv.setText(name);
            }

            email_tv.setText(email);

            new DownloadImageTask(photo_iv).execute(photoUrl.toString());

            name_tv.setVisibility(View.VISIBLE);
            email_tv.setVisibility(View.VISIBLE);
            phoneNumber_tv.setVisibility(View.VISIBLE);
            photo_iv.setVisibility(View.VISIBLE);
<<<<<<< Updated upstream
            addPhoto_ib.setVisibility(View.VISIBLE);
=======
            updatePhoto_ib.setVisibility(View.VISIBLE);
>>>>>>> Stashed changes

            loggedOut_tv.setVisibility(View.GONE);
            login_b.setVisibility(View.GONE);
            signup_b.setVisibility(View.GONE);
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        } else {
            name_tv.setVisibility(View.GONE);
            email_tv.setVisibility(View.GONE);
            phoneNumber_tv.setVisibility(View.GONE);
            photo_iv.setVisibility(View.GONE);
            addPhoto_ib.setVisibility(View.GONE);

<<<<<<< Updated upstream
            // TODO: Display message telling user that they are currently not logged in. Suggest signing up or logging in.
=======
>>>>>>> Stashed changes
            loggedOut_tv.setVisibility(View.VISIBLE);
            signup_b.setVisibility(View.VISIBLE);
            login_b.setVisibility(View.VISIBLE);
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // TODO: Set listener for add_photo ImageButton to call addPhoto()
        addPhoto_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPhoto();
            }
        });

        // TODO: Set listener for edit_email, edit_phone_number
    }

    private void editPhoto() {
        UserProfileChangeRequest updatePhoto = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectfirebase-9323d.appspot.com/o/test_profile_photo.jpg?alt=media&token=8653a2a4-37e4-4534-a9b0-3de3c54f14c2"))
                .build();

        user.updateProfile(updatePhoto).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("update_user", "User profile photo updated.");
                }
            }
        });
    }

    private void editName() {
        UserProfileChangeRequest updateName = new UserProfileChangeRequest.Builder()
                .setDisplayName("Evan Mateo") // TODO: Implement an "Edit Name" fragment
                .build();

        user.updateProfile(updateName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("update_user", "User profile name updated.");
                }
            }
        });
    }
}
