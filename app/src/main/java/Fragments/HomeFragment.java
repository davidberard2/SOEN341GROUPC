package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {
	//Listing RecyclerView instances
	//private View view;
	private ArrayList<Listing> listingsList = new ArrayList<>();
	private RecyclerView recyclerView;
	private ListItemAdapter mAdapter;

	// Fragment View
	private Context context;

	MenuItem fav;

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
	    setHasOptionsMenu(true);
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

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		MenuItem mSearchMenuItem = menu.findItem(R.id.action_search_query);
		SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
		searchView.setOnQueryTextListener(this);

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			    @Override
			    public boolean onQueryTextSubmit(String query) {
				    //LOIC the "query" string is what the user searches. Compare query to the elements in the database
				    //And refresh the list with ur answer HERE!!
				    Toast.makeText(getActivity(), query ,Toast.LENGTH_SHORT).show();
			        return false;
			    }

			    @Override
			    public boolean onQueryTextChange(String newText) {

			        return false;
			    }

			});

		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
//			case R.id.action_search_query:
//				// Not implemented here
//				Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
//				return true;
		}

		return false;
	}

	@Override
	public boolean onQueryTextChange(String query) {
		// Here is where we are going to implement the filter logic
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context=activity;
	}

}
