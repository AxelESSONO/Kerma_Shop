package com.obiangetfils.kermashop.Buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.obiangetfils.kermashop.Prevalent.Prevalent;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.UserOBJ;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;
import com.obiangetfils.kermashop.utills.KermaUtil.KermaAuth;

public class BuyerProfilActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_profil);
    }
}
