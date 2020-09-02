package com.obiangetfils.kermashop.Sellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.Sellers.ui.dashboard.DashboardFragment;
import com.obiangetfils.kermashop.Sellers.ui.home.HomeFragment;
import com.obiangetfils.kermashop.Sellers.ui.notifications.NotificationsFragment;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class SellerHomeActivity extends AppCompatActivity {

    private ImageView homePage;
    private TextView toolbarTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(new DashboardFragment());
                    return true;
                case R.id.navigation_notifications:
                    showFragment(new NotificationsFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_seller);
        homePage = (ImageView) findViewById(R.id.ret);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Accueil Vendeur");
        homePage.setImageResource(R.drawable.ic_home_24dp);

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerHomeActivity.this, BuyerHomeActivity.class));
            }
        });

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showFragment(new HomeFragment());
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}