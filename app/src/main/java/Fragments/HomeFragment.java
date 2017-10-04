package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projectfirebase.soen341.root.Adapters.ListItemAdapter;
import com.projectfirebase.soen341.root.Listing;
import com.projectfirebase.soen341.root.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
	//Listing RecyclerView instances
	private ArrayList<Listing> listingsList = new ArrayList<>();
	private RecyclerView recyclerView;
	private ListItemAdapter mAdapter;

	DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
	DatabaseReference itemsRef = rootRef.child("Items");

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

	    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
	    mAdapter = new ListItemAdapter(listingsList);
	    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
	    recyclerView.setLayoutManager(mLayoutManager);
	    recyclerView.setAdapter(mAdapter);

	    itemsRef.addValueEventListener(new ValueEventListener() {
		    @Override	//OnDataChange gets the full database every time something is changed inside of it.
		    public void onDataChange(DataSnapshot dataSnapshot) {
			    //clear the listingslist so we can add the items again (with changes)
			    listingsList.clear();
			    Map<String, Object> itemsMap = (HashMap<String, Object>) dataSnapshot.getValue();
			    for (Object itemMap : itemsMap.values()) {
				    if(itemMap instanceof Map){
					    Map<String, Object> itemObj = (Map<String, Object>) itemMap;
					    Listing item = new Listing();
					    item.setName((String) itemObj.get("Name"));
					    item.setPrice(((Number)itemObj.get("Price")).doubleValue());
					    listingsList.add(item);
				    }
			    }

			    //Once all the items are in the listingsList, notify the adapter that the dataset was changed
			    mAdapter.notifyDataSetChanged();
		    }
		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		    }
	    });

	    return view;
    }

}
