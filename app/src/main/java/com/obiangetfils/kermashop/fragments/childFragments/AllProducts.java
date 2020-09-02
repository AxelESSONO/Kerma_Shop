package com.obiangetfils.kermashop.fragments.childFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.ProductAdapter;

import java.util.Objects;

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

    ProductAdapter productAdapter;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        rootView = inflater.inflate(R.layout.childfragment_products_vertical, container, false);

        checkForAurguments();

        // Binding Layout Views
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

        // Hide some of the Views
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

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BuyerHomeActivity) Objects.requireNonNull(getActivity())).setRightDrawerEnable(true);
            }
        });

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());

        if (isCategoryFragmet) {
            productAdapter = new ProductAdapter(getContext(), MyData.getDummyProductsByCategory(categoryID), false, "");
        } else {
            productAdapter = new ProductAdapter(getContext(), isfavoriteApplied ? MyData.getDumyFavProducts() : MyData.getDummyProductList(), false, "");
        }
        setRecyclerViewLayoutManager(isGridView);
        all_products_recycler.setAdapter(productAdapter);

        if (isfavoriteApplied) {
            bottomBar.setVisibility(View.GONE);
            Objects.requireNonNull(((BuyerHomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("My Favorites");
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
