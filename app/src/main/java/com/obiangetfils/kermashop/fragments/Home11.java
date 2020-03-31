package com.obiangetfils.kermashop.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.customcompo.CustomViewPager;
import com.obiangetfils.kermashop.fragments.childFragments.AllProductsHorizontal;
import com.obiangetfils.kermashop.fragments.childFragments.Product;
import com.obiangetfils.kermashop.fragments.childFragments.BannerSlider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home11 extends Fragment {

    View rootView;

    ViewPager viewPager;
    TabLayout tabLayout;

    Fragment recentlyViewed, productsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Kerma Shop");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);

        bindViews();
        inflateBannerSlider();
        setupViewPagerOne(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        inflateRecentProductFragment();
        inflateMainProductFragment();

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

    private void setupViewPagerOne(ViewPager viewPager) {

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

        ViewPagerCustomAdapter viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        viewPagerCustomAdapter.addFragment(productsNewest, getString(R.string.newest));
        viewPagerCustomAdapter.addFragment(productsOnSale, getString(R.string.super_deals));
        viewPagerCustomAdapter.addFragment(productsFeatured, getString(R.string.featured));

        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(viewPagerCustomAdapter);
    }

    private void inflateRecentProductFragment() {
        recentlyViewed = new AllProductsHorizontal();
        Bundle b = new Bundle();
        b.putBoolean("isHeaderVisible", true);
        b.putString("shortType", "Recent");
        recentlyViewed.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.recently_viewed_fragment, recentlyViewed).commit();
    }

    private void inflateMainProductFragment() {

        productsFragment = new Product();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSubFragment", true);
        productsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.products_fragment, productsFragment).commit();
    }

    /*********View Pager Adapter********/
    public static class ViewPagerCustomAdapter extends FragmentStatePagerAdapter {

        private int mCurrentPosition = -1;
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerCustomAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);

            if (position != mCurrentPosition) {
                Fragment fragment = (Fragment) object;
                CustomViewPager pager = (CustomViewPager) container;

                if (fragment != null && fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.setCurrentView(fragment.getView());
                }
            }
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

    }

}
