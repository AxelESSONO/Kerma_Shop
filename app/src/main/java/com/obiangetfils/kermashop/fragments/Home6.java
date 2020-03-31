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
import com.obiangetfils.kermashop.fragments.childFragments.BannerSlider;


public class Home6 extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home6, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Kerma Shop");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);

        inflateBannerSlider();
        inflateShopFragments();
        inflateViewPager();

        return rootView;
    }

    private void inflateBannerSlider() {
        BannerSlider bannerSlider = new BannerSlider();
        getFragmentManager().beginTransaction().replace(R.id.frame_banner_slider, bannerSlider).commit();
    }


    private void inflateShopFragments(){
        Bundle newBundle = new Bundle();
        newBundle.putBoolean("isHeaderVisible", true);

        Bundle saleBundle = new Bundle();
        saleBundle.putBoolean("isHeaderVisible", true);

        Bundle featureBundle = new Bundle();
        featureBundle.putBoolean("isHeaderVisible", true);

        Fragment productsOnSale = new AllProductsHorizontal();
        Fragment productsFeatured = new AllProductsHorizontal();

        saleBundle.putString("shortType", "Sale");
        productsOnSale.setArguments(saleBundle);
        featureBundle.putString("shortType", "Featured");
        productsFeatured.setArguments(featureBundle);

        getFragmentManager().beginTransaction().replace(R.id.frame_onslae, productsOnSale).commit();
        getFragmentManager().beginTransaction().replace(R.id.frame_featured, productsFeatured).commit();

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