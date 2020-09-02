package com.obiangetfils.kermashop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.CategoryAdapter;
import com.obiangetfils.kermashop.models.CategoryOBJ;
import java.util.ArrayList;
import java.util.List;


public class Category extends Fragment {

    View rootView;

    Boolean isMenuItem = true;
    Boolean isHeaderVisible = false;
    TextView emptyText, headerText;
    RecyclerView category_recycler;

    int CATEGORY_STYLE_NUMBER = 0;
    int subCategoriesAT = -1;

    private static final String ALL_CATEGORY = "ALL CATEGORY";
    private static List<CategoryOBJ> allCategoriesList;
    private CategoryAdapter adapter = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        if (getArguments() != null)
        {

            if (getArguments().containsKey("isHeaderVisible"))
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible", false);

            if (getArguments().containsKey("isMenuItem"))
                isMenuItem = getArguments().getBoolean("isMenuItem", true);

            if (getArguments().containsKey("CategoryStyleNumber"))
                CATEGORY_STYLE_NUMBER = getArguments().getInt("CategoryStyleNumber");

            if (getArguments().containsKey("SubCategoryAt"))
                subCategoriesAT = getArguments().getInt("SubCategoryAt");
        }

        if (isMenuItem) {
            //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.categories));
        }

        emptyText = rootView.findViewById(R.id.empty_record_text);
        headerText = rootView.findViewById(R.id.categories_header);


        emptyText.setVisibility(View.GONE);
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.categories));
        }

        final CategoryAdapter.clickListener listener = new CategoryAdapter.clickListener() {
            @Override
            public void onitemClicked(boolean hasSubCat, int subCatAt) {

                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();

         /*       if (hasSubCat) {
                    Fragment f = new SubCategory();
                    Bundle b = new Bundle();
                    b.putInt("CategoryStyleNumber", CATEGORY_STYLE_NUMBER);
                    b.putInt("SubCategoryAt", subCatAt);
                    f.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment, f).addToBackStack(null).commit();
                } else {
                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }*/
            }
        };

          MyData myData = new MyData(getContext());
        
        fillList(rootView);

        return rootView;
    }

    private void fillList(final View rootView) {

        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference();
        categoryRef.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataCategory)
            {
                List<String> CatKey = new ArrayList<>();
                for (DataSnapshot datCat : dataCategory.getChildren()){
                    CatKey.add(datCat.getKey());
                }
                List<CategoryOBJ> categoriesList = new ArrayList<>();
                for (int i = 0; i < CatKey.size(); i++){
                    String adminId = dataCategory.child(CatKey.get(i)).child("adminId").getValue(String.class);
                    String image = dataCategory.child(CatKey.get(i)).child("image").getValue(String.class);
                    String name = dataCategory.child(CatKey.get(i)).child("name").getValue(String.class);
                    categoriesList.add(new CategoryOBJ(image, R.drawable.category_men_icon, name, 23));
                }
                bindRecyclerView(categoriesList, rootView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    private void bindRecyclerView(List<CategoryOBJ> categoriesList, View view) {

        category_recycler = view.findViewById(R.id.categories_recycler);
        NestedScrollView scroll_container = view.findViewById(R.id.scroll_container);
        scroll_container.setNestedScrollingEnabled(true);
        category_recycler.setNestedScrollingEnabled(false);

        switch (CATEGORY_STYLE_NUMBER) {
            case 0:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_5, myData.getDummyCategories(), false);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_5, categoriesList, false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;

            case 1:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_1, myData.getDummyCategories(), false);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_1, categoriesList, false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;
            case 2:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_2, myData.getDummyCategories(), false);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_2, categoriesList, false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case 3:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_3, myData.getDummyCategories(), true);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_3, categoriesList, true);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;
            case 4:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_4, myData.getDummyCategories(), true);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_4, categoriesList, true);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case 5:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_5, myData.getDummyCategories(), false);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_5, categoriesList, false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case 6:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_6, myData.getDummyCategories(), true);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_6, categoriesList, true);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;

            case 7:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_horizontal_3, myData.getDummyCategories(), false);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_horizontal_3, categoriesList, false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                break;

            case 8:
                //adapter = new CategoryAdapter(getContext(), R.layout.item_categories_3, myData.getDummyCategories(), true);
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_3, categoriesList, true);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                //category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                break;
        }
        if (adapter != null) {
            //adapter.setListener(listener);
        }
    }
}