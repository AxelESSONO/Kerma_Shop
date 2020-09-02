package com.obiangetfils.kermashop.PickUpPoint;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.Prevalent.Prevalent;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class PickUpPointManActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ImageView returnHome;
    private Dialog myDialog;
    private String[] typeOfUser = {"Acheteur", "Commerçant", "Livreur", "Point Retrait", "Commerçant et Point retrait"};
    private String typeUSER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_point_man);
        returnHome = (ImageView) findViewById(R.id.ret);
        myDialog = new Dialog(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_pick_up);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String userType = Prevalent.currentOnLineUser.getUser_type();
                showPopup(Prevalent.currentOnLineUser.getUser_phone());
            }
        });

        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PickUpPointManActivity.this, BuyerHomeActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PickUpPointManActivity.this, BuyerHomeActivity.class));
    }

    public void showPopup(final String phone) {

        myDialog.setContentView(R.layout.custompopup);
        RadioGroup radioGroup = (RadioGroup) myDialog.findViewById(R.id.radio_group_popup);
        final RadioButton sellerRadio = (RadioButton) myDialog.findViewById(R.id.seller_popup);
        final RadioButton deliver = (RadioButton) myDialog.findViewById(R.id.livreur_popup);
        final RadioButton withdrawalPoint = (RadioButton) myDialog.findViewById(R.id.point_retrait_popup);
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
                    PickUpPointManActivity.this.typeUSER = typeOfUser[1];
                } else if (deliver.isChecked()) {
                    PickUpPointManActivity.this.typeUSER = typeOfUser[2];
                } else if (withdrawalPoint.isChecked()) {
                    PickUpPointManActivity.this.typeUSER = typeOfUser[3];
                } else {
                    PickUpPointManActivity.this.typeUSER = typeOfUser[0];
                }
                validate(phone, typeUSER);
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void validate(String phone, String typeUSER) {

        Prevalent.currentOnLineUser.setUser_type(typeUSER);
        final FirebaseDatabase firebaseDatabaseUpdate = FirebaseDatabase.getInstance();
        firebaseDatabaseUpdate.getReference().child("Users").child(phone).child("userType").setValue(typeUSER);

        if (!Prevalent.currentOnLineUser.getUser_type().equals("Acheteur")) {
            String updateUri = "@drawable/ic_specific_account";
            floatingActionButton.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(updateUri, null, getPackageName())));
        }
    }
}
