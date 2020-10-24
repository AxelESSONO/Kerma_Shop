package com.obiangetfils.kermashop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;

public class SearchFragment extends Fragment {

    View rootView;
   
    SearchView search_editText;
    FrameLayout banner_adView;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Rechercher un article");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);

        
        // Binding Layout Views
        banner_adView = (FrameLayout) rootView.findViewById(R.id.banner_adView);
        //search_editText = (SearchView) rootView.findViewById(R.id.search_editText);
    
        banner_adView.setVisibility(View.GONE);


        // Show Categories
        showCategories();

        return rootView;
    }

    //*********** Show Main Categories in the SearchList ********//

    private void showCategories() {

        Fragment f = new Category();
        Bundle b = new Bundle();
        b.putInt("CategoryStyleNumber", 5);
        b.putBoolean("isHeaderVisible", false);
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.categories_frame_layout, f).commit();
    }

}



