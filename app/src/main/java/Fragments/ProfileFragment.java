package Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
    private TextView name_et;
    private TextView email_et;
    private TextView phoneNumber_et;
    private ImageView photo_iv;

    private ImageButton updatePhoto_ib;
    Button update_ib;

    private String name;
    private String email;
    private Uri photoUrl;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //Image upload variable
    private static int IMG_RESULT = 1;
    Intent intent;

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

        name_et = (TextView) view.findViewById(R.id.profile_name);
        email_et = (TextView) view.findViewById(R.id.profile_email);
        phoneNumber_et = (TextView) view.findViewById(R.id.profile_phone_number);
        photo_iv = (ImageView) view.findViewById(R.id.profile_photo);
        updatePhoto_ib = (ImageButton) view.findViewById(R.id.profile_update_photo);
        update_ib = (Button) view.findViewById(R.id.button2);

        update_ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);

            }
        });

        if (user != null) {
            // Display menu save option
            setHasOptionsMenu(true);

            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();

            if (name != null) {
                name_et.setText(name);
            }

            // TODO: Check if user's email is verified?
            email_et.setText(email);

            if (photoUrl != null) {
                new DownloadImageTask(photo_iv).execute(photoUrl.toString());
            }

            name_et.setVisibility(View.VISIBLE);
            email_et.setVisibility(View.VISIBLE);
            phoneNumber_et.setVisibility(View.VISIBLE);
            photo_iv.setVisibility(View.VISIBLE);
            updatePhoto_ib.setVisibility(View.GONE);
            update_ib.setVisibility(View.VISIBLE);
        } else {
            name_et.setVisibility(View.GONE);
            email_et.setVisibility(View.GONE);
            phoneNumber_et.setVisibility(View.GONE);
            photo_iv.setVisibility(View.GONE);
            updatePhoto_ib.setVisibility(View.GONE);
            update_ib.setVisibility(View.GONE);

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
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem saveMenuItem = menu.findItem(R.id.profile_save_button);
        saveMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                updateName();
                updateEmail();
                return true;
            }
        });
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
        if (name_et.getText().toString().trim().equals("")) {
            Toast.makeText(view.getContext(), "Name is invalid", Toast.LENGTH_SHORT).show();
        } else {
            name = name_et.getText().toString();
            UserProfileChangeRequest updateName = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(updateName).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("USER_UPDATE", "User profile name updated.");
                    } else {
                        Toast.makeText(view.getContext(), "Name update error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // TODO: Only Gmail updates go through, possibly fix?
    private void updateEmail() {
        if (email_et.getText().toString().trim().equals("")) {
            Toast.makeText(view.getContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
        } else {
            email = email_et.getText().toString();
            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("USER_UPDATE", "User email updated.");
                            } else {
                                Toast.makeText(view.getContext(), "Email update error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
