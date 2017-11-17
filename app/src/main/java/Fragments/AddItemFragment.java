package Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projectfirebase.soen341.root.ItemDescription;
import com.projectfirebase.soen341.root.R;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddItemFragment extends Fragment {

    private DatabaseReference rootRef;
    private DatabaseReference databaseItems;
    private StorageReference mStorage;

    private EditText itemNameET;
    private EditText itemPriceET;
    private EditText itemDescriptionET;

    private LinearLayout selectImageCameraLayout;
    private LinearLayout selectImageGalleryLayout;

    private Button postItemButton;
    private boolean isItemPostable;

    private ProgressDialog mProgressDialog;

    private static final int CAMERA_REQUEST_CODE = 111;
    private static final int GALLERY_INTENT = 2;

    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static AddItemFragment newInstance() {
        return new AddItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_listing_item, container, false);

        rootRef = FirebaseDatabase.getInstance().getReference();
        databaseItems = rootRef.child("Items");

        mStorage = FirebaseStorage.getInstance().getReference();

        itemNameET = (EditText) view.findViewById(R.id.item_name);
        itemPriceET = (EditText) view.findViewById(R.id.item_price);
        itemDescriptionET = (EditText) view.findViewById(R.id.item_description);

        selectImageCameraLayout = (LinearLayout) view.findViewById(R.id.select_image_photo);
        selectImageGalleryLayout = (LinearLayout) view.findViewById(R.id.select_image_gallery);

        postItemButton = (Button) view.findViewById(R.id.post_item_button);
        mProgressDialog = new ProgressDialog(getContext());


        //CLICK LISTENERS
        selectImageCameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set camera click listener
            }
        });

        selectImageGalleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        postItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                isItemPostable = false;
                                dialog.cancel();
                            }
                        });

                if (isEmpty(itemNameET)) {
                    builder1.setMessage("Please enter a name for your item!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                } else if (isEmpty(itemPriceET)) {
                    builder1.setMessage("Please enter a price for your item!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                } else if (isEmpty(itemDescriptionET)) {
                    builder1.setMessage("Please enter a description for your item!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    isItemPostable = true;
                }

                if (isItemPostable) {
                    String itemName = itemNameET.getText().toString();
                    double itemPrice = Double.parseDouble(itemPriceET.getText().toString());
                    String itemDescription = itemDescriptionET.getText().toString();

                    String id = UUID.randomUUID().toString();
                    ItemDescription listingItem = new ItemDescription(id, itemName, itemPrice, "", itemDescription);

                    databaseItems.child(id).child("Price").setValue(itemPrice);
                    databaseItems.child(id).child("Description").setValue(itemDescription);
                    databaseItems.child(id).child("Name").setValue(itemName);
                    databaseItems.child(id).child("ImageURL").setValue("");
                    databaseItems.child(id).child("OwnerID").setValue("userUID");

                    Toast.makeText(getContext(), "Item Posted", Toast.LENGTH_LONG).show();

                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uploadImageToFirebase(data);

        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            //Camera logic
        }
    }

    public void uploadImageToFirebase(Intent data) {
        mProgressDialog.setMessage("Uploading ...");
        mProgressDialog.show();

        Uri uri = data.getData();
        StorageReference filePath = mStorage.child("Photos").child(uri.getLastPathSegment());

        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Photo Upload Done", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

}
