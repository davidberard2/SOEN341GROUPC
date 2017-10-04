package com.projectfirebase.soen341.root;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projectfirebase.soen341.root.Adapters.ListItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Fragments.HomeFragment;
import Fragments.ProfileFragment;
import Fragments.SearchFragment;
import Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {
	//Listing RecyclerView instances
	private ArrayList<Listing> listingsList = new ArrayList<>();
	private RecyclerView recyclerView;
	private ListItemAdapter mAdapter;

// Status
    private TextView textViewStatus;
    private Button buttonGood;
    private Button buttonBad;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
	DatabaseReference itemsRef = rootRef.child("Items");


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mAdapter = new ListItemAdapter(listingsList);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.action_search:
                                selectedFragment = SearchFragment.newInstance();
                                break;
                            case R.id.action_profile:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                            case R.id.action_settings:
                                selectedFragment = SettingsFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}