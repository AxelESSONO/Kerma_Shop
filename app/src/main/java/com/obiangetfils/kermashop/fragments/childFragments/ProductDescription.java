package com.obiangetfils.kermashop.fragments.childFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.google.android.material.snackbar.Snackbar;
import com.obiangetfils.kermashop.BuildConfig;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.BannersOBJ;
import com.obiangetfils.kermashop.models.ProductOBJ;


import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescription extends Fragment {

    View rootView;

    int productID;
    int sellerID;
    int selectedVariationID = 0;
    double productBasePrice = 0;
    double productFinalPrice = 0;

    int customerID;
    String[] attributesNames;

    Button productCartBtn, store_btn;
    ImageView sliderImageView;
    SliderView sliderLayout;
    //PagerIndicator pagerIndicator;
    ToggleButton product_like_btn;
    ColorRatingBar product_rating_bar;
    ListView attributes_list_view;
    TextView product_description_webView;
    ImageButton product_share_btn, product_quantity_plusBtn, product_quantity_minusBtn;
    TextView title, category, product_ratings_count, product_stock, product_tag_new, product_tag_sale, product_tag_featured, product_item_quantity,
            store_name, seller_name, product_price_new;
    LinearLayout product_reviews_ratings, product_attributes, simple_product, related_products, product_description,
            seller_div;


    // Seller Info
    String sellerName, seller_email, seller_rating, seller_pic;

    //Seller info WC
    String sellerNameWC, sellerEmailWC, sellerIDWC;


    public ProductDescription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.childfragment_product_description, container, false);

        setHasOptionsMenu(true);
        ((BuyerHomeActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(false);
        Objects.requireNonNull(((BuyerHomeActivity) getActivity()).getSupportActionBar()).setTitle("Product Description");

        productID = this.getArguments().getInt("clickedProductId");

        // Binding Layout Views
        productCartBtn = rootView.findViewById(R.id.product_cart_btn);
        store_btn = rootView.findViewById(R.id.store_btn);
        title = rootView.findViewById(R.id.product_title);
        category = rootView.findViewById(R.id.product_category);
        product_stock = rootView.findViewById(R.id.product_stock);
        product_tag_new = rootView.findViewById(R.id.product_tag_new);
        product_tag_sale = rootView.findViewById(R.id.product_tag_sale);
        product_tag_featured = rootView.findViewById(R.id.product_tag_featured);
        product_item_quantity = rootView.findViewById(R.id.product_item_quantity);
        product_ratings_count = rootView.findViewById(R.id.product_ratings_count);
        store_name = rootView.findViewById(R.id.store_name);
        seller_name = rootView.findViewById(R.id.seller_name);
        product_like_btn = rootView.findViewById(R.id.product_like_btn);
        product_share_btn = rootView.findViewById(R.id.product_share_btn);
        product_quantity_plusBtn = rootView.findViewById(R.id.product_item_quantity_plusBtn);
        product_quantity_minusBtn = rootView.findViewById(R.id.product_item_quantity_minusBtn);
        related_products = rootView.findViewById(R.id.related_products);
        simple_product = rootView.findViewById(R.id.simple_product);
        product_attributes = rootView.findViewById(R.id.product_attributes);
        product_description = rootView.findViewById(R.id.product_description);
        product_reviews_ratings = rootView.findViewById(R.id.product_reviews_ratings);
        seller_div = rootView.findViewById(R.id.seller_div);
        product_rating_bar = rootView.findViewById(R.id.product_rating_bar);
        sliderLayout = rootView.findViewById(R.id.image_product_slider);
        product_price_new = rootView.findViewById(R.id.product_price_new);
        product_description_webView = rootView.findViewById(R.id.product_description_webView);
        attributes_list_view = rootView.findViewById(R.id.attributes_list_view);


        category.setVisibility(View.GONE);
        product_tag_new.setVisibility(View.GONE);
        product_tag_sale.setVisibility(View.GONE);
        product_tag_featured.setVisibility(View.GONE);
        related_products.setVisibility(View.VISIBLE);
        product_attributes.setVisibility(View.GONE);

        setProductDetails(Objects.requireNonNull(MyData.getDummyProductById(productID)));

        inflateShopFragments();

        return rootView;
    }


    private void inflateShopFragments(){

        Bundle saleBundle = new Bundle();
        saleBundle.putBoolean("isHeaderVisible", false);

        Fragment productsOnSale = new AllProductsHorizontal();

        saleBundle.putString("shortType", "Sale");
        productsOnSale.setArguments(saleBundle);

        getFragmentManager().beginTransaction().replace(R.id.frame_onslae, productsOnSale).commit();

    }

    private void setProductDetails(final ProductOBJ product) {


        simple_product.setVisibility(View.VISIBLE);
        product_attributes.setVisibility(View.GONE);

        simple_product.setVisibility(View.VISIBLE);
        product_stock.setText(getString(R.string.in_stock));
        product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
        productCartBtn.setText(getString(R.string.addToCart));
        productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        product_price_new.setText(product.getNewPrice());

        // Setup the ImageSlider of Product Images
        setupBannerSlider(MyData.getDummyDetailedProduct());

        // Set Product's Information
        title.setText("VAUGHN SUEDE SLIP-ON SNEAKER");
        product_description_webView.setText(getResources().getString(R.string.lorem_ipsum));

        product_tag_new.setVisibility(product.isNewTag() ? View.VISIBLE : View.GONE);
        product_tag_featured.setVisibility(product.isFeaturedTag() ? View.VISIBLE : View.GONE);
        product_tag_sale.setVisibility(product.isSaleTag() ? View.VISIBLE : View.GONE);

        // Holds Product Quantity
        final int[] number = {1};
        product_item_quantity.setText("1");

        // Decrease Product Quantity
        product_quantity_minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number[0] > 1) {
                    number[0] = number[0] - 1;
                    product_item_quantity.setText("" + number[0]);

                }
            }
        });


        // Increase Product Quantity
        product_quantity_plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number[0] = number[0] + 1;
                product_item_quantity.setText("" + number[0]);

            }
        });


        // Handle Click event of product_share_btn Button
        product_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Share Product with the help of static method of Helper class
                Toast.makeText(getContext(), "share", Toast.LENGTH_SHORT).show();


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


            }
        });


        if (product.isFavTag()) {
            product_like_btn.setChecked(true);
        } else {
            product_like_btn.setChecked(false);
        }


        // Handle Click event of product_like_btn Button
        product_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if the User has Checked the Like Button
                if (product_like_btn.isChecked()) {
                    product_like_btn.setChecked(true);
                    Snackbar.make(view, "Item Added to Favorites", Snackbar.LENGTH_SHORT).show();
                } else {
                    product_like_btn.setChecked(false);
                    Snackbar.make(view, "Item Removed from Favorites", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        // Handle Click event of product_reviews_ratings Button
        product_reviews_ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "showRatingdialog", Toast.LENGTH_SHORT).show();
            }
        });


        // Handle Click event of productCartBtn Button
        productCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Product Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupBannerSlider(final List<BannersOBJ> bannerImages) {
        final LinkedHashMap<String, Integer> slider_covers = new LinkedHashMap<>();
        for (int i = 0; i < bannerImages.size(); i++) {
            BannersOBJ bannerDetails = bannerImages.get(i);
            slider_covers.put
                    (
                            "Image" + bannerDetails.getBannerTitle(),
                            bannerDetails.getBannerImage()
                    );
        }

    /*    for (String name : slider_covers.keySet()) {
            final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
            defaultSliderView
                    .description(name)
                    .image(slider_covers.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(sliderClickListener);
            //sliderLayout.addSlider(defaultSliderView);
        }*/
        //sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
     /*   if (slider_covers.size() < 2) {
            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        } else {
            sliderLayout.setCustomIndicator(pagerIndicator);
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }*/


    }

/*    BaseSliderView.OnSliderClickListener sliderClickListener = new BaseSliderView.OnSliderClickListener() {
        @Override
        public void onSliderClick(BaseSliderView slider) {

        }
    };*/
}
