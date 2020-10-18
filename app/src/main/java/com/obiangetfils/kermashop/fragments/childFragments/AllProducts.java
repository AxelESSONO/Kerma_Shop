package com.obiangetfils.kermashop.fragments.childFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.ProductAdapter;
import com.obiangetfils.kermashop.models.ImagesProducts;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProducts extends Fragment {

    View rootView;

    int pageNo = 1;
    boolean isGridView;
    boolean isFilterApplied;
    boolean isSaleApplied = false;
    boolean isFeaturedApplied = false;
    boolean isfavoriteApplied = false;
    boolean isCategoryFragmet = false;

    String customerID;
    int categoryID;
    String order = "desc";
    String sortBy = "Newest";
    boolean isBottomBarVisible = true;

    LinearLayout bottomBar;
    LinearLayout sortList;
    TextView emptyRecord;
    TextView sortListText;
    ProgressBar progressBar;
    ProgressBar loadingProgress;
    ImageButton removeFilterBtn;
    ImageButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView all_products_recycler;

    //List
    private List<ProductOBJ> productOBJList;
    List<ImagesProducts> imagesProductsList;
    List<String> categoryList;
    List<String> productKeyList;

    ProductAdapter productAdapter;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    //Database
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.childfragment_products_vertical, container, false);

        checkForAurguments();

        // Binding Layout Views
        bindView(rootView);

        // Database instance
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Hide some of the Views
        hideSomeOfTheViews();

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BuyerHomeActivity) requireActivity()).setRightDrawerEnable(true);
            }
        });

        loadRecyclerView();


        if (isfavoriteApplied) {
            bottomBar.setVisibility(View.GONE);
            Objects.requireNonNull(((BuyerHomeActivity) requireActivity()).getSupportActionBar()).setTitle("My Favorites");
        }

        bottomBar.setVisibility(isBottomBarVisible ? View.VISIBLE : View.GONE);


        sortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get sortBy_array from String_Resources
                final String[] sortArray = getResources().getStringArray(R.array.sortBy_array);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);

                dialog.setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String selectedText = sortArray[which];
                        sortListText.setText(selectedText);

                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        toggleLayoutView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // Grid
                    isGridView = true;
                    setRecyclerViewLayoutManager(isGridView);
                } else { // List
                    isGridView = false;
                    setRecyclerViewLayoutManager(isGridView);
                }
            }
        });
        return rootView;
    }

    private void loadRecyclerView() {

        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Get  private List<ProductOBJ> productOBJList;
                // List<ImagesProducts> imagesProductsList;
                // List<String> categoryList;
                // List<String> productKeyList;

                categoryList = new ArrayList<>();
                productKeyList = new ArrayList<>();
                productOBJList = new ArrayList<>();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    productKeyList.add(dataSnapshot.getKey());
                }

                for (int i = 0; i < productKeyList.size(); i++) {
                    final String category, currentPrice, description, oldPrice, pid, pname, quantity;
                    final Boolean tagNew, tagOnSale;

                    category = snapshot.child(productKeyList.get(i)).child("category").getValue(String.class);
                    currentPrice = snapshot.child(productKeyList.get(i)).child("currentPrice").getValue(String.class);
                    description = snapshot.child(productKeyList.get(i)).child("description").getValue(String.class);
                    oldPrice = snapshot.child(productKeyList.get(i)).child("oldPrice").getValue(String.class);
                    pid = snapshot.child(productKeyList.get(i)).child("pid").getValue(String.class);
                    pname = snapshot.child(productKeyList.get(i)).child("pname").getValue(String.class);
                    quantity = snapshot.child(productKeyList.get(i)).child("quantity").getValue(String.class);
                    tagNew = snapshot.child(productKeyList.get(i)).child("tagNew").getValue(Boolean.class);
                    tagOnSale = snapshot.child(productKeyList.get(i)).child("tagOnSale").getValue(Boolean.class);

                    imagesProductsList = new ArrayList<>();
                    for (DataSnapshot imageData : snapshot.child(productKeyList.get(i)).child("ImagesProducts").getChildren()) {
                        String imageUrl = imageData.getValue(String.class);
                        imagesProductsList.add(new ImagesProducts(imageUrl));
                    }

                    productOBJList.add(new ProductOBJ(imagesProductsList, category, currentPrice, description,
                            oldPrice, pid, pname, quantity, tagNew, tagOnSale));
                }

                gridLayoutManager = new GridLayoutManager(getContext(), 2);
                linearLayoutManager = new LinearLayoutManager(getContext());

                if (isCategoryFragmet) {
                    productAdapter = new ProductAdapter(getContext(), productOBJList, false, "");
                } else {
                    productAdapter = new ProductAdapter(getContext(), productOBJList, false, "");
                }
                setRecyclerViewLayoutManager(isGridView);
                all_products_recycler.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void hideSomeOfTheViews() {
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);
        sortListText.setText(sortBy);
        isGridView = true;
        isFilterApplied = (isSaleApplied || isFeaturedApplied);
        toggleLayoutView.setChecked(isGridView);
        //filterButton.setChecked(isFilterApplied);
        removeFilterBtn.setVisibility(isFilterApplied ? View.VISIBLE : View.GONE);
        filterButton.setVisibility(View.VISIBLE);
    }

    private void bindView(View rootView) {

        bottomBar = rootView.findViewById(R.id.bottomBar);
        sortList = rootView.findViewById(R.id.sort_list);
        sortListText = rootView.findViewById(R.id.sort_text);
        emptyRecord = rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);
        loadingProgress = rootView.findViewById(R.id.loading_progress);
        removeFilterBtn = rootView.findViewById(R.id.removeFilterBtn);
        filterButton = rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = rootView.findViewById(R.id.layout_toggleBtn);
        all_products_recycler = rootView.findViewById(R.id.products_recycler);
    }

    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;

        // If a LayoutManager has already been set, get current Scroll Position
        if (all_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) all_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        productAdapter.toggleLayout(isGridView);
        all_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        all_products_recycler.setAdapter(productAdapter);
        all_products_recycler.scrollToPosition(scrollPosition);
    }

    private void checkForAurguments() {
        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy", "date");
            }
            if (getArguments().containsKey("on_sale")) {
                isSaleApplied = getArguments().getBoolean("on_sale", false);
            }
            if (getArguments().containsKey("featured")) {
                isFeaturedApplied = getArguments().getBoolean("featured", false);
            }
            if (getArguments().containsKey("favorites")) {
                isfavoriteApplied = getArguments().getBoolean("favorites", false);
            }
            if (getArguments().containsKey("CategoryID")) {
                isCategoryFragmet = true;
                categoryID = getArguments().getInt("CategoryID");
            }
            if (getArguments().containsKey("isBottomBarVisible")) {
                isBottomBarVisible = getArguments().getBoolean("isBottomBarVisible");
            }
        }
    }
}