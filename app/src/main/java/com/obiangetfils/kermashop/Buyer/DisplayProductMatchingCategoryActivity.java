package com.obiangetfils.kermashop.Buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.athbk.ultimatetablayout.OnClickTabListener;
import com.athbk.ultimatetablayout.UltimateTabLayout;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.FragmentAdapterDemo;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class DisplayProductMatchingCategoryActivity extends AppCompatActivity {

    UltimateTabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapterDemo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product_matching_category);

        tabLayout = (UltimateTabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);


        adapter = new FragmentAdapterDemo(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

//        options. if you override onClickTabListener.
        tabLayout.setOnClickTabListener(new OnClickTabListener() {
            @Override
            public void onClickTab(int currentPos) {

                viewPager.setCurrentItem(currentPos);
                if (currentPos == 1){
                    tabLayout.setNumberBadge(currentPos, 0);
                }
                else {
                    tabLayout.setNumberBadge(currentPos, 1);
                }
            }
        });

        tabLayout.setViewPager(viewPager, adapter);
    }
}
