package com.obiangetfils.kermashop.fragments.childFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;

import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetails extends Fragment {

    View rootView;

    int orderID;
    OrderDetails orderDetails;

    Button cancel_order_btn;
    CardView trackingDiv;
    RecyclerView checkout_items_recycler, checkout_coupons_recycler;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, checkout_total;
    TextView billing_name, billing_street, billing_address, shipping_name, shipping_street, shipping_address;
    TextView order_price, order_products_count, order_status, order_date, shipping_method, payment_method;
    TextView tracking_number;

    SimpleDateFormat simpleDateFormat;
    int cancel_order_hours;
    String trackingNumber;
    AppCompatImageView copy_content_img;


    public OrderDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.childfragment_order_details, container, false);

        ((BuyerHomeActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(false);
        Objects.requireNonNull(((BuyerHomeActivity) getActivity()).getSupportActionBar()).setTitle("Order Details");

        // Binding Layout Views
        order_price = rootView.findViewById(R.id.order_price);
        order_products_count = rootView.findViewById(R.id.order_products_count);
        shipping_method = rootView.findViewById(R.id.shipping_method);
        payment_method = rootView.findViewById(R.id.payment_method);
        order_status = rootView.findViewById(R.id.order_status);
        order_date = rootView.findViewById(R.id.order_date);
        checkout_subtotal = rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = rootView.findViewById(R.id.checkout_discount);
        checkout_total = rootView.findViewById(R.id.checkout_total);
        billing_name =  rootView.findViewById(R.id.billing_name);
        billing_address = rootView.findViewById(R.id.billing_address);
        billing_street = rootView.findViewById(R.id.billing_street);
        shipping_name =  rootView.findViewById(R.id.shipping_name);
        shipping_address =  rootView.findViewById(R.id.shipping_address);
        shipping_street = rootView.findViewById(R.id.shipping_street);
        tracking_number =  rootView.findViewById(R.id.tracking_number);
        cancel_order_btn = rootView.findViewById(R.id.cancel_order_btn);
        copy_content_img = rootView.findViewById(R.id.copy_content_img);

        trackingDiv = rootView.findViewById(R.id.trackingDiv);
        checkout_items_recycler = rootView.findViewById(R.id.checkout_items_recycler);
        checkout_coupons_recycler = rootView.findViewById(R.id.checkout_coupons_recycler);

        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_coupons_recycler.setNestedScrollingEnabled(false);
        cancel_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cancel Order", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }

}
