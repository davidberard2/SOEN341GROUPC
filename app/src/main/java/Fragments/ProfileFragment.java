package Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ImageView photo_iv;
    private ImageButton addPhoto_ib;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name_tv = (TextView) view.findViewById(R.id.profile_name);
        email_tv = (TextView) view.findViewById(R.id.profile_email);
        phoneNumber_tv = (TextView) view.findViewById(R.id.profile_phone_number);
        photo_iv = (ImageView) view.findViewById(R.id.profile_photo);
        addPhoto_ib = (ImageButton) view.findViewById(R.id.profile_add_photo);

        if (user != null) {
            name = user.getDisplayName();
            email = user.getEmail();
            // photoUrl = user.getPhotoUrl();
            photoUrl = Uri.parse("http://i0.kym-cdn.com/photos/images/original/000/692/145/49c.png");

            // TODO: Check if user's email is verified?

            name_tv.setText(name);
            email_tv.setText(email);

            new DownloadImageTask(photo_iv).execute(photoUrl.toString());

            name_tv.setVisibility(View.VISIBLE);
            email_tv.setVisibility(View.VISIBLE);
            phoneNumber_tv.setVisibility(View.VISIBLE);
            photo_iv.setVisibility(View.VISIBLE);
            addPhoto_ib.setVisibility(View.VISIBLE);
        } else {
            name_tv.setVisibility(View.GONE);
            email_tv.setVisibility(View.GONE);
            phoneNumber_tv.setVisibility(View.GONE);
            photo_iv.setVisibility(View.GONE);
            addPhoto_ib.setVisibility(View.GONE);

            // TODO: Display message telling user that they are currently not logged in. Suggest signing up or logging in.
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
                addPhoto();
            }
        });

        // TODO: Set listener for edit_email, edit_phone_number
    }

    private void addPhoto() {
        UserProfileChangeRequest updatePhoto = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectfirebase-9323d.appspot.com/o/test_profile_photo.jpg?alt=media&token=8653a2a4-37e4-4534-a9b0-3de3c54f14c2"))
                .build();

        user.updateProfile(updatePhoto).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("PHOTO", "User profile updated.");
                }
            }
        });
    }
}
