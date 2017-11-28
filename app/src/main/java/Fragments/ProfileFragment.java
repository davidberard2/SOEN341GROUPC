package Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projectfirebase.soen341.root.Helper;
import com.projectfirebase.soen341.root.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.projectfirebase.soen341.root.Helper.setImage;

public class ProfileFragment extends Fragment {
    View view;

    private TextView firstName_et;
    private TextView lastName_et;
    private TextView email_et;
    private TextView phoneNumber_et;
    private TextView postalCode_et;
    private ImageView photo_iv;

    private ImageButton updatePhoto_ib;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String postalCode;
    private Uri photoUrl;

    private MenuItem editMenuItem;
    private MenuItem saveMenuItem;
    private MenuItem cancelMenuItem;
    private MenuItem settingsMenuItem;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //Database Update
    private DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference myUser = myRootRef.child("Users");
    private DatabaseReference myUID;

    //Firebase Authentication
    FirebaseAuth authRef = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener;

    //Image upload variable
    private static final int IMG_RESULT = 1;
    private Intent intent;
    private StorageReference storageRef = FirebaseStorage.getInstance("gs://projectfirebase-9323d.appspot.com").getReference();
    private StorageReference profilePictureRef = storageRef.child("ProfilePictures");

    public ProfileFragment() {
        // Required empty default constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        setAuthStateListener(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        editMenuItem = menu.findItem(R.id.profile_edit_button);
        saveMenuItem = menu.findItem(R.id.profile_save_button);
        cancelMenuItem = menu.findItem(R.id.profile_cancel_button);
        settingsMenuItem = menu.findItem(R.id.profile_settings_button);

        editMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                toggleEditMode(true);
                return true;
            }
        });
        saveMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                updateName();
                updateEmail();
                updatePhoneNumber();
                updatePostalCode();

                toggleEditMode(false);
                return true;
            }
        });
        cancelMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                setProfileFields();
                toggleEditMode(false);
                return true;
            }
        });
        settingsMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, new SettingsFragment());
                transaction.commit();
                return true;
            }
        });
    }

    private void toggleEditMode(boolean editMode) {
        firstName_et.setFocusableInTouchMode(editMode);
        firstName_et.setEnabled(editMode);
        lastName_et.setFocusableInTouchMode(editMode);
        lastName_et.setEnabled(editMode);
        email_et.setFocusableInTouchMode(editMode);
        email_et.setEnabled(editMode);
        phoneNumber_et.setFocusableInTouchMode(editMode);
        phoneNumber_et.setEnabled(editMode);
        postalCode_et.setFocusableInTouchMode(editMode);
        postalCode_et.setEnabled(editMode);
        editMenuItem.setVisible(!editMode);
        saveMenuItem.setVisible(editMode);
        cancelMenuItem.setVisible(editMode);
        settingsMenuItem.setVisible(!editMode);

        if (!editMode) {
            firstName_et.clearFocus();
            lastName_et.clearFocus();
            email_et.clearFocus();
            phoneNumber_et.clearFocus();
            postalCode_et.clearFocus();
        }
    }

    private void setProfileFields() {
        firstName_et.setText(firstName);
        lastName_et.setText(lastName);
        email_et.setText(email);
        phoneNumber_et.setText(phoneNumber);
        postalCode_et.setText(postalCode);
    }

    // Not currently used
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
        if (Helper.isNullOrEmpty(firstName_et.getText().toString().trim()) || Helper.isNullOrEmpty(lastName_et.getText().toString().trim())) {
            Toast.makeText(view.getContext(), "Name field is invalid", Toast.LENGTH_SHORT).show();
        } else if (!firstName_et.getText().toString().trim().equals(firstName) || !lastName_et.getText().toString().trim().equals(lastName)) {
            firstName = firstName_et.getText().toString().trim();
            myUID.child("FirstName").setValue(firstName);

            lastName = lastName_et.getText().toString().trim();
            myUID.child("LastName").setValue(lastName);
        }
    }

    // TODO: Only Gmail updates go through, possibly fix?
    private void updateEmail() {
        if (Helper.isNullOrEmpty(email_et.getText().toString().trim())) {
            Toast.makeText(view.getContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
        } else if (!email_et.getText().toString().trim().equals(email)) {
            email = email_et.getText().toString().trim();
            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                myUID.child("Email").setValue(email);
                                Log.d("USER_EMAIL_UPDATE", "User email updated.");
                            } else {
                                Toast.makeText(view.getContext(), "Email update error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // Do we want to allow people to remove their phone numbers?
    private void updatePhoneNumber() {
        if (!phoneNumber_et.getText().toString().trim().equals(phoneNumber)) {
            phoneNumber = phoneNumber_et.getText().toString().trim();
            myUID.child("PhoneNumber").setValue(phoneNumber);
        }
    }

    // Do we want to allow people to remove their postal codes?
    private void updatePostalCode() {
        if (!postalCode_et.getText().toString().trim().equals(postalCode)) {
            postalCode = postalCode_et.getText().toString().trim();
            myUID.child("ZIPCode").setValue(postalCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMG_RESULT && resultCode == RESULT_OK && data != null && user != null) {
                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContext().getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String imageDecode = cursor.getString(columnIndex);
                cursor.close();

               /* photo_iv.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));*/
                // Toast.makeText(getActivity(), ImageDecode, Toast.LENGTH_LONG).show();

                Uri file = Uri.fromFile(new File(imageDecode));
                StorageReference riversRef = profilePictureRef.child(user.getUid());
                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                if (user != null) {
                                    myUID = myUser.child(user.getUid());

                                    myUID.child("ImageURL").setValue(downloadUrl.toString());
                                }

                                Toast.makeText(getActivity(), "Successfully uploaded!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Toast.makeText(getActivity(), "Failed to upload! Check app permissions!", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG).show();
        }
    }

    public void setAuthStateListener(View view) {
        // Initialize controls
        firstName_et = (TextView) view.findViewById(R.id.profile_first_name);
        lastName_et = (TextView) view.findViewById(R.id.profile_last_name);
        email_et = (TextView) view.findViewById(R.id.profile_email);
        phoneNumber_et = (TextView) view.findViewById(R.id.profile_phone_number);
        postalCode_et = (TextView) view.findViewById(R.id.profile_zip);
        photo_iv = (ImageView) view.findViewById(R.id.profile_photo);
        updatePhoto_ib = (ImageButton) view.findViewById(R.id.profile_update_photo);

        // Set onClick listener to Update Photo button
        updatePhoto_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMG_RESULT);
            }
        });

        // SET Auth State Listener
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (authRef.getCurrentUser() != null) {
                    setHasOptionsMenu(true);

                    email = authRef.getCurrentUser().getEmail();
                    //photoUrl = user.getPhotoUrl();
                    // TODO: Check if user's email is verified?
                    email_et.setText(email);
                    //if (photoUrl != null) { new DownloadImageTask(photo_iv).execute(photoUrl.toString()); }
                    firstName_et.setVisibility(View.VISIBLE);
                    lastName_et.setVisibility(View.VISIBLE);
                    email_et.setVisibility(View.VISIBLE);
                    phoneNumber_et.setVisibility(View.VISIBLE);
                    photo_iv.setVisibility(View.VISIBLE);
                    postalCode_et.setVisibility(View.VISIBLE);

                    updatePhoto_ib.setVisibility(View.VISIBLE);
                    /*updatePhoto_ib.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updatePhoto();
                        }
                    });*/

                    // SET Data Listener
                    myUID = myUser.child(authRef.getCurrentUser().getUid());
                    myUID.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String imgUrl = dataSnapshot.child("ImageURL").getValue(String.class);
                            if (!Helper.isNullOrEmpty(imgUrl)) {
                                setImage(getActivity(), imgUrl, photo_iv);
                            }

                            firstName = dataSnapshot.child("FirstName").getValue(String.class);
                            lastName = dataSnapshot.child("LastName").getValue(String.class);
                            phoneNumber = dataSnapshot.child("PhoneNumber").getValue(String.class);
                            postalCode = dataSnapshot.child("ZIPCode").getValue(String.class);

                            setProfileFields();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        };

        // ADD Auth State Listener
        authRef.addAuthStateListener(authListener);
    }
}
