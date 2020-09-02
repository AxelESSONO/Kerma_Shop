package com.obiangetfils.kermashop.fragments.childFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.customcompo.CustomViewPager;
import com.obiangetfils.kermashop.fragments.childFragments.AllProducts;
import com.obiangetfils.kermashop.models.CategoryOBJ;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Product extends Fragment {

    boolean isMenuItem = false;
    boolean isSubFragment = false;
    boolean isSaleApplied = false;
    boolean isFeaturedApplied = false;
    boolean isTabHasIcon = false;

    String sortBy = "Newest";

    int selectedTabIndex = 0;
    String selectedTabText = "";

    TabLayout product_tabs, defaultTab, iconTab;
    ViewPager product_viewpager;

    ViewPagerCustomAdapter viewPagerCustomAdapter;

    List<CategoryOBJ> allCategoriesList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_product, container, false);

        checkForAurguments();
        if (!isSubFragment) {
            if (isMenuItem) {
                ((BuyerHomeActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(true);
            } else {
                ((BuyerHomeActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(false);
            }

            Objects.requireNonNull(((BuyerHomeActivity) getActivity()).getSupportActionBar()).setTitle("Shop");
            ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);
        }


        // Binding Layout Views
        defaultTab = rootView.findViewById(R.id.product_tabs_default);
        iconTab = rootView.findViewById(R.id.product_tabs_with_icon);
        product_tabs =  (isTabHasIcon) ? iconTab : defaultTab;
        iconTab.setVisibility((isTabHasIcon) ? View.VISIBLE : View.GONE);
        product_viewpager = rootView.findViewById(R.id.product_viewpager);

        setupViewPagerAdapter();

        product_viewpager.setOffscreenPageLimit(0);
        product_viewpager.setAdapter(viewPagerCustomAdapter);

        product_tabs.setupWithViewPager(product_viewpager);

        if (isTabHasIcon) setupCustomTabs();
        try {
            product_tabs.getTabAt(selectedTabIndex).select();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        return rootView;
    }

    private void checkForAurguments() {
        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy", "Newest");
            }

            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", false);
            }

            if (getArguments().containsKey("isSubFragment")) {
                isSubFragment = getArguments().getBoolean("isSubFragment", false);
            }

            if (getArguments().containsKey("on_sale")) {
                isSaleApplied = getArguments().getBoolean("on_sale", false);
            }

            if (getArguments().containsKey("featured")) {
                isFeaturedApplied = getArguments().getBoolean("featured", false);
            }

            if (getArguments().containsKey("CategoryName")) {
                selectedTabText = getArguments().getString("CategoryName", "Category");
            }

            if (getArguments().containsKey("IsTabHasIcon")) {
                isTabHasIcon = getArguments().getBoolean("IsTabHasIcon");
            }
        }
    }

    private void setupViewPagerAdapter() {
        viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        Fragment allProducts = new AllProducts();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putBoolean("on_sale", isSaleApplied);
        bundleInfo.putBoolean("featured", isFeaturedApplied);
        bundleInfo.putString("sortBy", sortBy);
        bundleInfo.putBoolean("isBottomBarVisible", false);
        allProducts.setArguments(bundleInfo);

        viewPagerCustomAdapter.addFragment(allProducts, getContext().getString(R.string.all));

        allCategoriesList.addAll(MyData.getDummyCategories());

        for (int i=0;  i < allCategoriesList.size();  i++) {

            Fragment categoryProducts = new AllProducts();
            Bundle categoryInfo = new Bundle();
            categoryInfo.putBoolean("on_sale", isSaleApplied);
            categoryInfo.putBoolean("featured", isFeaturedApplied);
            categoryInfo.putInt("CategoryID", i);
            bundleInfo.putBoolean("isBottomBarVisible", true);
            categoryProducts.setArguments(categoryInfo);

            viewPagerCustomAdapter.addFragment(categoryProducts, allCategoriesList.get(i).getName());

            if (getArguments().containsKey("CategoryName")  && getArguments().containsKey("CategoryID")) {
                if (selectedTabText.equalsIgnoreCase(allCategoriesList.get(i).getName())
                        && getArguments().getInt("CategoryID", 0) == i) {
                    selectedTabIndex = i + 1;
                }
            }
        }
    }

    private void setupCustomTabs() {

        View tabOne = LayoutInflater.from(getContext()).inflate(R.layout.item_custom_tabs, null);
        TextView tabText1 = tabOne.findViewById(R.id.myTabs_text);
        tabText1.setText(getString(R.string.all));
        ImageView tabIcon1 = tabOne.findViewById(R.id.myTabs_icon);
        tabIcon1.setImageResource(R.drawable.ic_list_view);

        product_tabs.getTabAt(0).setCustomView(tabOne);

        for (int i=0;  i < allCategoriesList.size();  i++) {
            View tabNew = LayoutInflater.from(getContext()).inflate(R.layout.item_custom_tabs, null);
            TextView tabText2 = tabNew.findViewById(R.id.myTabs_text);
            tabText2.setText(allCategoriesList.get(i).getName());

            ImageView tabIcon2 = tabNew.findViewById(R.id.myTabs_icon);
            tabIcon2.setImageResource(allCategoriesList.get(i).getIcon());

            product_tabs.getTabAt(i+1).setCustomView(tabNew);
        }
    }

    /*Adapter Class*/
    public class ViewPagerCustomAdapter extends FragmentStatePagerAdapter {

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
