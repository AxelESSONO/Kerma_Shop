package com.obiangetfils.kermashop.Delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obiangetfils.kermashop.Delivery.Fragment_delivery.DashboardDeliveryFragment;
import com.obiangetfils.kermashop.Delivery.Fragment_delivery.GainDeleveryFragment;
import com.obiangetfils.kermashop.Delivery.Fragment_delivery.NotificationsDeliveryFragment;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.Sellers.ui.dashboard.DashboardFragment;
import com.obiangetfils.kermashop.Sellers.ui.home.HomeFragment;
import com.obiangetfils.kermashop.Sellers.ui.notifications.NotificationsFragment;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class DeliveryManHomeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard_delivery:
                    showFragment(new DashboardDeliveryFragment());
                    return true;
                case R.id.navigation_notifications_delivery:
                    showFragment(new NotificationsDeliveryFragment());
                    return true;
                case R.id.navigation_home_delivery:
                    showFragment(new GainDeleveryFragment());
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_man_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_delivery);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        showFragment(new DashboardDeliveryFragment());

    }


    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_delivery, fragment)
                .commit();
    }
}
