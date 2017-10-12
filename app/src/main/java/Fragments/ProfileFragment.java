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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projectfirebase.soen341.root.R;

import Tasks.DownloadImageTask;

public class ProfileFragment extends Fragment {

    private TextView name_tv;
    private TextView email_tv;
    private TextView phoneNumber_tv;
    private TextView ZIP_tv;
    private ImageView photo_iv;
    private ImageButton addPhoto_ib;

    private String email;

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

    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myUser = myRootRef.child("Users");
    DatabaseReference myUID = myUser.child(user.getUid());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name_tv = (TextView) view.findViewById(R.id.profile_name);
        email_tv = (TextView) view.findViewById(R.id.profile_email);
        phoneNumber_tv = (TextView) view.findViewById(R.id.profile_phone_number);
        ZIP_tv = (TextView) view.findViewById(R.id.profile_ZIP);
        photo_iv = (ImageView) view.findViewById(R.id.profile_photo);
        addPhoto_ib = (ImageButton) view.findViewById(R.id.profile_add_photo);

        if (user != null) {
            email = user.getEmail();

            // TODO: Check if user's email is verified?

            email_tv.setText(email);

            name_tv.setVisibility(View.VISIBLE);
            email_tv.setVisibility(View.VISIBLE);
            phoneNumber_tv.setVisibility(View.VISIBLE);
            ZIP_tv.setVisibility(View.VISIBLE);
            photo_iv.setVisibility(View.VISIBLE);
            addPhoto_ib.setVisibility(View.VISIBLE);
        } else {
            name_tv.setVisibility(View.GONE);
            email_tv.setVisibility(View.GONE);
            phoneNumber_tv.setVisibility(View.GONE);
            ZIP_tv.setVisibility(View.GONE);
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
                editPhoto();
            }
        });

        // TODO: Set listener for edit_email, edit_phone_number

        myUID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imgUrl = dataSnapshot.child("ImageURL").getValue(String.class);
                new DownloadImageTask(photo_iv).execute(imgUrl);

                String name = dataSnapshot.child("FirstName").getValue(String.class) + " " + dataSnapshot.child("LastName").getValue(String.class);
                name_tv.setText(name);

                String phoneNbr = dataSnapshot.child("PhoneNumber").getValue(String.class);
                phoneNumber_tv.setText(phoneNbr);

                String ZIP = dataSnapshot.child("ZIPCode").getValue(String.class);
                ZIP_tv.setText(ZIP);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
