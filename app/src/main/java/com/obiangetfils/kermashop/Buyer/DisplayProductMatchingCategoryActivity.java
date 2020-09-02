package com.obiangetfils.kermashop.Buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class DisplayProductMatchingCategoryActivity extends AppCompatActivity {

    String nameCategory, imageCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product_matching_category);

        nameCategory = getIntent().getStringExtra("categoryName");
        imageCategory = getIntent().getStringExtra("productCategoryImage");

    }
}
