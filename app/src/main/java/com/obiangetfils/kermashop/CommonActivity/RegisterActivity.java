package com.obiangetfils.kermashop.CommonActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.Buyer.LoginActivity;
import com.obiangetfils.kermashop.Delivery.DeliveryManHomeActivity;
import com.obiangetfils.kermashop.PickUpPoint.PickUpPointManActivity;
import com.obiangetfils.kermashop.Prevalent.Prevalent;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.Sellers.SellerHomeActivity;
import com.obiangetfils.kermashop.models.UserOBJ;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

import java.util.HashMap;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {
    private static final String USER_URI = "userUri";
    private static final String URI = "uri";

    // Create field

    private EditText userFirstName, userLastName, userName, userPhone, userPassword;

    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;


    private ImageView ImgUserPhoto;
    private Button registerBtn, loginBtn;
    private ProgressDialog loadingBar;
    private Dialog myDialog;
    private String[] typeOfUser = {"Acheteur", "Commerçant", "Livreur", "Point Retrait", "Commerçant et Point retrait"};
    private String typeUSER;

    private DatabaseReference RootRef;
    private FirebaseDatabase firebaseDatabase;

    private FirebaseDatabase m_Database;
    private static boolean s_persistenceInitialized = false;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //Creating DataBase
        //RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef = firebaseDatabase.getReference();

        // Config field
        userFirstName = (EditText) findViewById(R.id.user_firstname);
        userLastName = (EditText) findViewById(R.id.user_lastname);
        userName = (EditText) findViewById(R.id.username);
        userPhone = (EditText) findViewById(R.id.userphone);
        userPassword = (EditText) findViewById(R.id.user_password);
        ImgUserPhoto = (ImageView) findViewById(R.id.logoImg);
        myDialog = new Dialog(this);
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

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    openGallery();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {
            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

            UserOBJ userOBJ = new UserOBJ();
            updateUserInfo(pickedImgUri, userOBJ);
        }
    }

    // update user photo and name
    private void updateUserInfo(Uri pickeImgUri, final UserOBJ presentUser) {

        // first we need to upload user photo to firebase storage and get url
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickeImgUri.getLastPathSegment());
        imageFilePath.putFile(pickeImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // image uploaded succesfully
                // now we can get our image url
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // uri contain user image url
                        presentUser.setUser_image(uri.toString());

                        /** Share preference to retreive image URI **/
                        Context context = RegisterActivity.this;
                        SharedPreferences sharedPref = context.getSharedPreferences(USER_URI, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(URI, presentUser.getUser_image());
                        editor.commit();

                    }
                });
            }
        });

        Context context = RegisterActivity.this;
        SharedPreferences sharedPrefGet = context.getSharedPreferences(USER_URI, MODE_PRIVATE);
        String image = sharedPrefGet.getString(URI, "R.drawable.profile");
    }


    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(RegisterActivity.this, "Veuillez accepter les permissions requises", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else
            openGallery();
    }

    private void openGallery() {

        //TODO: open gallery intent and wait for user to pick an image !
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }


    public void showPopup(final String firstName, final String lastName, final String username, final String phone, final String password, final String image) {

        myDialog.setContentView(R.layout.custompopup);

        RadioGroup radioGroup = (RadioGroup) myDialog.findViewById(R.id.radio_group_popup);
        final RadioButton sellerRadio = (RadioButton) myDialog.findViewById(R.id.seller_popup);
        final RadioButton deliver = (RadioButton) myDialog.findViewById(R.id.livreur_popup);
        final RadioButton withdrawalPoint = (RadioButton) myDialog.findViewById(R.id.point_retrait_popup);
        final RadioButton sellerAndwithdrawalPoint = (RadioButton) myDialog.findViewById(R.id.livreur_et_commerçant);

        Button closePopup = (Button) myDialog.findViewById(R.id.close_popup);
        Button validatePopup = (Button) myDialog.findViewById(R.id.validate_popup);
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        validatePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sellerRadio.isChecked()) {
                    RegisterActivity.this.typeUSER = typeOfUser[1];
                } else if (deliver.isChecked()) {
                    RegisterActivity.this.typeUSER = typeOfUser[2];
                } else if (withdrawalPoint.isChecked()) {
                    RegisterActivity.this.typeUSER = typeOfUser[3];
                } else if(sellerAndwithdrawalPoint.isChecked()){
                    RegisterActivity.this.typeUSER = typeOfUser[4];
                }else {
                    RegisterActivity.this.typeUSER = typeOfUser[0];
                }
                validate(firstName, lastName, username, phone, password, RegisterActivity.this.typeUSER, image);
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    // This method creates userId
    private static String random(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    private void createAccount() {

        String firstName, lastName, username, phone, password, userID, image;
        firstName = userFirstName.getText().toString();
        lastName = userLastName.getText().toString();
        username = userName.getText().toString();
        phone = userPhone.getText().toString();
        password = userPassword.getText().toString();

        Context context = RegisterActivity.this;
        SharedPreferences sharedPrefGet = context.getSharedPreferences(USER_URI, MODE_PRIVATE);
        image = sharedPrefGet.getString(URI, "R.drawable.profile");
        sharedPrefGet.edit().clear();

        //TODO: Initialize image here;
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(username) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || image.isEmpty()) {
            showMessage("Tous les champs sont obligatoires");
        } else {
            showPopup(firstName, lastName, username, phone, password, image);
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void validate(final String firstName, final String lastName, final String username, final String phone, final String password, final String userType, final String image) {

        loadingBar.setTitle("Création du compte");
        loadingBar.setMessage("Veuiller patienter un instant");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("userFirstName", firstName);
                    userdataMap.put("userLastName", lastName);
                    userdataMap.put("userName", username);
                    userdataMap.put("userPhone", phone);
                    userdataMap.put("userPassword", password);
                    userdataMap.put("userType", userType);
                    userdataMap.put("userID", phone);
                    userdataMap.put("userImage", image);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Félitation, votre compte a été créé avec succès.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                UserOBJ userOBJ = new UserOBJ(firstName, lastName, username, password, phone, userType, phone, image);
                                gotoActivity(userOBJ);

                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Problème de réseau, veuillez vous connecter à internet.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Un compte existe déjà avec le numéro de téléphone: " + phone, Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Veuillez saisir un autre numéro de téléphone", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void gotoActivity(UserOBJ userOBJ) {

        if (userOBJ.getUser_type().equals("Acheteur")) {
            Intent intent = new Intent(RegisterActivity.this, BuyerHomeActivity.class);
            Prevalent.currentOnLineUser = userOBJ;
            startActivity(intent);
        } else if (userOBJ.getUser_type().equals("Commerçant")) {
            Intent intent = new Intent(RegisterActivity.this, SellerHomeActivity.class);
            Prevalent.currentOnLineUser = userOBJ;
            startActivity(intent);
        } else if (userOBJ.getUser_type().equals("Livreur")) {
            Intent intent = new Intent(RegisterActivity.this, DeliveryManHomeActivity.class);
            Prevalent.currentOnLineUser = userOBJ;
            startActivity(intent);
        } else {
            Intent intent = new Intent(RegisterActivity.this, PickUpPointManActivity.class);
            Prevalent.currentOnLineUser = userOBJ;
            startActivity(intent);
        }
    }
}
