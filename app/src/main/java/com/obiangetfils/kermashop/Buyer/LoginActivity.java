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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Admin.AdminHomeActivity;
import com.obiangetfils.kermashop.Prevalent.Prevalent;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.AdminModel;
import com.obiangetfils.kermashop.models.UserOBJ;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    public static final int LOGIN_REQUEST_CODE = 10001 ; // new
    private CheckBox chkBoxRememberMe;
    private TextView AdminLink, NotAdminLink, ForgetPassWordLink;
    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chk);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);
        ForgetPassWordLink = (TextView) findViewById(R.id.forget_password_link);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.login_user_password);
        LoginButton = (Button) findViewById(R.id.login_btn);
        loadingBar = new ProgressDialog(this);


        Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
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
        RootRef = FirebaseDatabase.getInstance().getReference().child(parentDbName).child(phone);

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ////Users userData = dataSnapshot.getValue(Users.class);

                // Users Login
                if (parentDbName.equals("Users")) {
                    String user_firstname = dataSnapshot.child("userFirstName").getValue(String.class);
                    String user_lastName = dataSnapshot.child("userLastName").getValue(String.class);
                    String user_username = dataSnapshot.child("userName").getValue(String.class);
                    String user_password = dataSnapshot.child("userPassword").getValue(String.class);
                    String user_phone = dataSnapshot.child("userPhone").getValue(String.class);
                    UserOBJ userData = new UserOBJ(user_firstname, user_lastName, user_username, user_password, user_phone);

                    if (dataSnapshot.exists()) {
                        if (userData.getUser_phone().equals(phone)) {
                            if (userData.getUser_password().equals(password)) {
                                if (parentDbName.equals("Users")) {
                                    Toast.makeText(LoginActivity.this, "Vous êtes désormais connecté", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, BuyerHomeActivity.class);
                                    Prevalent.currentOnLineUser = userData;
                                    startActivity(intent);
                                }
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Mot de passe saisi est incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Aucun compte n'existe au numéro: " + phone, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Numéro de téléphone ou mot de passe incorrect, veuillez vérifier les informations saisies.", Toast.LENGTH_LONG).show();
                    }
                }

                // Admin Login

                else if (parentDbName.equals("Admins")) {

                    String admin_firstname = dataSnapshot.child("adminFirstName").getValue(String.class);
                    String admin_Name = dataSnapshot.child("adminName").getValue(String.class);
                    String admin_password = dataSnapshot.child("adminPassword").getValue(String.class);
                    String admin_phone = dataSnapshot.child("adminPhoneNumber").getValue(String.class);
                    AdminModel userData = new AdminModel(admin_firstname, admin_Name, admin_password, admin_phone);

                    if (dataSnapshot.exists()) {
                        if (userData.getAdmin_phone().equals(phone)) {
                            if (userData.getAdmin_password().equals(password)) {
                                if (parentDbName.equals("Admins")) {
                                    Toast.makeText(LoginActivity.this, "Vous êtes désormais connecté", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Mot de passe saisi est incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Aucun compte n'existe au numéro: " + phone, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Numéro de téléphone ou mot de passe incorrect, veuillez vérifier les informations saisies.", Toast.LENGTH_LONG).show();
                    }

                } else if (parentDbName.equals("Delivery Man")) {

                } else if (parentDbName.equals("Seller")) {

                }

                //Toast.makeText(LoginPostActivity.this, userData.getPhone(), Toast.LENGTH_SHORT).show();

    /*            if (dataSnapshot.exists()) {

                    if (userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                            if (parentDbName.equals("Admins")) {
                                Toast.makeText(LoginPostActivity.this, "Vous êtes désormais connecté", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginPostActivity.this, AdminHomeActivity.class);
                                startActivity(intent);

                            } else if (parentDbName.equals("Users")) {
                                Toast.makeText(LoginPostActivity.this, "Vous êtes désormais connecté", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginPostActivity.this, BuyerHomeActivity.class);
                                Prevalent.currentOnLineUser = userData;
                                startActivity(intent);
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginPostActivity.this, "Mot de passe saisi est incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginPostActivity.this, "Aucun compte n'existe au numéro: " + phone, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginPostActivity.this, "Numéro de téléphone ou mot de passe incorrect, veuillez vérifier les informations saisies.", Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
