package com.obiangetfils.kermashop.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.obiangetfils.kermashop.Buyer.LoginActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.Story.AddStoryActivity;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class AdminHomeActivity extends AppCompatActivity {

    private CardView addNewProduct, maintainProduct, checkNewOrder, addCategory, admin_new_story;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addNewProduct = (CardView) findViewById(R.id.new_product_cardView);
        maintainProduct = (CardView) findViewById(R.id.maintain_product_cardView);
        checkNewOrder = (CardView) findViewById(R.id.check_new_order_cardView);
        addCategory = (CardView) findViewById(R.id.add_category_cardView);
        admin_new_story = (CardView) findViewById(R.id.admin_new_story_cardView);

        logoutBtn = (Button) findViewById(R.id.logout);


        // On click cardview
        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newProductIntent = new Intent(AdminHomeActivity.this, AddNewProductActivity.class);
                startActivity(newProductIntent);
            }
        });

        maintainProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent maintainProductIntent = new Intent(AdminHomeActivity.this, AdminMaintainProductsActivity.class);
                startActivity(maintainProductIntent);
            }
        });

        checkNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkOrderIntent = new Intent(AdminHomeActivity.this, AdminMaintainProductsActivity.class);
                startActivity(checkOrderIntent);
            }
        });

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(AdminHomeActivity.this, AddCategoryActivity.class);
                startActivity(categoryIntent);
            }
        });

        admin_new_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(AdminHomeActivity.this, AddStoryActivity.class);
                startActivity(categoryIntent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
