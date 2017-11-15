package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	//private View view;
	//I chose to use an unfilteredlist to base filters off of. This way, the database is only called when something is changed in the database.
	//Otherwise, every time the filter is changed, we would have to get the items from the database again.
	private ArrayList<Listing> unfilteredList = new ArrayList<>();
	private ArrayList<Listing> listingsList = new ArrayList<>();
	private RecyclerView recyclerView;
	private ListItemAdapter mAdapter;
	private boolean isViewFiltered;
	private String filterString;
	MenuItem loginLogout;
	// Fragment View
	private Context context;

	MenuItem login_logout;  //Commented out until we use it.

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
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
		menuInflater.inflate(R.menu.search_menu, menu);
		super.onCreateOptionsMenu(menu, menuInflater);
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
				unfilteredList.clear();
			    listingsList.clear();
				//itemsMap is a map of every item in the 'Items' database
			    Map<String, Object> itemsMap = (HashMap<String, Object>) dataSnapshot.getValue();

			    for ( String key : itemsMap.keySet() ) {
					Object itemMap = itemsMap.get(key);
					//itemMap is a single item, but still in json format.
					//From this object, extract wanted data to item, and add it to our list of items.
				    if(itemMap instanceof Map){
					    Map<String, Object> itemObj = (Map<String, Object>) itemMap;

						String id = key;
						String name = (String) itemObj.get("Name");
						Double price = ((Number)itemObj.get("Price")).doubleValue();
						String url = (String) itemObj.get("ImageURL");

					    Listing item = new Listing(key, name, price, url);

						//filter the item out of the display list if necessary
						if(!isViewFiltered || isViewFiltered && item.getName().toLowerCase().contains(filterString.toLowerCase())){
							listingsList.add(item);
						}
						unfilteredList.add(item);
				    }
			    }

			    //Me all the items are in the listingsList, notify the adapter that the dataset was changed
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

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			    @Override
			    public boolean onQueryTextSubmit(String query) {
					//Filters the listingsListdata set

					if (query != null && !query.isEmpty()) {
						isViewFiltered = true;
						filterString = query.toLowerCase();
					}
					listingsList.clear();
					//To filter, go through the unfiltered list and only add the wanted items to the list to listingslist, which is the displayed list
					for (Listing item : unfilteredList) {
						if (!isViewFiltered || isViewFiltered && item.getName().toLowerCase().contains(filterString)) {
							listingsList.add(item);
						}
					}
					mAdapter.notifyDataSetChanged();
					return false;
				}

			    @Override
				// Responsible for displaying all possible string from the list based on each additionnal character input made by user
			    public boolean onQueryTextChange(String newText) {

				    newText = newText.toLowerCase(); //eliminate possibility of uppercases

				    listingsList.clear();
				    for (Listing list : unfilteredList) {
					    final String text = list.getName().toLowerCase();
					    if (text.contains(newText)) { //adding all items that match the query string to the filtered arraylist
						    listingsList.add(list);
					    }
				    }

				    mAdapter.notifyDataSetChanged(); //notify the adapter that the dataset was changed
				    return true;
				}

			});

		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Fragment newFragment = new SettingsFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_layout, newFragment);
		transaction.commit();

		return true;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context=activity;
	}
}
