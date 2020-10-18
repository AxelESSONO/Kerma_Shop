package com.obiangetfils.kermashop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.fragments.childFragments.AllProducts;
import com.obiangetfils.kermashop.fragments.childFragments.AllProductsHorizontal;
import com.obiangetfils.kermashop.fragments.childFragments.BannerSlider;


public class Home7 extends Fragment {

    View rootView;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home7, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Kerma Shop");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(true);

        bindViews();
        inflateBannerSlider();
        inflateCategory7();
        setupViewPagerOne(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        inflateViewPager();

        return rootView;
    }

    private void bindViews() {
        viewPager = rootView.findViewById(R.id.myViewPager);
        tabLayout = rootView.findViewById(R.id.tabs);
    }

    private void inflateBannerSlider() {
        BannerSlider bannerSlider = new BannerSlider();
        getFragmentManager().beginTransaction().replace(R.id.frame_banner_slider, bannerSlider).commit();
    }

    private void inflateCategory7() {

        Fragment f = new Category();
        Bundle b = new Bundle();
        b.putInt("CategoryStyleNumber", 8);
        b.putBoolean("isHeaderVisible", true);
        b.putBoolean("isMenuItem", false);
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.frame_category7, f).commit();
    }

    private void setupViewPagerOne(ViewPager viewPager)
    {
        Bundle newBundle = new Bundle();
        newBundle.putBoolean("isHeaderVisible", false);

        Bundle saleBundle = new Bundle();
        saleBundle.putBoolean("isHeaderVisible", false);

        Bundle featureBundle = new Bundle();
        featureBundle.putBoolean("isHeaderVisible", false);

        Fragment productsNewest = new AllProductsHorizontal();
        Fragment productsOnSale = new AllProductsHorizontal();
        Fragment productsFeatured = new AllProductsHorizontal();

        newBundle.putString("shortType", "Newest");
        productsNewest.setArguments(newBundle);
        saleBundle.putString("shortType", "Sale");
        productsOnSale.setArguments(saleBundle);
        featureBundle.putString("shortType", "Featured");
        productsFeatured.setArguments(featureBundle);

        Home11.ViewPagerCustomAdapter viewPagerCustomAdapter = new Home11.ViewPagerCustomAdapter(getChildFragmentManager());

        viewPagerCustomAdapter.addFragment(productsNewest, getString(R.string.newest_product));
        viewPagerCustomAdapter.addFragment(productsOnSale, getString(R.string.super_deals_product));
        viewPagerCustomAdapter.addFragment(productsFeatured, getString(R.string.trend ));

        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(viewPagerCustomAdapter);
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