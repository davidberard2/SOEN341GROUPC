package com.projectfirebase.soen341.root;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projectfirebase.soen341.root.Adapters.ListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	//Listing RecyclerView instances
	private ArrayList<Listing> listingsList = new ArrayList<>();
	private RecyclerView recyclerView;
	private ListItemAdapter mAdapter;

// Status
    private TextView textViewStatus;
    private Button buttonGood;
    private Button buttonBad;

// Login
//    Button buttonLoginPage;
//
//    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference statusRef = rootRef.child("Status");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


	    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
	    mAdapter = new ListItemAdapter(listingsList);
	    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
	    recyclerView.setLayoutManager(mLayoutManager);
	    recyclerView.setAdapter(mAdapter);

	    prepareListData();

//        // Status
//        textViewStatus = (TextView)findViewById(R.id.textViewStatus);
//        buttonGood = (Button)findViewById(R.id.buttonGood);
//        buttonBad = (Button)findViewById(R.id.buttonBad);
//
//        // Login
//        buttonLoginPage = (Button)findViewById(R.id.buttonLoginPage);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //evansStuff();



    }

//    public void evansStuff() {
//	    // Status
//	    statusRef.addValueEventListener(new ValueEventListener() {
//		    @Override
//		    public void onDataChange(DataSnapshot dataSnapshot) {
//			    String text = dataSnapshot.getValue(String.class);
//			    textViewStatus.setText(text);
//		    }
//
//		    @Override
//		    public void onCancelled(DatabaseError databaseError) {
//
//		    }
//	    });
//
//	    buttonGood.setOnClickListener(new View.OnClickListener() {
//		    @Override
//		    public void onClick(View view) {
//			    statusRef.setValue("Good!");
//		    }
//	    });
//
//	    buttonBad.setOnClickListener(new View.OnClickListener() {
//		    @Override
//		    public void onClick(View view) {
//			    statusRef.setValue("Bad!");
//		    }
//	    });
//
//	    buttonLoginPage.setOnClickListener(new View.OnClickListener() {
//		    @Override
//		    public void onClick(View view) {
//			    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//		    }
//	    });
//    }


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
