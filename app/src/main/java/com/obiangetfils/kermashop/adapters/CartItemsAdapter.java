package com.obiangetfils.kermashop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.fragments.childFragments.ProductDescription;
import com.obiangetfils.kermashop.models.CartOBJ;
import com.obiangetfils.kermashop.models.ImagesProducts;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.ArrayList;
import java.util.List;


public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder> {

    private final Context context;
    private final List<CartOBJ> cartItemsList;
    private int number = 1;
    private int totalQuantity;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private AlertDialog removeProductDialog;


    public CartItemsAdapter(Context context, List<CartOBJ> cartItemsList) {
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

        final ProductOBJ productOBJ = cartItemsList.get(position).getProductOBJ();

        int priceUnit = Integer.parseInt(productOBJ.getCurrentPrice());
        int quantity = Integer.parseInt(productOBJ.getQuantity());
        int subTotalPrice = priceUnit * quantity;

        myViewHolder.cart_item_title.setText(productOBJ.getPname());
        myViewHolder.cart_item_category.setText(productOBJ.getCategory());
        Glide.with(context).load(productOBJ.getImagesProductsList().get(0).getImageUri())
                .into(myViewHolder.imageProduct);
        myViewHolder.cart_item_base_price.setText("" + productOBJ.getCurrentPrice() + " FCFA");
        myViewHolder.cart_item_quantity.setText(productOBJ.getQuantity());
        myViewHolder.cart_item_sub_price.setText("" + subTotalPrice + " FCFA");

        // Decrease Product Quantity
        myViewHolder.cart_item_quantity_minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                initDatabase();
                databaseReference.child("CartList").child(currentUser.getUid())
                        .child("ProductCart").child(productOBJ.getPid())
                        .child("productOBJ").child("quantity").addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        number = Integer.parseInt(snapshot.getValue(String.class));
                        number--;
                        if (number > 0) {

                            databaseReference.child("CartList").child(currentUser.getUid())
                                    .child("ProductCart").child(productOBJ.getPid())
                                    .child("productOBJ").child("quantity").setValue("" + number);
                            myViewHolder.cart_item_quantity.setText("" + number);

                            productOBJ.setQuantity("" + number);
                            notifyDataSetChanged();
                        } else {

                            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                            View dialogView = LayoutInflater.from(context).inflate(R.layout.cart_popup, null);
                            dialog.setView(dialogView);
                            dialog.setCancelable(false);

                            // Widgets
                            ImageView imagePopup = (ImageView) dialogView.findViewById(R.id.imagePopup);
                            Button yesBtn = (Button) dialogView.findViewById(R.id.yesBtn);
                            Button noBtn = (Button) dialogView.findViewById(R.id.noBtn);

                            Glide.with(context).load(productOBJ.getImagesProductsList().get(0).getImageUri())
                                    .into(imagePopup);

                            removeProductDialog = dialog.create();

                            noBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    myViewHolder.cart_item_quantity.setText("1");

                                    initDatabase();
                                    databaseReference.child("CartList").child(currentUser.getUid())
                                            .child("ProductCart").child(productOBJ.getPid())
                                            .child("productOBJ").child("quantity").setValue("1");
                                    notifyDataSetChanged();
                                    removeProductDialog.dismiss();
                                }
                            });

                            yesBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    removeProductFromCart(productOBJ, view);
                                    notifyDataSetChanged();
                                    removeProductDialog.dismiss();
                                }
                            });
                            removeProductDialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        // Increase Product Quantity
        myViewHolder.cart_item_quantity_plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //initDatabase();
                updateCart(productOBJ, myViewHolder);
            }
        });

        // View Button
        myViewHolder.cart_item_viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatabase();
                setSimilarProduct(productOBJ);
            }
        });

        // Delete relevant Product from Cart
        myViewHolder.cart_item_removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                initDatabase();
                removeProductFromCart(productOBJ, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final Button cart_item_viewBtn;
        private final Button cart_item_removeBtn;
        private final ImageView imageProduct;
        private final ImageButton cart_item_quantity_minusBtn;
        private final ImageButton cart_item_quantity_plusBtn;
        private final TextView cart_item_title;
        private final TextView cart_item_category;
        private final TextView cart_item_base_price;
        private final TextView cart_item_sub_price;
        private final TextView cart_item_quantity;

        public MyViewHolder(final View itemView) {
            super(itemView);

            cart_item_title = itemView.findViewById(R.id.cart_item_title);
            cart_item_base_price = itemView.findViewById(R.id.cart_item_base_price);
            cart_item_sub_price = itemView.findViewById(R.id.cart_item_sub_price);
            cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);
            cart_item_category = itemView.findViewById(R.id.cart_item_category);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            cart_item_viewBtn = itemView.findViewById(R.id.cart_item_viewBtn);
            cart_item_removeBtn = itemView.findViewById(R.id.cart_item_removeBtn);
            cart_item_quantity_plusBtn = itemView.findViewById(R.id.cart_item_quantity_plusBtn);
            cart_item_quantity_minusBtn = itemView.findViewById(R.id.cart_item_quantity_minusBtn);
        }
    }

    private void initDatabase() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void removeProductFromCart(ProductOBJ productOBJ, final View view) {
        databaseReference.child("CartList").child(currentUser.getUid())
                .child("ProductCart").child(productOBJ.getPid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                notifyDataSetChanged();
                com.chootdev.csnackbar.Snackbar.with(context, null).type(Type.SUCCESS)
                        .message("Produit retiré du panier")
                        .duration(Duration.SHORT).fillParent(true)
                        .textAlign(Align.CENTER).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                com.chootdev.csnackbar.Snackbar.with(context, null).type(Type.ERROR)
                        .message("Suppression échouée, peut-être un problème de connexion")
                        .duration(Duration.SHORT).fillParent(true)
                        .textAlign(Align.CENTER).show();
            }
        });
    }

    private void updateCart(final ProductOBJ productOBJ, final MyViewHolder myViewHolder) {

        initDatabase();
        databaseReference.child("Products").child(productOBJ.getPid()).child("quantity").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int quantity = Integer.parseInt(snapshot.getValue(String.class));
                if (Integer.parseInt(productOBJ.getQuantity()) < quantity) {
                    number = Integer.parseInt(productOBJ.getQuantity());
                    number++;
                    databaseReference.child("CartList").child(currentUser.getUid()).child("ProductCart").child(productOBJ.getPid())
                            .child("productOBJ").child("quantity").setValue("" + number);
                    myViewHolder.cart_item_quantity.setText("" + number);

                } else {

                    databaseReference.child("CartList").child(currentUser.getUid()).child("ProductCart").child(productOBJ.getPid())
                            .child("productOBJ").child("quantity").setValue("" + quantity);
                    myViewHolder.cart_item_quantity.setText("" + quantity);

                    com.chootdev.csnackbar.Snackbar.with(context, null)
                            .type(Type.WARNING)
                            .message("Quantité maximale disponible atteinte, vous ne pouvez plus ajouter cet article!")
                            .duration(Duration.SHORT)
                            .fillParent(true)
                            .textAlign(Align.CENTER)
                            .show();

                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                com.chootdev.csnackbar.Snackbar.with(context, null).type(Type.ERROR).message("Erreur, peut-être un problème de connexion")
                        .duration(Duration.SHORT)
                        .fillParent(true)
                        .textAlign(Align.CENTER)
                        .show();
            }
        });
    }

    private void setSimilarProduct(final ProductOBJ productOBJ) {

        // Instantiate List
        final List<String> productKeyList = new ArrayList<>();
        final List<ProductOBJ> productOBJArrayList = new ArrayList<>();
        final List<ImagesProducts>[] imagesProductsList = new List[]{new ArrayList<>()};

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (!productOBJ.getPid().equals(dataSnapshot.getKey())) {
                        productKeyList.add(dataSnapshot.getKey());
                    }
                }

                if (productKeyList.size() > 0) {

                    for (int i = 0; i < productKeyList.size(); i++) {
                        final String category, currentPrice, description, oldPrice, pid, pname, quantity;
                        final Boolean tagNew, tagOnSale;

                        imagesProductsList[0] = new ArrayList<>();
                        category = snapshot.child(productKeyList.get(i)).child("category").getValue(String.class);
                        if (category.equals(productOBJ.getCategory())) {

                            currentPrice = snapshot.child(productKeyList.get(i)).child("currentPrice").getValue(String.class);
                            description = snapshot.child(productKeyList.get(i)).child("description").getValue(String.class);
                            oldPrice = snapshot.child(productKeyList.get(i)).child("oldPrice").getValue(String.class);
                            pid = snapshot.child(productKeyList.get(i)).child("pid").getValue(String.class);
                            pname = snapshot.child(productKeyList.get(i)).child("pname").getValue(String.class);
                            quantity = snapshot.child(productKeyList.get(i)).child("quantity").getValue(String.class);
                            tagNew = snapshot.child(productKeyList.get(i)).child("tagNew").getValue(Boolean.class);
                            tagOnSale = snapshot.child(productKeyList.get(i)).child("tagOnSale").getValue(Boolean.class);

                            for (DataSnapshot imageData : snapshot.child(productKeyList.get(i)).child("ImagesProducts").getChildren()) {
                                String imageUrl = imageData.getValue(String.class);
                                imagesProductsList[0].add(new ImagesProducts(imageUrl));
                            }
                            productOBJArrayList.add(new ProductOBJ(imagesProductsList[0], category, currentPrice, description,
                                    oldPrice, pid, pname, quantity, tagNew, tagOnSale));
                        }
                    }

                    gotoProductDetails(productOBJ, productOBJArrayList);

                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.with(context, null).type(Type.ERROR)
                        .message(context.getResources().getString(R.string.impossible_upload))
                        .duration(Duration.SHORT)
                        .fillParent(true)
                        .textAlign(Align.CENTER)
                        .show();
            }
        });
    }

    private void gotoProductDetails(ProductOBJ productOBJ, List<ProductOBJ> productList) {

        Fragment fragment = new ProductDescription();
        Bundle bundle = new Bundle();

        bundle.putParcelable("PRODUCT_DETAIL_BUNDLE", productOBJ);
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