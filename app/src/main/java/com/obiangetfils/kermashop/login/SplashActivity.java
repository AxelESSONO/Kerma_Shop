package com.obiangetfils.kermashop.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.Intro.IntroScreen;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private Boolean isConnected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pref = getApplicationContext().getSharedPreferences("AUTHENTIFICATION", MODE_PRIVATE);
                isConnected = pref.getBoolean("IS_CONNECTED", false);

                if (isConnected) {
                    Intent mainActivityIntent = new Intent(SplashActivity.this, BuyerHomeActivity.class);
                    mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainActivityIntent);
                } else {
                    startActivity(new Intent(SplashActivity.this, IntroScreen.class));
                }
                finish();
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        // Do nothing.
    }
}
