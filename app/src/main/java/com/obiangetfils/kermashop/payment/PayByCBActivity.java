package com.obiangetfils.kermashop.payment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.view.CardForm;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

public class PayByCBActivity extends AppCompatActivity {

    private CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_by_cb);

        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("Un SMS est requis sur ce numéro")
                .setup(PayByCBActivity.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        buy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(PayByCBActivity.this);
                    alertBuilder.setTitle("Confirmer avant l'achat");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Date d'expiration de la carte: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Carte CVV: " + cardForm.getCvv() + "\n" +
                            "Code postal: " + cardForm.getPostalCode() + "\n" +
                            "Numéro de téléphone: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            dialogInterface.dismiss();
                            Toast.makeText(PayByCBActivity.this, "Merci d'avoir acheté", Toast.LENGTH_LONG).show();
                        }
                    });
                    alertBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(PayByCBActivity.this, "Veuillez remplir le formulaire", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
