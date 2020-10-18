package com.obiangetfils.kermashop.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.fragments.childFragments.DetailProductItem;
import com.obiangetfils.kermashop.models.ImagesProducts;

import java.util.ArrayList;
import java.util.List;

public class PagerImageDetailAdapter extends PagerAdapter {

    private List<ImagesProducts> imagesProducts;
    private LayoutInflater layoutInflater;
    private Context context;
    private final String mName;


    public PagerImageDetailAdapter(String pName, List<ImagesProducts> imagesProducts, Context context) {
        this.imagesProducts = imagesProducts;
        this.context = context;
        this.mName = pName;
    }

    @Override
    public int getCount() {
        return imagesProducts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_item, container, false);

        ImageView imageView;

        imageView = view.findViewById(R.id.image);

   /*     Glide.with(context)
                .load(imagesProducts.get(position).getImageUri())
                .into(imageView);*/


        Glide.with(context)
                .asBitmap()
                .load(imagesProducts.get(position).getImageUri())
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        //Play with bitmap
                        super.setResource(resource);
                    }
                });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProductDetails(imagesProducts);
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void gotoProductDetails(List<ImagesProducts> imagesProducts) {

        Fragment fragment = new DetailProductItem();
        Bundle bundle = new Bundle();

        //bundle.putParcelable("PRODUCT_IMAGE", (Parcelable) imagesProducts);
        bundle.putParcelableArrayList("PRODUCT_IMAGE", (ArrayList<? extends Parcelable>) imagesProducts);
        bundle.putString("PNAME", mName);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = ((BuyerHomeActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

}
