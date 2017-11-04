package com.projectfirebase.soen341.root.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projectfirebase.soen341.root.Listing;
import com.projectfirebase.soen341.root.R;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Tasks.DownloadImageTask;

import static com.projectfirebase.soen341.root.Helper.setImage;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder>{
	private List<Listing> listingsList;
	private List<String> favList;
	private String favString;

	private DatabaseReference rootRef;
	private DatabaseReference usersRef;
	private DatabaseReference favRef;

	private FirebaseUser user;


	// View holder is what holds the views
	public class ViewHolder extends RecyclerView.ViewHolder{
		public View view;
		public TextView name;
		public TextView price;
		public ImageView image;
		public ToggleButton fav;
		public String id;

		public ViewHolder(View v) {
			super(v);
			name = (TextView) v.findViewById(R.id.list_item_name);
			price = (TextView) v.findViewById(R.id.list_item_price);
			image = (ImageView) v.findViewById(R.id.list_item_photo);
			fav = (ToggleButton) v.findViewById(R.id.list_item_fav);
			view = v;
		}
	}

	public ListItemAdapter(List<Listing> moviesList) {
		this.listingsList = moviesList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_list_row, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		rootRef = FirebaseDatabase.getInstance().getReference();
		usersRef = rootRef.child("Users");
		user = FirebaseAuth.getInstance().getCurrentUser();

		Listing listItem = listingsList.get(position);

		holder.id = listItem.getID();
		holder.name.setText(listItem.getName());
		holder.price.setText(String.valueOf(listItem.getPrice()));
		holder.view.setTag(listItem.getID());

		String imgUrl = listItem.getImageURL();
		setImage(holder.view, imgUrl, holder.image);

		final ViewHolder currentHolder = holder;
		favRef = usersRef.child(user.getUid());
		favRef.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				favString = dataSnapshot.child("Favorites").getValue(String.class);
				if(favString != null)
					favList = new LinkedList<String>(Arrays.asList(favString.split(";")));
				setFavToggle(currentHolder, favList);
			}
			@Override
			public void onCancelled(DatabaseError databaseError) {
			}
		});
	}

	@Override
	public int getItemCount() {
		return listingsList.size();
	}

	public Listing getListItem(int pos){ return listingsList.get(pos); }

	public void setFavToggle(final ViewHolder holder, final List<String> newFavList) {
		rootRef = FirebaseDatabase.getInstance().getReference();
		usersRef = rootRef.child("Users");
		user = FirebaseAuth.getInstance().getCurrentUser();
		if(newFavList.contains(holder.id)) {
			holder.fav.setChecked(true);
			holder.fav.setBackgroundResource(R.drawable.ic_action_favorite);
		}
		else {
			holder.fav.setChecked(false);
			holder.fav.setBackgroundResource(0);
		}

		if(user != null) {
			holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						holder.fav.setBackgroundResource(R.drawable.ic_action_favorite);
						usersRef.child(user.getUid()).child("Favorites").setValue(favString + ";" + holder.id.toString());
					} else {
						holder.fav.setBackgroundResource(0);
						newFavList.remove(newFavList.indexOf(holder.id.toString()));
						usersRef.child(user.getUid()).child("Favorites").setValue(android.text.TextUtils.join(";", newFavList));
					}

				}
			});
		}
	}
}
