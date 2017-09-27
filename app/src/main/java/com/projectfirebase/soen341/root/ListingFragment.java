package com.projectfirebase.soen341.root;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projectfirebase.soen341.root.Adapters.ListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListingFragment extends Fragment {
	private List<Listing> listingsList = new ArrayList<>();
	private RecyclerView recyclerView;
	private ListItemAdapter mAdapter;


	public ListingFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view =  inflater.inflate(R.layout.fragment_listing, container, false);

		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		mAdapter = new ListItemAdapter(listingsList);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(mAdapter);

		prepareListData();

		return view;
	}

	private void prepareListData() {
		Listing list = new Listing("Action & Adventure", 20);
		listingsList.add(list);

		list = new Listing("Animation", 2000);
		listingsList.add(list);

		list = new Listing("Science Fiction", 1985);
		listingsList.add(list);

		list = new Listing("Action & Adventure", 1981);
		listingsList.add(list);

		list = new Listing("Action & Adventure", 1965);
		listingsList.add(list);

		list = new Listing("Science Fiction & Fantasy", 2014);
		listingsList.add(list);

		mAdapter.notifyDataSetChanged();
	}

}
