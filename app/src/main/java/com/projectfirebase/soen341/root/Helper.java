package com.projectfirebase.soen341.root;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Tasks.DownloadImageTask;

import static android.R.attr.fragment;

public class Helper {
    public static boolean isNullOrEmpty(String s){ return s == null || s.trim() == ""; }
    public static boolean isNullOrEmpty(Object[] o) { return o == null || o.length < 1; }

    public static void setImage(View view, String URL, ImageView imageView){
        if (view != null) {
            Glide.with(view)
                    .load(URL)
                    .into(imageView);
        }
        else {
            if (URL != null)
                new DownloadImageTask(imageView).execute(URL);
        }
    }
    public static void setImage(Activity activity, String URL, ImageView view){
        if (activity != null) {
            Glide.with(activity)
                    .load(URL)
                    .into(view);
        }
        else {
            if (URL != null)
                new DownloadImageTask(view).execute(URL);
        }
    }

    public static boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    public static String[] getCategoryArrayFromSnapshot(DataSnapshot dataSnapshot, String firstOption){
        List<Object> subCategoriesList = (ArrayList<Object>) dataSnapshot.getValue();

        Map<Integer, String> categories = new HashMap<>();
        for ( Object category : subCategoriesList ) {
            int index = subCategoriesList.indexOf(category);
            //itemMap is a single item, but still in json format.
            //From this object, extract wanted data to item, and add it to our list of items.
            if(category instanceof Map){
                Map<String, Object> categoryObj = (Map<String, Object>) category;

                String name = (String) categoryObj.get("Name");
                categories.put( index, name);
            }
        }

        String[] options;
        options = new String[categories.size() + 1];
        options[0] = firstOption;

        for(Integer key : categories.keySet()){
            String name = categories.get(key);

            options[key+1] = name;
        }

        return options;
    }
}

