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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projectfirebase.soen341.root.Helper;
import com.projectfirebase.soen341.root.ItemDescription;
import com.projectfirebase.soen341.root.R;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddItemFragment extends Fragment {
    View view;

    private DatabaseReference rootRef;
    private DatabaseReference databaseItems;
    private DatabaseReference categoriesRef;
    private DatabaseReference subCategoryRef;
    private StorageReference mStorage;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private EditText itemNameET;
    private EditText itemPriceET;
    private EditText itemDescriptionET;

    private LinearLayout selectImageCameraLayout;
    private LinearLayout selectImageGalleryLayout;

    private Spinner categorySpinner;
    private String[] categoryOptions;
    private int selectedCategory;
    private boolean justCreatedFlagC;


    private Spinner subCategorySpinner;
    private String[] subCategoryOptions;
    private int selectedSubCategory;
    private boolean justCreatedFlagSC;

    private Button postItemButton;
    private boolean isItemPostable;
    private String uploadURL;
    private final String uniqueItemID = UUID.randomUUID().toString();

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
        view = inflater.inflate(R.layout.fragment_add_listing_item, container, false);

        rootRef = FirebaseDatabase.getInstance().getReference();
        databaseItems = rootRef.child("Items");
        categoriesRef = rootRef.child("Categories");

        mStorage = FirebaseStorage.getInstance().getReference();

        itemNameET = (EditText) view.findViewById(R.id.item_name);
        itemPriceET = (EditText) view.findViewById(R.id.item_price);
        itemDescriptionET = (EditText) view.findViewById(R.id.item_description);

        selectImageCameraLayout = (LinearLayout) view.findViewById(R.id.select_image_photo);
        selectImageGalleryLayout = (LinearLayout) view.findViewById(R.id.select_image_gallery);

        postItemButton = (Button) view.findViewById(R.id.post_item_button);
        mProgressDialog = new ProgressDialog(getContext());

        categorySpinner = (Spinner) view.findViewById(R.id.add_category);
        subCategorySpinner = (Spinner) view.findViewById(R.id.add_subCategory);

        //DATABASE-DEPENDANT LISTENERS
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryOptions = Helper.getCategoryArrayFromSnapshot(dataSnapshot, "Choose a Category.");

                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categoryOptions); //this, android.R.layout.simple_spinner_item, categoryOptions);

                categorySpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        justCreatedFlagC = true;
        selectedCategory = -1;
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int category, long id) {
                        if(justCreatedFlagC){
                            justCreatedFlagC = false;
                            return;
                        } else {
                            selectedCategory = category - 1;

                            subCategoryRef = categoriesRef.child(Integer.toString(selectedCategory)).child("SubCategories");
                            subCategoryRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(selectedCategory >= 0) {
                                        subCategoryOptions = Helper.getCategoryArrayFromSnapshot(dataSnapshot, "Choose a Sub-Category.");

                                        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, subCategoryOptions); //this, android.R.layout.simple_spinner_item, categoryOptions);

                                        subCategorySpinner.setAdapter(adapter);
                                    } else {
                                        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.search_chooseCategory, android.R.layout.simple_spinner_item);
                                        subCategorySpinner.setAdapter(adapter);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                }
        );

        justCreatedFlagSC = true;
        selectedSubCategory = -1;
        subCategorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int subCategory, long l) {
                        if(justCreatedFlagSC){
                            justCreatedFlagSC = false;
                            return;
                        } else {
                            selectedSubCategory = subCategory - 1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                }
        );

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

                if (Helper.isEmpty(itemNameET)) {
                    builder1.setMessage("Please enter a name for your item!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                } else if (Helper.isEmpty(itemPriceET)) {
                    builder1.setMessage("Please enter a price for your item!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else if (Helper.isEmpty(itemDescriptionET)) {
                    builder1.setMessage("Please enter a description for your item!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else if(selectedCategory < 0){
                    builder1.setMessage("Please choose a category!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else if (selectedSubCategory < 0){
                    builder1.setMessage("Please choose a sub-category!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else if(Helper.isNullOrEmpty(uploadURL)){
                    builder1.setMessage("Please upload an image!");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else{
                    isItemPostable = true;
                }

                if (isItemPostable) {
                    String itemName = itemNameET.getText().toString();
                    double itemPrice = Double.parseDouble(itemPriceET.getText().toString());
                    String itemDescription = itemDescriptionET.getText().toString();

                    ItemDescription listingItem = new ItemDescription(uniqueItemID, user.getUid(), itemName, itemPrice, uploadURL, itemDescription, selectedCategory, selectedSubCategory);

                    databaseItems.child(uniqueItemID).setValue(listingItem);

//                    databaseItems.child(uniqueItemID).child("Price").setValue(itemPrice);
//                    databaseItems.child(uniqueItemID).child("Description").setValue(itemDescription);
//                    databaseItems.child(uniqueItemID).child("Name").setValue(itemName);
//                    databaseItems.child(uniqueItemID).child("ImageURL").setValue(uploadURL);
//                    databaseItems.child(uniqueItemID).child("OwnerID").setValue(user.getUid());
//                    databaseItems.child(uniqueItemID).child("Category").setValue(selectedCategory);
//                    databaseItems.child(uniqueItemID).child("SubCategory").setValue(selectedSubCategory);

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
        StorageReference filePath = mStorage.child("ItemPictures").child(uniqueItemID + "---" + uri.getLastPathSegment());

        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Photo Upload Done", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
                uploadURL = taskSnapshot.getDownloadUrl().toString();
            }
        });
    }
}
