package com.projectfirebase.soen341.root.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projectfirebase.soen341.root.Listing;
import com.projectfirebase.soen341.root.R;

import java.util.List;

import Tasks.DownloadImageTask;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder>{
	private List<Listing> listingsList;

	// View holder is what holds the views
	public class ViewHolder extends RecyclerView.ViewHolder{
		public View view;
		public TextView name;
		public TextView price;
		public ImageView image;
		public String id;

		public ViewHolder(View v) {
			super(v);
			name = (TextView) v.findViewById(R.id.list_item_name);
			price = (TextView) v.findViewById(R.id.list_item_price);
			image = (ImageView) v.findViewById(R.id.list_item_photo);
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
		Listing listItem = listingsList.get(position);

		holder.id = listItem.getID();
		holder.name.setText(listItem.getName());
		holder.price.setText(String.valueOf(listItem.getPrice()));
		holder.view.setTag(listItem.getID());

		String imgUrl = listItem.getImageURL();
		if (holder != null) {
			Glide.with(holder.view)
					.load(imgUrl)
					.into(holder.image);
		}
		else {
			if (imgUrl != null)
				new DownloadImageTask(holder.image).execute(listItem.getImageURL());
		}

	}

	@Override
	public int getItemCount() {
		return listingsList.size();
	}

	public Listing getListItem(int pos){ return listingsList.get(pos); }
}
