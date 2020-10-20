package com.obiangetfils.kermashop.fragments.childFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.ProductAdapter;
import com.obiangetfils.kermashop.models.ImagesProducts;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.ArrayList;
import java.util.List;

public class FavoriteProducts extends Fragment {

    private RecyclerView favorite_recycler;
    private LinearLayout linear_favorite;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    ProductAdapter productAdapter;

    //List
    private List<ProductOBJ> productOBJList;
    private List<ImagesProducts> imagesProductsList;

    public FavoriteProducts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_products, container, false);

        favorite_recycler = (RecyclerView) view.findViewById(R.id.favorite_recycler);
        linear_favorite = (LinearLayout) view.findViewById(R.id.linear_favorite);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                List<String> userKeyList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userKey = dataSnapshot.getKey();
                    if (firebaseUser.getUid().equals(userKey)) {
                        userKeyList.add(dataSnapshot.getKey());
                    }
                }

                if (userKeyList.size() == 0) {
                    linear_favorite.setVisibility(View.VISIBLE);
                } else {

                    List<String> productKeyList = new ArrayList<>();
                    imagesProductsList = new ArrayList<>();
                    productOBJList = new ArrayList<>();

                    for (String userKeyItem : userKeyList) {
                        for (DataSnapshot dataSnapshot : snapshot.child(userKeyItem).getChildren()) {
                            String productKey = dataSnapshot.getKey();
                            productKeyList.add(productKey);

                            // Get Favorite data
                            final String category, currentPrice, description, oldPrice, pid, pname, quantity;
                            final Boolean tagNew, tagOnSale;

                            category = snapshot.child(userKeyItem).child(productKey).child("category").getValue(String.class);
                            currentPrice = snapshot.child(userKeyItem).child(productKey).child("currentPrice").getValue(String.class);
                            description = snapshot.child(userKeyItem).child(productKey).child("description").getValue(String.class);
                            oldPrice = snapshot.child(userKeyItem).child(productKey).child("oldPrice").getValue(String.class);
                            pid = snapshot.child(userKeyItem).child(productKey).child("pid").getValue(String.class);
                            pname = snapshot.child(userKeyItem).child(productKey).child("pname").getValue(String.class);
                            quantity = snapshot.child(userKeyItem).child(productKey).child("quantity").getValue(String.class);
                            tagNew = snapshot.child(userKeyItem).child(productKey).child("tagNew").getValue(Boolean.class);
                            tagOnSale = snapshot.child(userKeyItem).child(productKey).child("tagOnSale").getValue(Boolean.class);

                            for (DataSnapshot imageData : snapshot.child(userKeyItem).child(productKey).child("imagesProductsList").getChildren()) {

                                int imageKey = Integer.parseInt(imageData.getKey());
                                String imageUrl = snapshot.child(userKeyItem).child(productKey)
                                        .child("imagesProductsList").child(String.valueOf(imageKey))
                                        .child("imageUri").getValue(String.class);

                                imagesProductsList.add(new ImagesProducts(imageUrl));
                            }
                            productOBJList.add(new ProductOBJ(imagesProductsList, category, currentPrice, description,
                                    oldPrice, pid, pname, quantity, tagNew, tagOnSale));
                        }
                    }
                    gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    productAdapter = new ProductAdapter(getContext(), productOBJList, false, "", "favorite");
                    setRecyclerViewLayoutManager(true);
                    favorite_recycler.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Vérifiez votre connexion", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setRecyclerViewLayoutManager(boolean isGridView) {

        int scrollPosition = 0;

        // If a LayoutManager has already been set, get current Scroll Position
        if (favorite_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) favorite_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
        productAdapter.toggleLayout(isGridView);
        favorite_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        favorite_recycler.setAdapter(productAdapter);
        favorite_recycler.scrollToPosition(scrollPosition);
    }
}