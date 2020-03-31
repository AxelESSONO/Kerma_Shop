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
import com.obiangetfils.kermashop.fragments.Category;


public class Home5 extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home5, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Kerma Shop");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);

        inflateCategory2();

        return rootView;
    }

    private void inflateCategory2() {

        Fragment f = new Category();
        Bundle b = new Bundle();
        b.putInt("CategoryStyleNumber", 2);
        b.putBoolean("isHeaderVisible", true);
        b.putBoolean("isMenuItem", false);
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.frame_category2, f).commit();

    }
}