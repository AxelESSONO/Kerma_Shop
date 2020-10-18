package com.obiangetfils.kermashop.fragments.childFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.DetailProductItemAdapter;
import com.obiangetfils.kermashop.models.ImageProductBis;
import com.obiangetfils.kermashop.models.ImagesProducts;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DetailProductItem extends Fragment implements DetailProductItemAdapter.OnClickOnItemListener {

    private TextView productName;
    private ImageView imageProduct;
    private RecyclerView recyclerImageItem;
    private List<ImagesProducts> imagesProducts;
    private List<ImageProductBis> imageProductBisList;

    private String pName;
    private DetailProductItemAdapter detailProductItemAdapter;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;

    public DetailProductItem() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_product_item, container, false);

        // Get Data
        if (this.getArguments() != null) {
            imagesProducts =  this.getArguments().getParcelableArrayList("PRODUCT_IMAGE");

            imageProductBisList = new ArrayList<>();
            for (int i = 0; i < imagesProducts.size(); i++) {
                imageProductBisList.add(new ImageProductBis(imagesProducts, false));
            }
            pName = this.getArguments().getString("PNAME", "");
        }

        productName = rootView.findViewById(R.id.productName);
        imageProduct = rootView.findViewById(R.id.imageProduct);
        recyclerImageItem = rootView.findViewById(R.id.recyclerImageItem);

        int position = 0;
        productName.setText(pName);
        Glide.with(requireContext()).load(imagesProducts.get(position).getImageUri()).into(imageProduct);
        imageProductBisList.get(position).setProductClicked(true);

        detailProductItemAdapter = new DetailProductItemAdapter(getContext(), imageProductBisList, (DetailProductItemAdapter.OnClickOnItemListener) this);

        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());

        // Set LayoutManager on Recycler View
        recyclerImageItem.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager( getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerImageItem.setLayoutManager(HorizontalLayout);

        // Set adapter on recycler view
        recyclerImageItem.setAdapter(detailProductItemAdapter);

        return rootView;
    }

    @Override
    public void OnClickOnItem(boolean isClicked, int position) {
        for (int i = 0; i < imageProductBisList.size(); i++) {
            if (i == position) {
                imageProductBisList.get(i).setProductClicked(true);
            }
            else {
                imageProductBisList.get(i).setProductClicked(false);
            }
        }
        detailProductItemAdapter.notifyDataSetChanged();
        Glide.with(requireContext()).load(imagesProducts.get(position).getImageUri()).into(imageProduct);
    }
}