package com.obiangetfils.kermashop.Sellers.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.obiangetfils.kermashop.Admin.AddNewProductActivity;
import com.obiangetfils.kermashop.Admin.AdminMaintainProductsActivity;
import com.obiangetfils.kermashop.Admin.CheckNewOrderActivity;
import com.obiangetfils.kermashop.Buyer.LoginActivity;
import com.obiangetfils.kermashop.Story.AddStoryActivity;
import com.obiangetfils.kermashop.R;

import io.paperdb.Paper;

public class HomeFragment extends Fragment {

    private CardView addStory, addNewProduct, maintainProduct, checkOrder, checkValidateNewProduct, deleteAccount;
    private Button logoutBtn;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        addStory = (CardView) root.findViewById(R.id.new_story_cardView);
        addNewProduct = (CardView) root.findViewById(R.id.new_product_cardView);
        maintainProduct = (CardView) root.findViewById(R.id.maintain_product_cardView);
        checkOrder = (CardView) root.findViewById(R.id.check_new_order_cardView);
        checkValidateNewProduct = (CardView) root.findViewById(R.id.check_validate_new_product);
        deleteAccount = (CardView) root.findViewById(R.id.delete_account);
        logoutBtn = (Button) root.findViewById(R.id.logout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        addStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getContext(), AddStoryActivity.class));
            }
        });

        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddNewProductActivity.class));
            }
        });

        maintainProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AdminMaintainProductsActivity.class));
            }
        });

        checkOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CheckNewOrderActivity.class));
            }
        });

        checkValidateNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), AddStoryActivity.class));
                Toast.makeText(getContext(), "Pas encore développé", Toast.LENGTH_SHORT).show();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), AddStoryActivity.class));
                Toast.makeText(getContext(), "Pas encore développé", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}