package com.obiangetfils.kermashop.fragments.childFragments;


import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.obiangetfils.kermashop.BuildConfig;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.Buyer.DisplayProductMatchingCategoryActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.PagerImageDetailAdapter;
import com.obiangetfils.kermashop.adapters.ProductAdapter;
import com.obiangetfils.kermashop.models.ImagesProducts;
import com.obiangetfils.kermashop.models.ProductOBJ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescription extends Fragment {

    View rootView;

    ProductOBJ productID;
    int sellerID;
    int selectedVariationID = 0;
    double productBasePrice = 0;
    double productFinalPrice = 0;

    Button productCartBtn, checkoutOrderBtn;
    Button store_btn;
    ImageView sliderImageView;
    ViewPager sliderLayout;
    ToggleButton product_like_btn;
    ColorRatingBar product_rating_bar;
    ListView attributes_list_view;
    TextView product_description_webView;
    ImageButton product_share_btn, product_quantity_plusBtn, product_quantity_minusBtn;
    TextView title, category, product_ratings_count, product_stock, product_tag_new;
    TextView product_tag_sale, product_tag_featured, product_item_quantity;
    TextView store_name, seller_name, product_price_new;
    LinearLayout product_reviews_ratings, product_attributes, simple_product, related_products, product_description,
            seller_div;
    LinearLayout sliderDotspanel;

    ProductAdapter productAdapter;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    private Context mContext;

    // Seller Info
    String sellerName, seller_email, seller_rating, seller_pic;

    //Seller info WC
    String sellerNameWC, sellerEmailWC, sellerIDWC;

    private List<ImagesProducts> imagesProducts;
    PagerImageDetailAdapter pagerImageDetailAdapter;
    private List<ProductOBJ> productList;

    private int dotsCount;
    private ImageView[] dots;

    RecyclerView recyclerViewSimilar;

    public ProductDescription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.childfragment_product_description, container, false);

        setHasOptionsMenu(true);
        ((BuyerHomeActivity) requireActivity()).setDrawerEnabled(false);
        Objects.requireNonNull(((BuyerHomeActivity) getActivity()).getSupportActionBar()).setTitle(R.string.detail_article);

        if (this.getArguments() != null) {
            productID = (ProductOBJ) this.getArguments().getParcelable("PRODUCT_DETAIL_BUNDLE");
            imagesProducts = new ArrayList<>();
            imagesProducts = productID.getImagesProductsList();
            productList = this.getArguments().getParcelableArrayList("ALL_PRODUCT");
        }

        mContext = getContext();

        // Binding Layout Views checkout_order_btn
        productCartBtn = rootView.findViewById(R.id.product_cart_btn);
        checkoutOrderBtn = rootView.findViewById(R.id.checkout_order_btn);
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
        sliderDotspanel = (LinearLayout) rootView.findViewById(R.id.SliderDots);
        recyclerViewSimilar = (RecyclerView) rootView.findViewById(R.id.recyclerViewSimilar);


        category.setVisibility(View.GONE);
        product_tag_new.setVisibility(View.GONE);
        product_tag_sale.setVisibility(View.GONE);
        product_tag_featured.setVisibility(View.GONE);
        related_products.setVisibility(View.VISIBLE);
        product_attributes.setVisibility(View.GONE);

        checkoutOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(mContext, DisplayProductMatchingCategoryActivity.class);
                mContext.startActivity(categoryIntent);
            }
        });

        setProductDetails(productID);
        setSimilarProduct();
        //inflateShopFragments();

        return rootView;
    }

    private void setSimilarProduct() {

        ArrayList<String> arrayList = new ArrayList<>();

        Collections.sort(arrayList);

        productAdapter = new ProductAdapter(getContext(), productList, false, "");
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewSimilar.setHasFixedSize(false);
        recyclerViewSimilar.setLayoutManager(gridLayoutManager);
        setRecyclerViewLayoutManager(true);
        recyclerViewSimilar.setAdapter(productAdapter);

    }

    public void setRecyclerViewLayoutManager(Boolean isGridView) {

        int scrollPosition = 0;

        // If a LayoutManager has already been set, get current Scroll Position
        if (recyclerViewSimilar.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerViewSimilar.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
        productAdapter.toggleLayout(isGridView);
        recyclerViewSimilar.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        recyclerViewSimilar.setAdapter(productAdapter);
        recyclerViewSimilar.scrollToPosition(scrollPosition);
    }


    private void inflateShopFragments() {

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
        //productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        product_price_new.setText(product.getCurrentPrice());

        // Setup the ImageSlider of Product Images
        pagerImageDetailAdapter = new PagerImageDetailAdapter(productID.getPname(), imagesProducts, getContext());
        setViewPager(pagerImageDetailAdapter, sliderLayout);

        // Set Product's Information
        title.setText("VAUGHN SUEDE SLIP-ON SNEAKER");
        product_description_webView.setText(getResources().getString(R.string.lorem_ipsum));

        //product_tag_new.setVisibility(product.isNewTag() ? View.VISIBLE : View.GONE);
        //product_tag_featured.setVisibility(product.isFeaturedTag() ? View.VISIBLE : View.GONE);
        //product_tag_sale.setVisibility(product.isSaleTag() ? View.VISIBLE : View.GONE);

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
                Toast.makeText(getContext(), "Lien partagé", Toast.LENGTH_SHORT).show();


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


    /*    if (product.isFavTag()) {
            product_like_btn.setChecked(true);
        } else {
            product_like_btn.setChecked(false);
        }*/


        // Handle Click event of product_like_btn Button
        product_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if the User has Checked the Like Button
                if (product_like_btn.isChecked()) {
                    product_like_btn.setChecked(true);
                    Snackbar.make(view, "Article ajouté aux favoris", Snackbar.LENGTH_SHORT).show();
                } else {
                    product_like_btn.setChecked(false);
                    Snackbar.make(view, "Article retiré des favoris", Snackbar.LENGTH_SHORT).show();
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



                Toast.makeText(getContext(), "Produit ajouté au panier", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setViewPager(final PagerImageDetailAdapter pagerImageDetailAdapter, final ViewPager sliderLayout) {

        sliderLayout.setAdapter(pagerImageDetailAdapter);
        sliderLayout.setClipToPadding(false);
        sliderLayout.setPadding(5, 5, 5, 2);
        sliderLayout.setPageMargin(5);

        dotsCount = pagerImageDetailAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));


        sliderLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 8000);

    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            // here you check the value of getActivity() and break up if needed
            if (getActivity() == null) {
                return;
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int itemCount = pagerImageDetailAdapter.getCount();
                        int currentItem = sliderLayout.getCurrentItem();

                        if (sliderLayout.getCurrentItem() < itemCount - 1) {
                            sliderLayout.setCurrentItem(currentItem + 1);
                        } else {
                            sliderLayout.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
}