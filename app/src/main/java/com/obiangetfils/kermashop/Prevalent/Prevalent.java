package com.obiangetfils.kermashop.Prevalent;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.models.AdminModel;
import com.obiangetfils.kermashop.models.UserOBJ;

import static android.content.Context.MODE_PRIVATE;

public class Prevalent
{
    public static final String UserPhoneKey = "UserPhone";
    public static final String UserPasswordKey = "UserPassword";

    private static final String USER_PREF = "user";
    private static final String USER_FIRST_NAME = "userFirstName";
    private static final String USER_LAST_NAME = "userLastName";
    private static final String USER_NAME = "userName";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_PHONE = "userPhone";
    private static final String USER_TYPE = "userType";
    private static final String USER_ID = "userID";
    private static final String USER_CHILD = "Users";
    private static final String USER_IMAGE = "userImage";
    public static UserOBJ currentOnLineUser;
    public static AdminModel currentOnLineAdmin;


    public static UserOBJ getCurrentOnLineUser(final String phone, final Context context) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Users").child(phone).exists()) {

                    final String userFirstName = dataSnapshot.child(USER_CHILD).child(phone).child(USER_FIRST_NAME).getValue(String.class);
                    final String userLastName = dataSnapshot.child(USER_CHILD).child(phone).child(USER_LAST_NAME).getValue(String.class);
                    final String userName = dataSnapshot.child(USER_CHILD).child(phone).child(USER_NAME).getValue(String.class);
                    final String userPassword = dataSnapshot.child(USER_CHILD).child(phone).child(USER_PASSWORD).getValue(String.class);
                    final String userPhone = dataSnapshot.child(USER_CHILD).child(phone).child(USER_PHONE).getValue(String.class);
                    final String userType = dataSnapshot.child(USER_CHILD).child(phone).child(USER_TYPE).getValue(String.class);
                    String userImage = dataSnapshot.child(USER_CHILD).child(phone).child(USER_IMAGE).getValue(String.class);
                    final String userId = dataSnapshot.child(USER_CHILD).child(phone).child(USER_ID).getValue(String.class);

                    SharedPreferences sharedPref = context.getSharedPreferences(USER_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(USER_FIRST_NAME, userFirstName);
                    editor.putString(USER_LAST_NAME, userLastName);
                    editor.putString(USER_NAME, userName);
                    editor.putString(USER_PASSWORD, userPassword);
                    editor.putString(USER_PHONE, userPhone);
                    editor.putString(USER_TYPE, userType);
                    editor.putString(USER_ID, userId);
                    editor.putString(USER_IMAGE, userImage);
                    editor.commit();

                    //UserOBJ userData = new UserOBJ(userFirstName, userLastName, userName, userPassword, userPhone, userType, userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        SharedPreferences sharedPrefGet = context.getSharedPreferences(USER_PREF, MODE_PRIVATE);
        String userFirstName = sharedPrefGet.getString(USER_FIRST_NAME, "Prénom(s)");
        String userLastName = sharedPrefGet.getString(USER_LAST_NAME, "NOM");
        String userName = sharedPrefGet.getString(USER_NAME, "Nom utilisateur");
        String userPassword = sharedPrefGet.getString(USER_PASSWORD, "Mot de passe");
        String userPhone = sharedPrefGet.getString(USER_PHONE, "06000000");
        String userType = sharedPrefGet.getString(USER_TYPE, "Type d'utilisateur");
        String userId = sharedPrefGet.getString(USER_ID, "Id utilisateur");
        String userImage = sharedPrefGet.getString(USER_IMAGE, "R.drawable.profile");

        UserOBJ userOBJ = new UserOBJ(userFirstName, userLastName, userName, userPassword, userPhone, userType, userId, userImage);
        sharedPrefGet.edit().clear();

        return userOBJ;
    }
}
