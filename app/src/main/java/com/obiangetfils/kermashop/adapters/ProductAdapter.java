package com.obiangetfils.kermashop.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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

        holder.product_checked.setVisibility(MyData.isCartContains(product.getID()) ? View.VISIBLE : View.GONE);
        Glide.with(context)
                .load(product.getImage())
                .into(holder.product_cover);
        holder.product_title.setText(product.getTitle());

        holder.product_tag_new.setVisibility(product.isNewTag() ? View.VISIBLE : View.GONE);
        holder.product_tag_sale.setVisibility(product.isSaleTag() ? View.VISIBLE : View.GONE);
        holder.product_tag_featured.setVisibility(product.isFeaturedTag() ? View.VISIBLE : View.GONE);

        holder.product_like_btn.setOnCheckedChangeListener(null);
        holder.product_like_btn.setChecked(product.isFavTag());

        holder.product_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.product_like_btn.isChecked()) {
                    holder.product_like_btn.setChecked(true);
                    MyData.addToFavProducts(product);
                    Snackbar.make(view, "Produits ajouté aux favories", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    holder.product_like_btn.setChecked(false);
                    MyData.removeFromFavProducts(product);
                    Snackbar.make(view, "Produits retiré des favories", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(position, product.getID(), ADD_TO_CART);
            }
        });

        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(position, product.getID(), ITEM_CARD);
            }
        });

        holder.product_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(position, product.getID(), ITEM_CARD);
            }
        });

        if (product.getDefaultStock() == 0){
            holder.product_add_cart_btn.setText("OUT OF STOCK");
            holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));
            holder.product_add_cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener(position, product.getID(), OUT_OF_STOCK_BUTTON);
                }
            });

        }

        if (product.getProductType().equals("Variable")) {
            holder.product_add_cart_btn.setText("View Product");
            holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_green));
            holder.product_add_cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener(position, product.getID(), VIEW_PRODUCT_BUTTON);
                }
            });

        }

        if (shortType.equals("Recent")){
            holder.product_add_cart_btn.setVisibility(View.VISIBLE);
            holder.product_add_cart_btn.setText("Remove");
            holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));
            holder.product_add_cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener(position, product.getID(), REMOVE_BUTTON);
                }
            });
        }

        holder.product_price_strike.setText(MyData.getDummyProductById(product.getID()).getOldPrice());
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
        TextView product_title, product_tag_sale, product_tag_featured, product_price_strike;


        public MyViewHolder(final View itemView) {
            super(itemView);

            mainCard = itemView.findViewById(R.id.mainCard);
            cover_loader = itemView.findViewById(R.id.product_cover_loader);
            product_checked = itemView.findViewById(R.id.product_checked);
            product_cover = itemView.findViewById(R.id.product_cover);
            product_tag_new = itemView.findViewById(R.id.product_tag_new);
            product_title = itemView.findViewById(R.id.product_title);
            product_price_strike = itemView.findViewById(R.id.product_price_strike);
            product_tag_sale = itemView.findViewById(R.id.product_tag_sale);
            product_tag_featured = itemView.findViewById(R.id.product_tag_featured);
            product_add_cart_btn = itemView.findViewById(R.id.product_card_Btn);
            product_like_btn = itemView.findViewById(R.id.product_like_btn);
        }

    }

    private void clickListener(int position, int productID, String clickType){
        switch (clickType) {
            case ADD_TO_CART:
                MyData.addToCartProduct(productList.get(position));
                holder.product_checked.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Produit ajouté au panier", Toast.LENGTH_SHORT).show();
                break;
            case ITEM_CARD:
                gotoProductDetails(productID);
                break;
            case FAV_BUTTON:
                Toast.makeText(context, "Fav Button", Toast.LENGTH_SHORT).show();
                break;
            case REMOVE_BUTTON:
                Toast.makeText(context, "Remove Button", Toast.LENGTH_SHORT).show();
                break;
            case VIEW_PRODUCT_BUTTON:
                gotoProductDetails(productID);
                break;
            case OUT_OF_STOCK_BUTTON:
                gotoProductDetails(productID);
                break;
        }
    }

    private void gotoProductDetails(int productID) {

        Fragment fragment = new ProductDescription();
        Bundle bundle = new Bundle();
        bundle.putInt("clickedProductId", productID);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = ((BuyerHomeActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

    }

}

