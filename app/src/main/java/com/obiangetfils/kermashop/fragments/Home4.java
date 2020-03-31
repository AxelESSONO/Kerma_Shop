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
import com.obiangetfils.kermashop.fragments.childFragments.AllProductsHorizontal;
import com.obiangetfils.kermashop.fragments.Category;
import com.obiangetfils.kermashop.fragments.childFragments.BannerSlider;


public class Home4 extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home4, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Kerma Shop");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);

        inflateBannerSlider();
        inflateShopFragments();
        inflateRecentProductFragment();
        inflateCategory1();

        return rootView;
    }

    private void inflateBannerSlider() {
        BannerSlider bannerSlider = new BannerSlider();
        getChildFragmentManager().beginTransaction().replace(R.id.frame_banner_slider, bannerSlider).commit();
    }

    private void inflateShopFragments(){
        Bundle newBundle = new Bundle();
        newBundle.putBoolean("isHeaderVisible", true);

        Bundle saleBundle = new Bundle();
        saleBundle.putBoolean("isHeaderVisible", true);

        Bundle featureBundle = new Bundle();
        featureBundle.putBoolean("isHeaderVisible", true);

        Fragment productsNewest = new AllProductsHorizontal();
        Fragment productsOnSale = new AllProductsHorizontal();
        Fragment productsFeatured = new AllProductsHorizontal();

        newBundle.putString("shortType", "Newest");
        productsNewest.setArguments(newBundle);
        saleBundle.putString("shortType", "Sale");
        productsOnSale.setArguments(saleBundle);
        featureBundle.putString("shortType", "Featured");
        productsFeatured.setArguments(featureBundle);

        getFragmentManager().beginTransaction().replace(R.id.frame_newest, productsNewest).commit();
        getFragmentManager().beginTransaction().replace(R.id.frame_onslae, productsOnSale).commit();
        getFragmentManager().beginTransaction().replace(R.id.frame_featured, productsFeatured).commit();

    }

    private void inflateCategory1() {

        Fragment f = new Category();
        Bundle b = new Bundle();
        b.putInt("CategoryStyleNumber", 3);
        b.putBoolean("isHeaderVisible", true);
        b.putBoolean("isMenuItem", false);
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.frame_category1, f).commit();

    }

    private void inflateRecentProductFragment() {
       Fragment recentlyViewed = new AllProductsHorizontal();
        Bundle b = new Bundle();
        b.putBoolean("isHeaderVisible", true);
        b.putString("shortType", "Recent");
        recentlyViewed.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.recently_viewed_fragment, recentlyViewed).commit();
    }

}
