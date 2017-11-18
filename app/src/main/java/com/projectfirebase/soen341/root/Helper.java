package com.projectfirebase.soen341.root;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
}

