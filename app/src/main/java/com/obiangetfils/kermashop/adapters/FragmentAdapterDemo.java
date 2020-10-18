package com.obiangetfils.kermashop.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.athbk.ultimatetablayout.IFTabAdapter;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.fragments.FragmentDemo;

public class FragmentAdapterDemo extends FragmentPagerAdapter implements IFTabAdapter {

    public FragmentAdapterDemo(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentDemo.newInstance();
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public String getTitle(int position) {
        return "Tableau " + position;
    }

    @Override
    public int getIcon(int position) {
        return 0;
    }

    @Override
    public boolean isEnableBadge(int position) {
        if (position == 0){
            return true;
        }
        return false;
    }
}
