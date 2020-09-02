package com.obiangetfils.kermashop.fragments.childFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.List;
import java.util.Objects;


public class Checkout extends Fragment {

    View rootView;
    public static boolean CHECKOUT_CALL;
    WebView checkout_webView;
    OrderDetails orderDetails;
    LinearLayout main_checkout;

    String ORDER_ID;
    String ORDER_RECEIVED = "order-received";

    String tax;
    String selectedPaymentMethod, selectedPaymentTitle;
    double checkoutSubtotal, checkoutPointsDiscount, checkoutTax, checkoutShipping, checkoutShippingCost, checkoutDiscount, checkoutTotal = 0;

    Button checkout_paypal_btn;
    CardView card_details_layout;
    ProgressDialog progressDialog;
    NestedScrollView scroll_container;
    RecyclerView checkout_items_recycler;
    // RecyclerView checkout_coupons_recycler;
    Button checkout_coupon_btn, checkout_order_btn, checkout_cancel_btn;
    ImageButton edit_billing_Btn, edit_shipping_Btn, edit_shipping_method_Btn;
    EditText checkout_coupon_code, checkout_comments, checkout_card_number, checkout_card_cvv, checkout_card_expiry;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, point_discount_subTotal, checkout_total, demo_coupons_text;
    TextView billing_name, billing_street, billing_address, shipping_name, shipping_street, shipping_address, shipping_method, payment_method;

    String ONE_PAGE_CHECKOUT;

    // Variables for Pints
    String pointsEqual, pointsEqualPrice, totalEarnedPoints;
    double pointsTotal;
    int totalPointEarned;
    CardView pointsCardLayer, pointsCardView;
    TextView points, point_discount;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.childfragment_checkout, container, false);

        ((BuyerHomeActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(false);
        Objects.requireNonNull(((BuyerHomeActivity) getActivity()).getSupportActionBar()).setTitle("Address");

        checkout_order_btn = rootView.findViewById(R.id.checkout_order_btn);
        checkout_cancel_btn = rootView.findViewById(R.id.checkout_cancel_btn);

        edit_billing_Btn = rootView.findViewById(R.id.checkout_edit_billing);
        edit_shipping_Btn = rootView.findViewById(R.id.checkout_edit_shipping);
        edit_shipping_method_Btn = rootView.findViewById(R.id.checkout_edit_shipping_method);
        shipping_method = rootView.findViewById(R.id.shipping_method);
        payment_method = rootView.findViewById(R.id.payment_method);
        checkout_subtotal = rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = rootView.findViewById(R.id.checkout_discount);
        checkout_total = rootView.findViewById(R.id.checkout_total);
        shipping_name = rootView.findViewById(R.id.shipping_name);
        shipping_street = rootView.findViewById(R.id.shipping_street);
        shipping_address = rootView.findViewById(R.id.shipping_address);
        billing_name = rootView.findViewById(R.id.billing_name);
        billing_street = rootView.findViewById(R.id.billing_street);
        billing_address = rootView.findViewById(R.id.billing_address);
        demo_coupons_text = rootView.findViewById(R.id.demo_coupons_text);

        checkout_comments = rootView.findViewById(R.id.checkout_comments);
        checkout_items_recycler = rootView.findViewById(R.id.checkout_items_recycler);
        main_checkout = rootView.findViewById(R.id.main_checkout);

        card_details_layout = rootView.findViewById(R.id.card_details_layout);
        checkout_paypal_btn = rootView.findViewById(R.id.checkout_paypal_btn);
        checkout_card_number = rootView.findViewById(R.id.checkout_card_number);
        checkout_card_cvv = rootView.findViewById(R.id.checkout_card_cvv);
        checkout_card_expiry = rootView.findViewById(R.id.checkout_card_expiry);
        scroll_container = rootView.findViewById(R.id.scroll_container);


        checkout_order_btn.setEnabled(true);
        card_details_layout.setVisibility(View.GONE);
        checkout_paypal_btn.setVisibility(View.GONE);


        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_card_expiry.setKeyListener(null);


        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(getContext(), MyData.getCartProducts());

        checkout_items_recycler.setAdapter(cartItemsAdapter);
        checkout_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartItemsAdapter.notifyDataSetChanged();



        // Handle the Click event of edit_payment_method_Btn
        payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Payment Methods", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle the Click event of edit_billing_Btn Button
        edit_billing_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Edit Billing Address", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle the Click event of edit_shipping_Btn Button
        edit_shipping_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Edit Shipping Address", Toast.LENGTH_SHORT).show();
            }
        });


        // Handle the Click event of edit_shipping_method_Btn Button
        edit_shipping_method_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Shipping Methods", Toast.LENGTH_SHORT).show();
            }
        });


        // Handle the Click event of checkout_cancel_btn Button
        checkout_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cancel the Order and Navigate back to My_Cart Fragment
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack("MyCart", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });

        checkout_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // MyData.placeOrder();
                Toast.makeText(getContext(), "Order Placed", Toast.LENGTH_SHORT).show();

                Fragment fragment = new Thank_You();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        return rootView;
    }


    public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder> {
        private Context context;
        private List<ProductOBJ> cartItemsList;


        public CartItemsAdapter(Context context, List<ProductOBJ> cartItemsList) {
            this.context = context;
            this.cartItemsList = cartItemsList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            final MyViewHolder holder2 = myViewHolder;
            holder2.checkout_item_title.setText(cartItemsList.get(position).getTitle());
            holder2.cover_loader.setVisibility(View.GONE);
            holder2.cart_item_cover.setVisibility(View.VISIBLE);
            //holder2.cart_item_cover.setImageResource(cartItemsList.get(position).getImage());

            Glide.with(getContext()).load(cartItemsList.get(position).getImage()).into(holder2.cart_item_cover);
            holder2.checkout_item_price.setText(cartItemsList.get(position).getNewPrice());
        }

        @Override
        public int getItemCount() {
            return cartItemsList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView cart_item_cover;
            ProgressBar cover_loader;
            private RecyclerView attributes_recycler;
            private TextView checkout_item_title, checkout_item_quantity, checkout_item_price, checkout_item_price_final, checkout_item_category;

            public MyViewHolder(final View itemView) {
                super(itemView);

                cart_item_cover = itemView.findViewById(R.id.cart_item_cover);
                cover_loader = itemView.findViewById(R.id.product_cover_loader);
                checkout_item_title = itemView.findViewById(R.id.checkout_item_title);
                checkout_item_quantity = itemView.findViewById(R.id.checkout_item_quantity);
                checkout_item_price = itemView.findViewById(R.id.checkout_item_price);
                checkout_item_price_final = itemView.findViewById(R.id.checkout_item_price_final);
                checkout_item_category = itemView.findViewById(R.id.checkout_item_category);
                attributes_recycler = itemView.findViewById(R.id.order_item_attributes_recycler);
            }
        }
    }
}