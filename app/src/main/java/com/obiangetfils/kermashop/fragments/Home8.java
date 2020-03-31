package com.obiangetfils.kermashop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.fragments.childFragments.AllProducts;
import com.obiangetfils.kermashop.fragments.childFragments.AllProductsHorizontal;
import com.obiangetfils.kermashop.fragments.Category;
import com.obiangetfils.kermashop.fragments.childFragments.BannerSlider;

public class Home8 extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home8, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Kerma Shop");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);

        inflateBannerSlider();
        inflateCategory7();
        inflateShopFragments();
        inflateViewPager();

        return rootView;
    }


    private void inflateBannerSlider() {
        BannerSlider bannerSlider = new BannerSlider();
        getFragmentManager().beginTransaction().replace(R.id.frame_banner_slider, bannerSlider).commit();
    }

    private void inflateCategory7() {

        Fragment f = new Category();
        Bundle b = new Bundle();
        b.putInt("CategoryStyleNumber", 7);
        b.putBoolean("isHeaderVisible", true);
        b.putBoolean("IsHorizontal", true);
        b.putBoolean("isMenuItem", false);
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.frame_category7, f).commit();

    }

    private void inflateShopFragments(){
        Bundle newBundle = new Bundle();
        newBundle.putBoolean("isHeaderVisible", true);

        Fragment productsNewest = new AllProductsHorizontal();

        newBundle.putString("shortType", "Newest");
        productsNewest.setArguments(newBundle);

        getFragmentManager().beginTransaction().replace(R.id.frame_newest, productsNewest).commit();
    }


    private void inflateViewPager() {
        Fragment allProducts = new AllProducts();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putBoolean("on_sale", true);
        bundleInfo.putBoolean("featured", true);
        allProducts.setArguments(bundleInfo);
        getFragmentManager().beginTransaction().replace(R.id.frame_layout, allProducts).commit();
    }
}