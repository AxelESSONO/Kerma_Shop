package com.obiangetfils.kermashop.fragments.childFragments;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.ProductAdapter;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProductsHorizontal extends Fragment {

    String customerID;
    Boolean isHeaderVisible;

    ProgressBar loadingProgress;
    TextView emptyRecord, headerText;
    RecyclerView newest_recycler;
    ImageView products_horizontal_header_icon;

    ProductAdapter productAdapter;
    List<ProductOBJ> newestProductList;

    String shortType = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.childfragment_products_horizontal, container, false);

        // Get the Boolean from Bundle Arguments
        isHeaderVisible = getArguments().getBoolean("isHeaderVisible");
        shortType = getArguments().getString("shortType", "");

        // Binding Layout Views
        emptyRecord = rootView.findViewById(R.id.empty_record_text);
        headerText = rootView.findViewById(R.id.products_horizontal_header);
        loadingProgress = rootView.findViewById(R.id.loading_progress);
        newest_recycler = rootView.findViewById(R.id.products_horizontal_recycler);
        products_horizontal_header_icon = rootView.findViewById(R.id.products_horizontal_header_icon);

        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);

        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
            products_horizontal_header_icon.setVisibility(View.GONE);
        } else {
            headerText.setVisibility(View.VISIBLE);
            headerText.setText(shortType);
            products_horizontal_header_icon.setVisibility(View.VISIBLE);
        }

        newestProductList = new ArrayList<>();


        productAdapter = new ProductAdapter(getContext(), newestProductList, true, shortType, "allProductHorizontal");
        newest_recycler.setAdapter(productAdapter);
        newest_recycler.setHasFixedSize(true);
        newest_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        productAdapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             /*   switch (shortType){
                    case "Newest":
                        addProducts(MyData.getDummyNewstProducts());

                        Glide.with(getContext())
                                .load(R.drawable.ic_newest_icon)
                                .into(products_horizontal_header_icon);
                        break;
                    case "Sale":
                        addProducts(MyData.getDumySaleProducts());
                        Glide.with(getContext())
                                .load(R.drawable.ic_dollar_symbol_icon)
                                .into(products_horizontal_header_icon);
                        break;
                    case "Featured":
                        addProducts(MyData.getDummyFeaturedProducts());
                        Glide.with(getContext())
                                .load(R.drawable.ic_featured_icon)
                                .into(products_horizontal_header_icon);
                        break;
                    case "Recent":
                        addProducts(MyData.getDummyRecentProducts());
                        Glide.with(getContext())
                                .load(R.drawable.ic_list)
                                .into(products_horizontal_header_icon);
                        break;
                }*/
                loadingProgress.setVisibility(View.GONE);
            }
        }, 500);

        return rootView;
    }


    private void addProducts(List<ProductOBJ> productList) {

        // Add Products to mostLikedProductList
        if (productList.size() > 0) {
            newestProductList.addAll(productList);
        }

        productAdapter.notifyDataSetChanged();

        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        } else {
            emptyRecord.setVisibility(View.GONE);
        }

    }
}
