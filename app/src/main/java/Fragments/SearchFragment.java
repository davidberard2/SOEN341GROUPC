package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projectfirebase.soen341.root.Helper;
import com.projectfirebase.soen341.root.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference categoriesRef = rootRef.child("Categories");
    DatabaseReference subCategoryRef;


    private EditText searchNameET;
    private EditText minPriceET;
    private EditText maxPriceET;

    private Spinner categorySpinner;
    private String[] categoryOptions;
    private int selectedCategory;
    private boolean justCreatedFlagC;

    private Spinner subCategorySpinner;
    private String[] subCategoryOptions;
    private int selectedSubCategory;
    private boolean justCreatedFlagSC;

    private Button searchButton;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    //Still need to comment code! Someone remind me if I forget when I submit a pull request!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchNameET = (EditText) view.findViewById(R.id.search_name);
        minPriceET = (EditText) view.findViewById(R.id.search_minprice);
        maxPriceET = (EditText) view.findViewById(R.id.search_maxprice);

        categorySpinner = (Spinner) view.findViewById(R.id.search_category);
        subCategorySpinner = (Spinner) view.findViewById(R.id.search_subCategory);

        searchButton = (Button) view.findViewById(R.id.search_submit);


        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryOptions = Helper.getCategoryArrayFromSnapshot(dataSnapshot, "Any...");

                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categoryOptions); //this, android.R.layout.simple_spinner_item, categoryOptions);

                categorySpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        justCreatedFlagC = true;
        selectedCategory = -1;
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int category, long id) {
                        if (justCreatedFlagC) {
                            justCreatedFlagC = false;
                            return;
                        } else {
                            selectedCategory = category - 1;

                            subCategoryRef = categoriesRef.child(Integer.toString(selectedCategory)).child("SubCategories");
                            subCategoryRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(selectedCategory >= 0) {
                                        subCategoryOptions = Helper.getCategoryArrayFromSnapshot(dataSnapshot, "Any...");

                                        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, subCategoryOptions); //this, android.R.layout.simple_spinner_item, categoryOptions);

                                        subCategorySpinner.setAdapter(adapter);
                                    } else {
                                        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.search_chooseCategory, android.R.layout.simple_spinner_item);
                                        subCategorySpinner.setAdapter(adapter);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                }
        );

        justCreatedFlagSC = true;
        selectedSubCategory = -1;
        subCategorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int subCategory, long l) {
                        if (justCreatedFlagSC) {
                            justCreatedFlagSC = false;
                            return;
                        } else {
                            selectedSubCategory = subCategory - 1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                }
        );


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringFilter = "";
                if (!Helper.isEmpty(searchNameET)) {
                    stringFilter = searchNameET.getText().toString();
                }

                double minPrice = 0;
                if (!Helper.isEmpty(minPriceET)) {
                    minPrice = Double.parseDouble(minPriceET.getText().toString());
                    minPrice = minPrice > 0 ? minPrice : 0;
                }

                double maxPrice = Double.MAX_VALUE;
                if (!Helper.isEmpty(maxPriceET)) {
                    maxPrice = Double.parseDouble(maxPriceET.getText().toString());
                    maxPrice = maxPrice > minPrice ? maxPrice : maxPrice;
                }

                int category = selectedCategory;
                int subCategory = selectedSubCategory;

                HomeFragment.itemFilter.setStringFilter(stringFilter);
                HomeFragment.itemFilter.setMinPrice(minPrice);
                HomeFragment.itemFilter.setMaxPrice(maxPrice);
                HomeFragment.itemFilter.setCategory(category);
                HomeFragment.itemFilter.setSubCategory(subCategory);

                submitSearchMethod();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void submitSearchMethod() {
        HomeFragment.applyAdvancedFilter = true;
        Fragment selectedFragment = HomeFragment.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
    }
}
