package com.projectfirebase.soen341.root;

import android.support.v7.app.AppCompatActivity;

import com.projectfirebase.soen341.root.Adapters.ListItemAdapter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ListItemAdapterTest extends AppCompatActivity{

    Listing L1 = new Listing("1", "test1",1);
    Listing L2 = new Listing("2", "test2",2);
    List<Listing> LL = new ArrayList<Listing>();

    @Test
    public void ListingItemAdapterConstructorTest() {
        LL.clear();
        LL.add(L1);
        LL.add(L2);

        ListItemAdapter LIA = new ListItemAdapter(LL);
        assertEquals(LIA.getListItem(0), L1);
        assertEquals(LIA.getListItem(1), L2);
    }

    @Test
    public void getItemCount() throws Exception {
        LL.clear();
        LL.add(L1);
        LL.add(L2);

        ListItemAdapter LIA = new ListItemAdapter(LL);
        assertEquals(LIA.getItemCount(),2);
    }
}