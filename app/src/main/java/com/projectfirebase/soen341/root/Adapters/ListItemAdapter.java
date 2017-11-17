package com.projectfirebase.soen341.root.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectfirebase.soen341.root.Listing;
import com.projectfirebase.soen341.root.R;

import java.text.NumberFormat;
import java.util.List;

import static com.projectfirebase.soen341.root.Helper.setImage;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {
    private List<Listing> itemList;

    // View holder is what holds the views
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView name_tv;
        public TextView price_tv;
        public ImageView image_iv;
        public String id;

        public ViewHolder(View v) {
            super(v);
            name_tv = (TextView) v.findViewById(R.id.list_item_name);
            price_tv = (TextView) v.findViewById(R.id.list_item_price);
            image_iv = (ImageView) v.findViewById(R.id.list_item_photo);
            view = v;
        }
    }

    public ListItemAdapter(List<Listing> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Listing listItem = itemList.get(position);

        holder.id = listItem.getID();
        holder.name_tv.setText(listItem.getName());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String price = formatter.format(listItem.getPrice());
        holder.price_tv.setText(price);

        holder.view.setTag(listItem.getID());

        String imgUrl = listItem.getImageURL();
        setImage(holder.view, imgUrl, holder.image_iv);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public Listing getListItem(int pos) {
        return itemList.get(pos);
    }
}
