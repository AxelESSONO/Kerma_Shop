package com.obiangetfils.kermashop.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.fragments.childFragments.ProductDescription;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private final String ADD_TO_CART = "onAddToCartClicked";
    private final String ITEM_CARD = "onItemClicked";
    private final String FAV_BUTTON = "onFavButtonClicked";
    private final String REMOVE_BUTTON = "onRemoveButtonClicked";
    private final String VIEW_PRODUCT_BUTTON = "onViewProductButtonClicked";
    private final String OUT_OF_STOCK_BUTTON = "onOutOfStockButtonClicked";

    private MyViewHolder holder;

    private Context context;
    private Boolean isGridView;
    private Boolean isHorizontal;
    private String shortType;
    private List<ProductOBJ> productList;

    public ProductAdapter(Context context, List<ProductOBJ> productList, Boolean isHorizontal, String shortType) {
        this.context = context;
        this.productList = productList;
        this.isHorizontal = isHorizontal;
        this.shortType = shortType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;

        // Check which Layout will be Inflated
        if (isHorizontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_grid_sm, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(isGridView ? R.layout.item_product_grid_lg : R.layout.item_product_list_lg, parent, false);
        }

        // Return a new holder instance
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        this.holder = holder;
        // Get the data model based on Position

        final ProductOBJ product = productList.get(position);

        Glide.with(context)
                .load(product.getImagesProductsList().get(0).getImageUri())
                .into(holder.product_cover);
        holder.product_title.setText(product.getPname());

        if (product.getOldPrice().equals("0")){
            holder.product_price_strike.setVisibility(View.GONE);
        }else {
            holder.product_price_strike.setText(product.getOldPrice()+ " "+ context.getString(R.string.currency_xaf));
        }
        holder.currentPrice.setText(product.getCurrentPrice()+ " " + context.getString(R.string.currency_xaf));

        holder.product_like_btn.setOnCheckedChangeListener(null);

        holder.product_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.product_like_btn.isChecked()) {
                    holder.product_like_btn.setChecked(true);
                    Snackbar.make(view, "Produit ajouté aux favories", Snackbar.LENGTH_SHORT).show();
                } else {
                    holder.product_like_btn.setChecked(false);
                    Snackbar.make(view, "Produit retiré des favories", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProductDetails(product);
            }
        });

        holder.product_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProductDetails(product);
            }
        });

        holder.product_price_strike.setPaintFlags(holder.product_price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void toggleLayout(Boolean isGridView) {
        this.isGridView = isGridView;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView mainCard;
        ProgressBar cover_loader;
        Button product_add_cart_btn;
        ToggleButton product_like_btn;
        ImageView product_cover, product_checked, product_tag_new;
        TextView product_title, product_tag_sale, product_tag_featured, product_price_strike, currentPrice;


        public MyViewHolder(final View itemView) {
            super(itemView);

            mainCard = itemView.findViewById(R.id.mainCard);
            cover_loader = itemView.findViewById(R.id.product_cover_loader);
            product_checked = itemView.findViewById(R.id.product_checked);
            product_cover = itemView.findViewById(R.id.product_cover);
            product_tag_new = itemView.findViewById(R.id.product_tag_new);
            product_title = itemView.findViewById(R.id.product_title);
            product_price_strike = itemView.findViewById(R.id.product_price_strike);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            product_tag_sale = itemView.findViewById(R.id.product_tag_sale);
            product_tag_featured = itemView.findViewById(R.id.product_tag_featured);
            product_add_cart_btn = itemView.findViewById(R.id.product_card_Btn);
            product_like_btn = itemView.findViewById(R.id.product_like_btn);
        }
    }

    private void gotoProductDetails(ProductOBJ productOBJ) {

        Fragment fragment = new ProductDescription();
        Bundle bundle = new Bundle();

        bundle.putParcelable("PRODUCT_DETAIL_BUNDLE",  productOBJ);
        bundle.putParcelableArrayList("ALL_PRODUCT", (ArrayList<? extends Parcelable>) productList);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = ((BuyerHomeActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }
}

