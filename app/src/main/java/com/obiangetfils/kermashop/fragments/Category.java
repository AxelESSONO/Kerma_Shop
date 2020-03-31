package com.obiangetfils.kermashop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.CategoryOBJ;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Category extends Fragment {

    View rootView;

    Boolean isMenuItem = true;
    Boolean isHeaderVisible = false;
    TextView emptyText, headerText;
    RecyclerView category_recycler;

    int CATEGORY_STYLE_NUMBER = 0;
    int subCategoriesAT = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_categories, container, false);


        if (getArguments() != null) {

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
        category_recycler = rootView.findViewById(R.id.categories_recycler);
        NestedScrollView scroll_container = rootView.findViewById(R.id.scroll_container);
        scroll_container.setNestedScrollingEnabled(true);
        category_recycler.setNestedScrollingEnabled(false);

        emptyText.setVisibility(View.GONE);
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.categories));
        }

        CategoryAdapter.clickListener listener = new CategoryAdapter.clickListener() {
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

        CategoryAdapter adapter = null;

        switch (CATEGORY_STYLE_NUMBER) {
            case 0:
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_5, MyData.getDummyCategories(), false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;

            case 1:
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_1, MyData.getDummyCategories(), false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;
            case 2:
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_2, MyData.getDummyCategories(), false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case 3:
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_3, MyData.getDummyCategories(), true);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;
            case 4:
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_4, MyData.getDummyCategories(), true);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case 5:
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_5, MyData.getDummyCategories(), false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case 6:
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_6, MyData.getDummyCategories(), true);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                break;

            case 7:
                adapter = new CategoryAdapter(getContext(),R.layout.item_categories_horizontal_3, MyData.getDummyCategories(), false);
                category_recycler.setAdapter(adapter);
                category_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                break;

            case 8:
                adapter = new CategoryAdapter(getContext(), R.layout.item_categories_3, MyData.getDummyCategories(), true);
                category_recycler.setAdapter(adapter);

                category_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                //category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                break;
        }
        if (adapter != null) {
            adapter.setListener(listener);
        }


        return rootView;
    }


    /**
     * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
     **/

    public static class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

        int layoutID;
        boolean isIconView;
        Context context;
        List<CategoryOBJ> categoriesList;
        clickListener listener;

        CategoryAdapter(Context context, int layoutID, List<CategoryOBJ> categoriesList, boolean isIconView) {
            this.context = context;
            this.layoutID = layoutID;
            this.isIconView = isIconView;
            this.categoriesList = categoriesList;
        }


        @Override
        public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final CategoryOBJ categoryDetails = categoriesList.get(position);
            if (isIconView) {
                Glide.with(context)
                        .load(categoriesList.get(position).getImage())
                        .into(holder.category_image);
            } else {
                Glide.with(context)
                        .load(categoriesList.get(position).getImage())
                        .into(holder.category_image);
            }

            holder.category_title.setText(categoryDetails.getName());
            holder.category_products.setText(categoriesList.get(position).getCount() + " " + context.getString(R.string.products));


            holder.category_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

        }

        void setListener(clickListener listener) {
            this.listener = listener;
        }

        @Override
        public int getItemCount() {
            return categoriesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout category_card;
            CircleImageView category_image;
            TextView category_title, category_products;

            public MyViewHolder(final View itemView) {
                super(itemView);
                category_card = itemView.findViewById(R.id.category_card);
                category_image = itemView.findViewById(R.id.category_image);
                category_title = itemView.findViewById(R.id.category_title);
                category_products = itemView.findViewById(R.id.category_products);

            }

        }

        public interface clickListener {
            void onitemClicked(boolean hasSubCat, int subCatAt);
        }
    }


}
