package com.obiangetfils.kermashop.Intro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Admin.AdminHomeActivity;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.Buyer.LoginActivity;
import com.obiangetfils.kermashop.CommonActivity.RegisterActivity;
import com.obiangetfils.kermashop.Delivery.DeliveryManHomeActivity;
import com.obiangetfils.kermashop.PickUpPoint.PickUpPointManActivity;
import com.obiangetfils.kermashop.Prevalent.Prevalent;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.Sellers.SellerHomeActivity;
import com.obiangetfils.kermashop.models.AdminModel;
import com.obiangetfils.kermashop.models.UserOBJ;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

import io.paperdb.Paper;


public class IntroScreen extends AppIntro {

    private String parentDbName = "Users";
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);

        addSlide(new Intro_Slide_1());
        addSlide(new Intro_Slide_2());
        addSlide(new Intro_Slide_3());
        addSlide(new Intro_Slide_4());
        addSlide(new Intro_Slide_5());

        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        // Hide StatusBar
        showStatusBar(false);
        showSkipButton(true);
        setProgressButtonEnabled(true);

        setBarColor(ContextCompat.getColor(IntroScreen.this, R.color.white));
        setSeparatorColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimaryLight));

        setColorDoneText(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));
        setColorSkipButton(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));
        setNextArrowColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));

        setIndicatorColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary), ContextCompat.getColor(IntroScreen.this, R.color.iconsLight));
        setSkipText("IGNORER");

    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);


        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserPhoneKey, UserPasswordKey);

                loadingBar.setTitle("Déjà connecté");
                loadingBar.setMessage("Veuillez patienter un instant.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }
        }


        Intent intent = new Intent(IntroScreen.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void AllowAccess(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        if (parentDbName.equals("Admins")) {

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(parentDbName).child(phone).exists()) {

                        String adminFirstName = dataSnapshot.child(parentDbName).child(phone).child("adminFirstName").getValue(String.class);
                        String adminName = dataSnapshot.child(parentDbName).child(phone).child("adminName").getValue(String.class);
                        String adminPassword = dataSnapshot.child(parentDbName).child(phone).child("adminPassword").getValue(String.class);
                        String adminPhone = dataSnapshot.child(parentDbName).child(phone).child("adminPhoneNumber").getValue(String.class);

                        AdminModel adminData = new AdminModel(adminFirstName, adminName, adminPassword, adminPhone);

                        if (adminData.getAdmin_phone().equals(phone)) {
                            if (adminData.getAdmin_password().equals(password)) {

                                Toast.makeText(IntroScreen.this, "Vous êtes désormais connecté", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(IntroScreen.this, AdminHomeActivity.class);
                                Prevalent.currentOnLineAdmin = adminData;
                                startActivity(intent);
                            } else {
                                Toast.makeText(IntroScreen.this, "Mot de passe ou numéro de téléphone incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(IntroScreen.this, "Mot de passe ou numéro de téléphone incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(IntroScreen.this, "Connexion annulée", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (parentDbName.equals("Users")) {

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(parentDbName).child(phone).exists()) {

                        String userFirstName = dataSnapshot.child(parentDbName).child(phone).child("userFirstName").getValue(String.class);
                        String userLastName = dataSnapshot.child(parentDbName).child(phone).child("userLastName").getValue(String.class);
                        String userName = dataSnapshot.child(parentDbName).child(phone).child("userName").getValue(String.class);
                        String userPassword = dataSnapshot.child(parentDbName).child(phone).child("userPassword").getValue(String.class);
                        String userPhone = dataSnapshot.child(parentDbName).child(phone).child("userPhone").getValue(String.class);
                        String userType = dataSnapshot.child(parentDbName).child(phone).child("userType").getValue(String.class);
                        String userId = dataSnapshot.child(parentDbName).child(phone).child("userID").getValue(String.class);

                        String userImage = dataSnapshot.child(parentDbName).child(phone).child("userImage").getValue(String.class);

                        UserOBJ userData = new UserOBJ(userFirstName, userLastName, userName, userPassword, userPhone, userType, userId, userImage);

                        if (userData.getUser_phone().equals(phone)) {
                            if (userData.getUser_password().equals(password)) {

                                //Toast.makeText(LoginActivity.this, "Vous êtes désormais connecté", Toast.LENGTH_SHORT).show();
                                //loadingBar.dismiss();

                                gotoActivity(userData);

                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(IntroScreen.this, "Mot de passe ou numéro de téléphone incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(IntroScreen.this, "Mot de passe ou numéro de téléphone incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(IntroScreen.this, "Aucun compte n'existe sous ce numéro", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(IntroScreen.this, "Connexion annulée", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void gotoActivity(UserOBJ userData) {

        if (userData.getUser_type().equals("Acheteur")) {
            Intent intent = new Intent(IntroScreen.this, BuyerHomeActivity.class);
            Prevalent.currentOnLineUser = userData;
            startActivity(intent);
        } else if (userData.getUser_type().equals("Commerçant")) {
            //Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);
            Intent intent = new Intent(IntroScreen.this, SellerHomeActivity.class);
            Prevalent.currentOnLineUser = userData;
            startActivity(intent);
        } else if (userData.getUser_type().equals("Livreur")) {
            Intent intent = new Intent(IntroScreen.this, DeliveryManHomeActivity.class);
            Prevalent.currentOnLineUser = userData;
            startActivity(intent);
        } else {
            Intent intent = new Intent(IntroScreen.this, PickUpPointManActivity.class);
            Prevalent.currentOnLineUser = userData;
            startActivity(intent);
        }
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent intent = new Intent(IntroScreen.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    //*********** Called when the Activity has detected the User pressed the Back key ********//

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Finish this Activity
        finish();
    }
}
