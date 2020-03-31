package com.obiangetfils.kermashop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.fragments.childFragments.Product;
import com.obiangetfils.kermashop.fragments.childFragments.ProductDescription;
import com.obiangetfils.kermashop.fragments.childFragments.Shipping_Address;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCart extends Fragment {

    View rootView;
    double cartSubTotal = 0;
    public static double cartDiscount;
    double cartTotalPrice = 0;
    boolean disableOtherCoupons = false;
    String customerID, customerToken, customerEmailAddress;

    EditText cart_coupon_code;
    LinearLayout cart_view, cart_view_empty, cart_prices;
    RecyclerView cart_items_recycler, cart_coupons_recycler;
    Button cart_checkout_btn, apply_coupon_btn, continue_shopping_btn;
    TextView cart_subtotal, cart_discount, cart_total_price, demo_coupons_text;
    //ScratchTextView scratchTextView;

    AlertDialog demoCouponsDialog;


    public MyCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_cart, container, false);

        ((BuyerHomeActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Cart");

        setHasOptionsMenu(false);
        cartDiscount = 0.0;

        // Binding Layout Views
        cart_prices = rootView.findViewById(R.id.cart_prices);
        cart_view = rootView.findViewById(R.id.cart_view);
        cart_view_empty = rootView.findViewById(R.id.cart_view_empty);
        cart_subtotal = rootView.findViewById(R.id.cart_subtotal);
        cart_discount = rootView.findViewById(R.id.cart_discount);
        cart_total_price = rootView.findViewById(R.id.cart_total_price);
        demo_coupons_text = rootView.findViewById(R.id.demo_coupons_text);
        cart_coupon_code = rootView.findViewById(R.id.cart_coupon_code);
        apply_coupon_btn = rootView.findViewById(R.id.cart_coupon_btn);
        cart_checkout_btn = rootView.findViewById(R.id.cart_checkout_btn);
        continue_shopping_btn = rootView.findViewById(R.id.continue_shopping_btn);
        cart_items_recycler = rootView.findViewById(R.id.cart_items_recycler);
        cart_coupons_recycler = rootView.findViewById(R.id.cart_coupons_recycler);

        cart_items_recycler.setNestedScrollingEnabled(false);
        cart_coupons_recycler.setNestedScrollingEnabled(false);

        // Check if the recents List isn't empty
        if (MyData.getCartProducts().size() < 1) {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        } else {
            cart_view.setVisibility(View.VISIBLE);
            cart_view_empty.setVisibility(View.GONE);

        }

        CartItemsAdapter adapter = new CartItemsAdapter(getContext(), MyData.getCartProducts());
        cart_items_recycler.setAdapter(adapter);
        cart_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();
        continue_shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("isSubFragment", false);

                // Navigate to Products Fragment
                Fragment fragment = new Product();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("My Cart").commit();

            }
        });

        // Handle Click event of cart_checkout_btn Button
        cart_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if cartItemsList isn't empty
                if (MyData.getCartProducts().size() != 0) {

                    Fragment fragment = new Shipping_Address();
                    Bundle b = new Bundle();
                    b.putString("shipping", "-");
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = ((BuyerHomeActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .addToBackStack(null)
                            .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }
            }
        });

        return rootView;
    }


    public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder> {

        private Context context;
        private List<ProductOBJ> cartItemsList;
        int number = 1;


        public CartItemsAdapter(Context context, List<ProductOBJ> cartItemsList) {
            this.context = context;
            this.cartItemsList = cartItemsList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);


            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

            myViewHolder.cart_item_cover.setImageResource(cartItemsList.get(position).getImage());
            myViewHolder.cover_loader.setVisibility(View.GONE);
            myViewHolder.cart_item_cover.setVisibility(View.VISIBLE);
            myViewHolder.cart_item_title.setText(cartItemsList.get(position).getTitle());
            myViewHolder.cart_item_category.setText(MyData.getDummyCategories().get(cartItemsList.get(position).getCategoryID()).getName());


            // Decrease Product Quantity
            myViewHolder.cart_item_quantity_minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (number > 1) {
                        number = number - 1;
                        myViewHolder.cart_item_quantity.setText("" + number);
                    }
                }
            });

            // Increase Product Quantity
            myViewHolder.cart_item_quantity_plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    number = number + 1;
                    myViewHolder.cart_item_quantity.setText("" + number);
                }
            });

            // View Product Details
            myViewHolder.cart_item_viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoProductDetails(MyData.getDummyProductList().get(position).getID());
                }
            });

            // Delete relevant Product from Cart
            myViewHolder.cart_item_removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

/*
                    myViewHolder.cart_item_removeBtn.setClickable(false);
                    cartItemsList.remove(position);
                    notifyItemRemoved(position);
*/

                }
            });
        }

        @Override
        public int getItemCount() {
            return cartItemsList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private Button cart_item_viewBtn;
            private ImageView cart_item_cover;
            private RecyclerView attributes_recycler;
            private ImageButton cart_item_quantity_minusBtn, cart_item_quantity_plusBtn;
            private Button cart_item_removeBtn;
            private TextView cart_item_title, cart_item_category, cart_item_base_price, cart_item_sub_price, cart_item_total_price, cart_item_quantity;
            ProgressBar cover_loader;

            public MyViewHolder(final View itemView) { super(itemView);

                cover_loader = itemView.findViewById(R.id.product_cover_loader);
                cart_item_title = itemView.findViewById(R.id.cart_item_title);
                cart_item_base_price = itemView.findViewById(R.id.cart_item_base_price);
                cart_item_sub_price = itemView.findViewById(R.id.cart_item_sub_price);
                cart_item_total_price = itemView.findViewById(R.id.cart_item_total_price);
                cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);
                cart_item_category = itemView.findViewById(R.id.cart_item_category);
                cart_item_cover = itemView.findViewById(R.id.cart_item_cover);
                cart_item_viewBtn = itemView.findViewById(R.id.cart_item_viewBtn);
                cart_item_removeBtn = itemView.findViewById(R.id.cart_item_removeBtn);
                cart_item_quantity_plusBtn = itemView.findViewById(R.id.cart_item_quantity_plusBtn);
                cart_item_quantity_minusBtn = itemView.findViewById(R.id.cart_item_quantity_minusBtn);

                attributes_recycler = itemView.findViewById(R.id.cart_item_attributes_recycler);


                cart_item_total_price.setVisibility(View.GONE);
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
}
