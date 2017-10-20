package Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.projectfirebase.soen341.root.R;


import java.io.File;

import Tasks.DownloadImageTask;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    View view;

    // TODO:  Refactor to abide by name conventions
    private TextView name_et;
    private TextView email_et;
    private TextView phoneNumber_et;
    private TextView ZIP_et;
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
    String ImageDecode;
    private StorageReference storageRef = FirebaseStorage.getInstance("gs://projectfirebase-9323d.appspot.com").getReference();

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
        ZIP_et = (TextView) view.findViewById(R.id.profile_zip);
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
            //photoUrl = user.getPhotoUrl();

            /*if (name != null) {
                name_et.setText(name);
            }*/

            // TODO: Check if user's email is verified?
            email_et.setText(email);

           /* if (photoUrl != null) {
                new DownloadImageTask(photo_iv).execute(photoUrl.toString());
            }*/

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

        if (user != null) {
            DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myUser = myRootRef.child("Users");
            DatabaseReference myUID = myUser.child(user.getUid());

            myUID.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                            String imgUrl = dataSnapshot.child("ImageURL").getValue(String.class);
                    Toast.makeText(getActivity(), imgUrl, Toast.LENGTH_LONG)
                            .show();
                            new DownloadImageTask(photo_iv).execute(imgUrl);

                            String name = dataSnapshot.child("FirstName").getValue(String.class) + " " + dataSnapshot.child("LastName").getValue(String.class);
                            name_et.setText(name);

                            String phoneNbr = dataSnapshot.child("PhoneNumber").getValue(String.class);
                            phoneNumber_et.setText(phoneNbr);

                            String ZIP = dataSnapshot.child("ZIPCode").getValue(String.class);
                            ZIP_et.setText(ZIP);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
         }

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
        /*UserProfileChangeRequest updatePhoto = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectfirebase-9323d.appspot.com/o/test_profile_photo.jpg?alt=media&token=8653a2a4-37e4-4534-a9b0-3de3c54f14c2"))
                .build();

        user.updateProfile(updatePhoto).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("USER_UPDATE", "User profile photo updated.");
                }
            }
        });*/
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };


                Cursor cursor = getContext().getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();

                photo_iv.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));
                Toast.makeText(getActivity(), ImageDecode, Toast.LENGTH_LONG).show();



                Uri file = Uri.fromFile(new File(ImageDecode));
                StorageReference riversRef = storageRef.child("WORD HERE");

                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Toast.makeText(getActivity(), downloadUrl.toString(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
