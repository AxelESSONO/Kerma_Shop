package com.obiangetfils.kermashop.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.DataSettings.MyData;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.CartItemsAdapter;
import com.obiangetfils.kermashop.adapters.ProductAdapter;
import com.obiangetfils.kermashop.fragments.childFragments.Product;
import com.obiangetfils.kermashop.fragments.childFragments.Shipping_Address;
import com.obiangetfils.kermashop.models.CartOBJ;
import com.obiangetfils.kermashop.models.ImagesProducts;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.ArrayList;
import java.util.List;

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
    TextView cart_subtotal, cart_discount, cart_total_price, demo_coupons_text, totalPrice;
    //ScratchTextView scratchTextView;

    AlertDialog demoCouponsDialog;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    //List
    private List<ProductOBJ> productOBJList;
    private List<ImagesProducts> imagesProductsList;
    private List<String> categoryList;
    private List<String> productKeyList;

    private ProductAdapter productAdapter;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private List<CartOBJ> cartItemsList;

    public MyCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_cart, container, false);

        ((BuyerHomeActivity) requireActivity()).setDrawerEnabled(false);

        // Set title bar
        ((BuyerHomeActivity) getActivity()).setActionBarTitle("Mon panier");

        setHasOptionsMenu(false);
        cartDiscount = 0.0;

        // Binding Layout Views cart_view_empty
        cart_prices = rootView.findViewById(R.id.cart_prices);
        cart_view = rootView.findViewById(R.id.cart_view);
        cart_view_empty = rootView.findViewById(R.id.cart_view_empty);
        cart_discount = rootView.findViewById(R.id.cart_discount);
        demo_coupons_text = rootView.findViewById(R.id.demo_coupons_text);
        cart_coupon_code = rootView.findViewById(R.id.cart_coupon_code);
        apply_coupon_btn = rootView.findViewById(R.id.cart_coupon_btn);
        cart_checkout_btn = rootView.findViewById(R.id.cart_checkout_btn);
        continue_shopping_btn = rootView.findViewById(R.id.continue_shopping_btn);
        cart_items_recycler = rootView.findViewById(R.id.cart_items_recycler);
        totalPrice = rootView.findViewById(R.id.totalPrice);

        cart_items_recycler.setNestedScrollingEnabled(false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        setRecyclerCart();

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

    private void setRecyclerCart() {
        databaseReference.child("CartList").child(firebaseUser.getUid())
                .child("ProductCart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                categoryList = new ArrayList<>();
                productKeyList = new ArrayList<>();
                productOBJList = new ArrayList<>();
                cartItemsList = new ArrayList<>();

                int overPrice = 0;

                // Get All Key in CartList
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    productKeyList.add(dataSnapshot.getKey());
                }

                for (int i = 0; i < productKeyList.size(); i++) {
                    final String category, currentPrice, description, oldPrice, pid, pname, quantity, date, time;
                    final Boolean tagNew, tagOnSale;

                    category = snapshot.child(productKeyList.get(i)).child("productOBJ").child("category").getValue(String.class);
                    currentPrice = snapshot.child(productKeyList.get(i)).child("productOBJ").child("currentPrice").getValue(String.class);
                    description = snapshot.child(productKeyList.get(i)).child("productOBJ").child("description").getValue(String.class);
                    oldPrice = snapshot.child(productKeyList.get(i)).child("productOBJ").child("oldPrice").getValue(String.class);
                    pid = snapshot.child(productKeyList.get(i)).child("productOBJ").child("pid").getValue(String.class);
                    pname = snapshot.child(productKeyList.get(i)).child("productOBJ").child("pname").getValue(String.class);
                    quantity = snapshot.child(productKeyList.get(i)).child("productOBJ").child("quantity").getValue(String.class);
                    tagNew = snapshot.child(productKeyList.get(i)).child("productOBJ").child("tagNew").getValue(Boolean.class);
                    tagOnSale = snapshot.child(productKeyList.get(i)).child("productOBJ").child("tagOnSale").getValue(Boolean.class);
                    date = snapshot.child(productKeyList.get(i)).child("date").getValue(String.class);
                    time = snapshot.child(productKeyList.get(i)).child("time").getValue(String.class);

                    imagesProductsList = new ArrayList<>();
                    for (DataSnapshot imageData : snapshot.child(productKeyList.get(i)).child("productOBJ").child("imagesProductsList")
                            .getChildren()) {
                        String imageUrlKey = imageData.getKey();
                        String image = snapshot.child(productKeyList.get(i)).child("productOBJ").child("imagesProductsList").child(imageUrlKey)
                                .child("imageUri").getValue(String.class);
                        imagesProductsList.add(new ImagesProducts(image));
                    }

                    ProductOBJ productOBJ = new ProductOBJ(imagesProductsList, category, currentPrice, description,
                            oldPrice, pid, pname, quantity, tagNew, tagOnSale);
                    cartItemsList.add(new CartOBJ(productOBJ, date, time));

                    int itemCount = Integer.parseInt(productOBJ.getQuantity());
                    int itemPrice = Integer.parseInt(productOBJ.getCurrentPrice());
                    overPrice = overPrice + (itemPrice * itemCount);

                }

                // Set total price
                totalPrice.setText("" + overPrice + " FCFA");

                // Check if the recents List isn't empty
                if (cartItemsList.size() < 1) {
                    cart_view.setVisibility(View.GONE);
                    cart_view_empty.setVisibility(View.VISIBLE);
                } else {
                    cart_view.setVisibility(View.VISIBLE);
                    cart_view_empty.setVisibility(View.GONE);
                    CartItemsAdapter adapter = new CartItemsAdapter(getContext(), cartItemsList);
                    cart_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    cart_items_recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
