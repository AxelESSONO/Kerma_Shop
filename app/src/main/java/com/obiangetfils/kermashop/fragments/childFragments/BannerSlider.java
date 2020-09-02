package com.obiangetfils.kermashop.fragments.childFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.BannerSliderAdapter;
import com.obiangetfils.kermashop.models.SliderItem;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * IMAGE SLIDER  PACKAGE
 **/
/** PACKAGE **/

public class BannerSlider extends Fragment {

    View rootView;
    SliderView sliderView;
    private BannerSliderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.childfragment_bannerslider, container, false);

        SliderView sliderView = rootView.findViewById(R.id.imageSlider);

        adapter = new BannerSliderAdapter(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.BLUE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        String uri[] = {"https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
                "https://firebasestorage.googleapis.com/v0/b/kerma-shop-76da2.appspot.com/o/Category%20Images%2FBeaut%C3%A9%20pour%20femmes.jpg?alt=media&token=10fe2044-4a03-42ff-8b84-6c2f928345d0",
                "https://firebasestorage.googleapis.com/v0/b/kerma-shop-76da2.appspot.com/o/Category%20Images%2Fcategory_men_backpack.png?alt=media&token=8940edeb-9695-4b42-a456-2b70e0ed044d",
                "https://firebasestorage.googleapis.com/v0/b/kerma-shop-76da2.appspot.com/o/Category%20Images%2Fcategory_men_jacket.png?alt=media&token=24613fe9-ac98-4676-9ff8-a85f94377027",
                "https://firebasestorage.googleapis.com/v0/b/kerma-shop-76da2.appspot.com/o/Category%20Images%2Fcategory_men_shoe.png?alt=media&token=af1974f2-1dd2-4ca4-8549-98aa4d0871bb"};

        for (int i = 0; i < uri.length; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setImageUrl(uri[i]);
            adapter.addItem(sliderItem);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
