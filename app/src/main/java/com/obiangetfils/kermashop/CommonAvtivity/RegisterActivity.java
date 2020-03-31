package com.obiangetfils.kermashop.CommonAvtivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Buyer.LoginActivity;
import com.obiangetfils.kermashop.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    // Create field

    private EditText userFirstName, userLastName, userName, userPhone, userPassword;
    private Button registerBtn, loginBtn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Config field
        userFirstName = (EditText) findViewById(R.id.user_firstname);
        userLastName = (EditText) findViewById(R.id.user_lastname);
        userName = (EditText) findViewById(R.id.username);
        userPhone = (EditText) findViewById(R.id.userphone);
        userPassword = (EditText) findViewById(R.id.user_password);

        registerBtn = (Button) findViewById(R.id.signupBtn);
        loginBtn = (Button) findViewById(R.id.signup_login);

        loadingBar = new ProgressDialog(this);

        // Go to Login Activity
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount();
            }
        });

    }

    private void createAccount() {

        String firstName, lastName, username, phone, password;
        firstName = userFirstName.getText().toString();
        lastName = userLastName.getText().toString();
        username = userName.getText().toString();
        phone = userPhone.getText().toString();
        password = userPassword.getText().toString();

        if (TextUtils.isEmpty(firstName))
        {
            Toast.makeText(this, "Saisissez votre prénom SVP...", Toast.LENGTH_SHORT).show();
        }
        
        else if (TextUtils.isEmpty(lastName))
        {
            Toast.makeText(this, "Saisissez votre nom SVP...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Saisissez votre nom d'utilisateur SVP...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Saisissez votre numéro de téléphone SVP...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Saisissez votre mot de passe SVP...", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Création du compte");
            loadingBar.setMessage("Veuiller patienter un instant");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validate(firstName,lastName,username, phone,password);
        }

    }

    private void validate(final String firstName,final String lastName,final String username, final String phone, final String password) {

        //Creating DataBase
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("userFirstName", firstName);
                    userdataMap.put("userLastName", lastName);
                    userdataMap.put("userName", username);
                    userdataMap.put("userPhone", phone);
                    userdataMap.put("userPassword", password);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Félitation, votre compte a été créé avec succès.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Problème de réseau, veuillez vous connecter à internet.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Un compte existe déjà avec le numéro de téléphone: " + phone, Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Veuillez saisir un autre numéro de téléphone", Toast.LENGTH_LONG).show();

                    //Intent intent = new Intent(RegisterActivity.this, TestActivity.class);
                    //startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
