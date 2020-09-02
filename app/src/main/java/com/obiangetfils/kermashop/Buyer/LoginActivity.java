package com.obiangetfils.kermashop.Buyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Admin.AdminHomeActivity;
import com.obiangetfils.kermashop.CommonActivity.ResetPasswordActivity;
import com.obiangetfils.kermashop.Delivery.DeliveryManHomeActivity;
import com.obiangetfils.kermashop.PickUpPoint.PickUpPointManActivity;
import com.obiangetfils.kermashop.Prevalent.Prevalent;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.Sellers.SellerHomeActivity;
import com.obiangetfils.kermashop.models.AdminModel;
import com.obiangetfils.kermashop.models.UserOBJ;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    public static final int LOGIN_REQUEST_CODE = 10001; // new
    private CheckBox chkBoxRememberMe;
    private TextView AdminLink, NotAdminLink;
    private TextView ForgetPassWordLink;
    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private SignInButton googleButton;

    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    String buttonText = "Google";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chk);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);
        ForgetPassWordLink = (TextView) findViewById(R.id.forget_password_link);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.login_user_password);
        LoginButton = (Button) findViewById(R.id.login_btn);
        googleButton = (SignInButton) findViewById(R.id.google_signIn);
        loadingBar = new ProgressDialog(this);

        InputPhoneNumber.setText("");
        InputPassword.setText("");

        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        ForgetPassWordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);
            }
        });


        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Connexion admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Connexion");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

        setGooglePlusButtonText(googleButton, buttonText);
    }

    private void LoginUser() {

        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();


        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Veuillez saisir votre numéro de téléphone, s'il vous plaît", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Veuillez saisir votre mot de passe, s'il vous plaît", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Connexion en cours");
            loadingBar.setMessage("Veuillez patienter un instant.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone, password);
        }

    }

    private void AllowAccessToAccount(final String phone, final String password) {

        if (chkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

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

                                Toast.makeText(LoginActivity.this, "Vous êtes désormais connecté", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                Prevalent.currentOnLineAdmin = adminData;
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Mot de passe ou numéro de téléphone incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Mot de passe ou numéro de téléphone incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Connexion annulée", Toast.LENGTH_SHORT).show();
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
                                loadingBar.dismiss();

                                gotoActivity(userData);

                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Mot de passe ou numéro de téléphone incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Mot de passe ou numéro de téléphone incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Aucun compte n'existe sous ce numéro", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Connexion annulée", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void gotoActivity(UserOBJ userData) {

        if (userData.getUser_type().equals("Acheteur")) {
            Intent intent = new Intent(LoginActivity.this, BuyerHomeActivity.class);
            Prevalent.currentOnLineUser = userData;
            startActivity(intent);
        } else if (userData.getUser_type().equals("Commerçant")) {
            //Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);
            Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);
            Prevalent.currentOnLineUser = userData;
            startActivity(intent);
        } else if (userData.getUser_type().equals("Livreur")) {
            Intent intent = new Intent(LoginActivity.this, DeliveryManHomeActivity.class);
            Prevalent.currentOnLineUser = userData;
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this, PickUpPointManActivity.class);
            Prevalent.currentOnLineUser = userData;
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        InputPhoneNumber.setText("");
        InputPassword.setText("");
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }


}
