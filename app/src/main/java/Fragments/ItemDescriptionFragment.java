package Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projectfirebase.soen341.root.Helper;
import com.projectfirebase.soen341.root.ItemDescription;
import com.projectfirebase.soen341.root.R;

import java.util.HashMap;
import java.util.Map;

import static com.projectfirebase.soen341.root.Helper.setImage;

public class ItemDescriptionFragment extends Fragment {
    public static String itemIDToDisplay;
    public ItemDescription itemToDisplay;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference itemsRef = rootRef.child("Items");

    private TextView name_tv, price_tv, description_tv;
    private ImageView photo_iv;

    public ItemDescriptionFragment() {
        // Required empty public constructor
    }


    public static Fragments.ItemDescriptionFragment newInstance() {
        Fragments.ItemDescriptionFragment fragment = new Fragments.ItemDescriptionFragment();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_description, container, false);

        //Get the views in case we ever want to do anything with them
        name_tv = (TextView) view.findViewById(R.id.item_name);
        price_tv = (TextView) view.findViewById(R.id.item_price);
        description_tv = (TextView) view.findViewById(R.id.item_description);
        photo_iv = (ImageView) view.findViewById(R.id.item_photo);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //make the id string final to make it accessible in the onDataChange listener

        this.populateItem();
    }

    public void populateItem() {
        final String id = getItemIDToDisplay();
        DatabaseReference item = itemsRef.child(id);
        itemToDisplay = new ItemDescription();

        item.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> itemsInDB = (HashMap<String, Object>) dataSnapshot.getValue();

                Map<String, Object> itemObj = (Map<String, Object>) itemsInDB;

                //get the data for the item to display
                String name = (String) itemObj.get("Name");
                String description = (String) itemObj.get("Description");
                String url = (String) itemObj.get("ImageURL");
                Double price = ((Number) itemObj.get("Price")).doubleValue();
                int category = ((Number)itemObj.get("Category")).intValue();
                int subCategory = ((Number)itemObj.get("SubCategory")).intValue();

                //set it
                itemToDisplay = new ItemDescription(id, name, price, url, description, category, subCategory);

                setDisplayViews();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setDisplayViews(){
        name_tv.setText(this.itemToDisplay.getName());
        price_tv.setText("$"+this.itemToDisplay.getPrice());
        if(Helper.isNullOrEmpty(this.itemToDisplay.getDescription())) {
            description_tv.setTypeface(null, Typeface.BOLD_ITALIC);
            description_tv.setText("No additional information available");
        }else{
            description_tv.setTypeface(null);
            description_tv.setText(this.itemToDisplay.getDescription());
        }

        String imgUrl = itemToDisplay.getImageURL();
        setImage(getActivity(), imgUrl, photo_iv);
    }

    public static void setItemIDToDisplay(String id){
        ItemDescriptionFragment.itemIDToDisplay = id;
    }

    public static String getItemIDToDisplay(){
        return ItemDescriptionFragment.itemIDToDisplay;
    }
}
