package com.obiangetfils.kermashop.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private CountryCodePicker ccp;
    private AppCompatEditText edtPhoneNumber;
    private String phoneNumber;
    private Button continueBtn;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        edtPhoneNumber = findViewById(R.id.phone_number_edt);
        ccp.registerPhoneNumberTextView(edtPhoneNumber);
        continueBtn = (Button) findViewById(R.id.continueBtn);

        mAuth = FirebaseAuth.getInstance();


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = edtPhoneNumber.getText().toString().trim();
                if (phoneNumber.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Veuillez saisir votre numéro de téléphone SVP !", Toast.LENGTH_SHORT).show();
                } else {
                    int firstDigitInPhoneNumber = Integer.parseInt(String.valueOf(phoneNumber.charAt(0)));
                   /* if (firstDigitInPhoneNumber == 0) {
                        Toast.makeText(RegisterActivity.this, "Veuillez retirer le zéro qui est au début du numéro de téléphone!", Toast.LENGTH_SHORT).show();
                    } else {*/
                    registerOTP(ccp.getFullNumber());
                    //Toast.makeText(RegisterActivity.this, ccp.getFullNumber(), Toast.LENGTH_SHORT).show();
                    //}
                }
            }
        });
    }

    private void registerOTP(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+"+ccp.getFullNumber(), 60,
                TimeUnit.MICROSECONDS,
                LoginActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verifyCodeActivity(verificationId);
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                    }

                });

    }

    private void verifyCodeActivity(String verificationId) {
        Intent intent = new Intent(LoginActivity.this, VerifyCodeActivity.class);
        intent.putExtra("VERIFICATION_ID", verificationId);
        intent.putExtra("PHONE", "+"+ccp.getFullNumber());
        startActivity(intent);
        finish();
    }

}
