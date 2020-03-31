package com.obiangetfils.kermashop.Buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.obiangetfils.kermashop.R;

public class DisplayProductMatchingCategoryActivity extends AppCompatActivity {

    String nameCategory, imageCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product_matching_category);


        nameCategory = getIntent().getStringExtra("categoryName");
        imageCategory = getIntent().getStringExtra("productCategoryImage");

    }
}
