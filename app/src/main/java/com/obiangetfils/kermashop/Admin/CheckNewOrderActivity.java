package com.obiangetfils.kermashop.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class CheckNewOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_order);
    }
}
