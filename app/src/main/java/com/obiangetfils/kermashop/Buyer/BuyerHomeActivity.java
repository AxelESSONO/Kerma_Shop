package com.obiangetfils.kermashop.Buyer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.BuildConfig;
import com.obiangetfils.kermashop.Interface.RightMenuClick;
import com.obiangetfils.kermashop.Intro.IntroScreen;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.Story.StoryHomeActivity;
import com.obiangetfils.kermashop.fragments.Category;
import com.obiangetfils.kermashop.fragments.MyCart;
import com.obiangetfils.kermashop.fragments.childFragments.FavoriteProducts;
import com.obiangetfils.kermashop.fragments.childFragments.Product;
import com.obiangetfils.kermashop.login.LoginActivity;
import com.obiangetfils.kermashop.utills.DrawerLocker;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

import java.util.ArrayList;
import java.util.List;

public class BuyerHomeActivity extends AppCompatActivity implements DrawerLocker, RightMenuClick {

    private RelativeLayout toolbarRelative;
    private SearchView searchView;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ImageView drawer_header, user_image;
    private ImageButton right_navigation_cross_btn;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView, navigationView_right;
    private ExpandableListView main_drawer_list;
    private TextView userName, cartCountTv;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    AlertDialog builder;
    private List<DrawerMenuItem> items = new ArrayList<>();

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            long cartCount = intent.getLongExtra("CartCount", -1);
            if (cartCount > 0) {
                cartCountTv.setText("" + cartCount);
            } else {
                cartCountTv.setText("0");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home);

        bindViews();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String text = newText;
                //adapter.filter(text);

                return false;
            }
        });

        setSupportActionBar(toolbar);
        setupToolbar();
        setupDrawer();
        setupMenuItems();
        setupDrawerList();
        setupRightNavigationItem();
        onNavigationItemClick("Home7");
    }

    @SuppressLint("RestrictedApi")
    private void bindViews() {
        toolbarRelative = findViewById(R.id.myToolbar);
        toolbar = toolbarRelative.findViewById(R.id.toolbar);
        searchView = toolbarRelative.findViewById(R.id.searchView);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        navigationView_right = findViewById(R.id.navigationView_right);
        right_navigation_cross_btn = findViewById(R.id.right_nevigation_cross_btn);
        main_drawer_list = findViewById(R.id.main_drawer_list);
        drawer_header = findViewById(R.id.drawer_header);
        user_image = findViewById(R.id.drawer_profile_image);

        userName = findViewById(R.id.drawer_profile_name);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.app_name));
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        final View.OnClickListener toggleNavigationClickListener = actionBarDrawerToggle.getToolbarNavigationClickListener();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                                drawerLayout.closeDrawers();
                            } else {
                                getSupportFragmentManager().popBackStack();
                            }
                        }
                    });
                } else {
                    actionBar.setTitle("Kerma Shop");
                    setDrawerEnabled(true);
                }
            }
        });

        drawer_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyerHomeActivity.this, LoginActivity.class));
            }
        });

        TextView drawer_profile_name = findViewById(R.id.drawer_profile_name);

    }

    private void setupRightNavigationItem() {

        right_navigation_cross_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(navigationView_right);
            }
        });
    }

    private void setupMenuItems() {
        List<ChildItem> homeChildItems = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            homeChildItems.add(new ChildItem(R.drawable.ic_home_black_24dp, "Home " + i));
        items.add(new DrawerMenuItem(R.drawable.ic_home_black_24dp, "Home", homeChildItems));

        List<ChildItem> categoryChildItems = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
            categoryChildItems.add(new ChildItem(R.drawable.ic_categories_icon, "Category " + i));
        items.add(new DrawerMenuItem(R.drawable.ic_categories_icon, "Category", categoryChildItems));

        List<ChildItem> shop = new ArrayList<>();
        shop.add(new ChildItem(R.drawable.ic_fiber_new_black_24dp, "Les plus récents"));
        shop.add(new ChildItem(R.drawable.ic_fiber_new_black_24dp, "Meilleurs ventes"));
        shop.add(new ChildItem(R.drawable.ic_stars_black_24dp, "Super Affaires"));
        shop.add(new ChildItem(R.drawable.ic_stars_black_24dp, "Les plus aimés"));
        items.add(new DrawerMenuItem(R.drawable.ic_shop_icon, "Boutique", shop));

        items.add(new DrawerMenuItem(R.drawable.ic_person_black_24dp, "Mon compte", null));
        items.add(new DrawerMenuItem(R.drawable.ic_assignment_black_24dp, "Mes commandes", null));
        items.add(new DrawerMenuItem(R.drawable.ic_medal, "Points de récompense", null));
        items.add(new DrawerMenuItem(R.drawable.ic_scratch_card, "Cartes à gratter", null));

        items.add(new DrawerMenuItem(R.drawable.ic_location_on_black_24dp, "Mon adresse", null));
        items.add(new DrawerMenuItem(R.drawable.ic_favourite_icon, "Mes favoris", null));
        items.add(new DrawerMenuItem(R.drawable.ic_intro_icon, "Intro", null));
        items.add(new DrawerMenuItem(R.drawable.ic_news_icon, "News", null));
        items.add(new DrawerMenuItem(R.drawable.ic_contact_icon, "Nous contacter", null));
        items.add(new DrawerMenuItem(R.drawable.ic_about_us_icon, "A propos de nous", null));
        items.add(new DrawerMenuItem(R.drawable.ic_share_black_24dp, "Partager notre application", null));
        items.add(new DrawerMenuItem(R.drawable.ic_rate_us_icon, "Rate & Review", null));
        items.add(new DrawerMenuItem(R.drawable.ic_settings_icon, "Réglages", null));
        items.add(new DrawerMenuItem(R.drawable.ic_account_balance_wallet_black_24dp, "Connectivité", null));
    }

    private void setupDrawerList() {
        main_drawer_list.setAdapter(new DrawerListAdapter(this, getListItems()));
        main_drawer_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (items.get(groupPosition).getChildren() == null)
                    onNavigationItemClick(items.get(groupPosition).getTitle());
            }
        });
        main_drawer_list.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if (items.get(groupPosition).getChildren() == null)
                    onNavigationItemClick(items.get(groupPosition).getTitle());
            }
        });
        main_drawer_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                onNavigationItemClick(items.get(groupPosition).getChildren().get(childPosition).getTitle());
                return true;
            }
        });
    }

    private List<DrawerMenuItem> getListItems() {
        return items;
    }

    private void onNavigationItemClick(String title) {
        String fragmentTitle = title.replace(" ", "");

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);

        Toast.makeText(this, fragmentTitle, Toast.LENGTH_SHORT).show();
        Fragment f;

        /* Categories */
        if (fragmentTitle.contains("Category")) {

            setDrawerEnabled(false);
            setActionBarTitle("Categories");

            f = new Category();
            int styleNumber = Integer.parseInt(fragmentTitle.substring(fragmentTitle.length() - 1));
            Bundle b = new Bundle();
            b.putInt("CategoryStyleNumber", styleNumber);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation).addToBackStack(null).replace(R.id.main_fragment, f).commit();
        }

        /* Shop */
        if (fragmentTitle.equalsIgnoreCase("Newest")
            || fragmentTitle.equalsIgnoreCase("TopSellers")
            || fragmentTitle.equalsIgnoreCase("SuperDeals")
            || fragmentTitle.equalsIgnoreCase("MostLiked")) {

            Bundle productBundle = new Bundle();
            productBundle.putString("sortBy", fragmentTitle);
            productBundle.putBoolean("isMenuItem", true);
            productBundle.putBoolean("isBottomBarVisible", true);
            Fragment fragment = new Product();
            fragment.setArguments(productBundle);
            getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation).replace(R.id.main_fragment, fragment).commit();
        }

        if (fragmentTitle.equalsIgnoreCase("MyFavorites")) {

            setDrawerEnabled(false);

            //Bundle bundle = new Bundle();
            //bundle.putBoolean("isHeaderVisible", true);
            //bundle.putBoolean("favorites", true);
            Fragment fragment = new FavoriteProducts();
            //fragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                    .addToBackStack(null)
                    .replace(R.id.main_fragment, fragment)
                    .commit();
        }

        if (fragmentTitle.equalsIgnoreCase("ShareApp")) {

            setDrawerEnabled(false);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        if (fragmentTitle.equalsIgnoreCase("Intro"))
            startActivity(new Intent(BuyerHomeActivity.this, IntroScreen.class));

        if (fragmentTitle.equalsIgnoreCase("Login"))
            startActivity(new Intent(BuyerHomeActivity.this, LoginActivity.class));

        try {

            f = (Fragment) Class.forName("com.obiangetfils.kermashop" + ".fragments." + fragmentTitle).newInstance();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation).addToBackStack(null).replace(R.id.main_fragment, f).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        // CART
        final MenuItem item = menu.findItem(R.id.actionToolbar_Cart);
        RelativeLayout cart_relative = (RelativeLayout) menu.findItem(R.id.actionToolbar_Cart).getActionView();
        cartCountTv = cart_relative.findViewById(R.id.tv_cart_count);

        //cartCountTv.setText("2");

        setCartCount();

        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MyCart();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });

        return true;
    }

    private void setCartCount() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("CartList").child(firebaseUser.getUid())
                .child("ProductCart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long cartCount = snapshot.getChildrenCount();
                if (cartCount > 0) {
                    cartCountTv.setText("" + cartCount);
                } else {
                    cartCountTv.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem cartItem = menu.findItem(R.id.actionToolbar_Cart);
        MenuItem storyItem = menu.findItem(R.id.actionToolbar_story);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            cartItem.setVisible(false);
            storyItem.setVisible(false);
        } else {
            cartItem.setVisible(true);
            storyItem.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.actionToolbar_Cart:
              /*  Fragment fragment = new MyCart();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();*/

                break;

            case R.id.actionToolbar_story:
                startActivity(new Intent(BuyerHomeActivity.this, StoryHomeActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    public void onBackPressed() {
        // Get FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // Check if NavigationDrawer is Opened
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();

        }
        // Check if BackStack has some Fragments
        else if (fm.getBackStackEntryCount() == 1) {
            // Pop previous Fragment
            builder = new AlertDialog.Builder(this)
                    .setTitle("Sortir?")
                    .setMessage("Etes-vous sûr de vouloir quitter?")
                    .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("NON", null)
                    .show();
            Button nbutton = builder.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(Color.BLACK);
            Button pbutton = builder.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(Color.BLACK);

        } else fm.popBackStack();
    }

    @Override
    public void setRightDrawerEnable(boolean enable) {
        drawerLayout.openDrawer(navigationView_right);
    }

    /*
     * Adapter Class
     * */

    class DrawerListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private List<DrawerMenuItem> dataList;

        DrawerListAdapter(Context context, List<DrawerMenuItem> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public int getGroupCount() {
            return dataList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return (dataList.get(groupPosition).getChildren() == null) ? 0 : dataList.get(groupPosition).getChildren().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return dataList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return dataList.get(groupPosition).getChildren().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_drawer_parent, null);
            }
            TextView listTitleTextView = convertView.findViewById(R.id.main_drawer_list_header_text);
            ImageView iconState = convertView.findViewById(R.id.main_drawer_list_state_image);
            listTitleTextView.setText(((DrawerMenuItem) getGroup(groupPosition)).getTitle());
            iconState.setVisibility((dataList.get(groupPosition).getChildren() == null) ? View.GONE : View.VISIBLE);
            iconState.setImageResource((isExpanded) ? R.drawable.ic_keyboard_arrow_up_black_24dp : R.drawable.ic_keyboard_arrow_down_black_24dp);
            ((ImageView) convertView.findViewById(R.id.main_drawer_list_header_icon)).setImageResource(dataList.get(groupPosition).getIcon());

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_drawer_child, null);
            }
            ImageView childListIcon = convertView.findViewById(R.id.main_drawer_list_child_icon);
            TextView childListText = convertView.findViewById(R.id.main_drawer_list_child_text);
            Glide.with(context)
                    .load(dataList.get(groupPosition).getChildren().get(childPosition).getIcon())
                    .into(childListIcon);
            childListText.setText(dataList.get(groupPosition).getChildren().get(childPosition).getTitle());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    class DrawerMenuItem {
        private int icon;
        private String title;
        private List<ChildItem> children;

        public DrawerMenuItem(int icon, String title, List<ChildItem> children) {
            this.icon = icon;
            this.title = title;
            this.children = children;
        }

        public int getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }

        public List<ChildItem> getChildren() {
            return children;
        }
    }

    class ChildItem {
        private int icon;
        private String title;

        public ChildItem(int icon, String title) {
            this.icon = icon;
            this.title = title;
        }

        public int getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }
    }
}
