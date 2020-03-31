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
import com.obiangetfils.kermashop.fragments.childFragments.Product;
import com.obiangetfils.kermashop.fragments.childFragments.BannerSlider;

public class Home2 extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home2, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Kerma Shop");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);

        inflateBannerSlider();
        inflateViewPager();

        return rootView;
    }



    private void inflateBannerSlider() {
        BannerSlider bannerSlider = new BannerSlider();
        getFragmentManager().beginTransaction().replace(R.id.frame_banner_slider, bannerSlider).commit();
    }

    private void inflateViewPager() {
        Fragment productsFragment = new Product();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSubFragment", true);
        bundle.putBoolean("IsTabHasIcon", true);
        productsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frame_viewpager, productsFragment).commit();
    }



}
