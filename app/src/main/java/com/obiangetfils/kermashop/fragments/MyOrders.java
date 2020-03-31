package com.obiangetfils.kermashop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.fragments.childFragments.OrderDetails;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment {

    View rootView;
    String customerID;

    TextView emptyRecord;
    RecyclerView orders_recycler;

    public MyOrders() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView =  inflater.inflate(R.layout.fragment_my_order, container, false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("MyOrders");
        ((BuyerHomeActivity) getActivity()).setDrawerEnabled(false);

        // Binding Layout Views
        emptyRecord = rootView.findViewById(R.id.empty_record);
        orders_recycler = rootView.findViewById(R.id.orders_recycler);

        // Hide some of the Views
        if (!MyData.getOrderedProducts().isEmpty()) {
            emptyRecord.setVisibility(View.GONE);
        }

        OrdersListAdapter ordersListAdapter = new OrdersListAdapter(getContext(), MyData.getOrderedProducts());
        orders_recycler.setAdapter(ordersListAdapter);
        orders_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));



        return rootView;
    }

    public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder>{

        Context context;
        List<ProductOBJ> ordersList;

        public OrdersListAdapter(Context context, List<ProductOBJ> ordersList) {
            this.context = context;
            this.ordersList = ordersList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            // Get the data model based on Position
            final ProductOBJ orderDetails = ordersList.get(position);


            holder.order_id.setText(String.valueOf(orderDetails.getID()));
            holder.order_price.setText(orderDetails.getNewPrice());



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get Order Info
                    Fragment fragment = new OrderDetails();
                    Bundle bundle = new Bundle();
                    bundle.putInt("MyOrderPosition", position);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((BuyerHomeActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }
            });



        }

        @Override
        public int getItemCount() {
            return ordersList.size();
        }

        /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView order_id, order_product_count, order_status, order_price, order_date;


            public MyViewHolder(final View itemView) {
                super(itemView);

                order_id = (TextView) itemView.findViewById(R.id.order_id);
                order_product_count = (TextView) itemView.findViewById(R.id.order_products_count);
                order_status = (TextView) itemView.findViewById(R.id.order_status);
                order_price = (TextView) itemView.findViewById(R.id.order_price);
                order_date = (TextView) itemView.findViewById(R.id.order_date);

            }
        }
    }

}
