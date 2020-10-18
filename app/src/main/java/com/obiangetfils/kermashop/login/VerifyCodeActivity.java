package com.obiangetfils.kermashop.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.UserOBJ;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerifyCodeActivity extends AppCompatActivity {

    private String verificationId;
    private String phoneNumber;
    private TextView textMobile;
    private Button verifyBtn;
    private SharedPreferences pref;

    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private TextView resendTxt;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        Intent intent = getIntent();
        verificationId = intent.getStringExtra("VERIFICATION_ID");
        phoneNumber = intent.getStringExtra("PHONE");

        verifyBtn = (Button) findViewById(R.id.verifyBtn);
        resendTxt = (TextView) findViewById(R.id.resendTxt);

        textMobile = (TextView) findViewById(R.id.textMobile);
        textMobile.setText(phoneNumber);

        inputCode1 = (EditText) findViewById(R.id.inputCode1);
        inputCode2 = (EditText) findViewById(R.id.inputCode2);
        inputCode3 = (EditText) findViewById(R.id.inputCode3);
        inputCode4 = (EditText) findViewById(R.id.inputCode4);
        inputCode5 = (EditText) findViewById(R.id.inputCode5);
        inputCode6 = (EditText) findViewById(R.id.inputCode6);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        setUpOTPInputs();

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        inputCode1.getText().toString().trim().isEmpty() ||
                        inputCode2.getText().toString().trim().isEmpty() ||
                        inputCode3.getText().toString().trim().isEmpty() ||
                        inputCode4.getText().toString().trim().isEmpty() ||
                        inputCode5.getText().toString().trim().isEmpty() ||
                        inputCode6.getText().toString().trim().isEmpty()) {

                    Toast.makeText(VerifyCodeActivity.this, "Veuillez saisir le code envoyé par SMS!", Toast.LENGTH_SHORT).show();

                } else {

                    String allInputs = inputCode1.getText().toString().trim() +
                                       inputCode2.getText().toString().trim() +
                                       inputCode3.getText().toString().trim() +
                                       inputCode4.getText().toString().trim() +
                                       inputCode5.getText().toString().trim() +
                                       inputCode6.getText().toString().trim();

                    if (verificationId != null) {

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                verificationId,
                                allInputs
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            pref = getApplicationContext().getSharedPreferences("AUTHENTIFICATION", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putString("PHONE", phoneNumber);
                                            editor.putBoolean("IS_CONNECTED", true);
                                            editor.commit();

                                            FirebaseUser currentUser = mAuth.getCurrentUser();

                                            String user_phone, user_id;
                                            user_phone = phoneNumber;
                                            user_id = currentUser.getUid();

                                            saveUserInformation(user_phone, user_id);

                                            Intent mainActivityIntent = new Intent(VerifyCodeActivity.this, BuyerHomeActivity.class);
                                            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(mainActivityIntent);
                                        } else {
                                            Toast.makeText(VerifyCodeActivity.this, "Le code entré est erroné", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        resendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,
                        60,
                        TimeUnit.MICROSECONDS,
                        VerifyCodeActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyCodeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = newVerificationId;
                                Toast.makeText(VerifyCodeActivity.this, "Nouveau code envoyé", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void saveUserInformation(final String user_phone, final String user_id) {

        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(user_id).exists())) {

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("userPhone", user_phone);
                    userdataMap.put("userID", user_id);
                    databaseReference.child("Users").child(user_phone)
                            .updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(VerifyCodeActivity.this, "Félicitation, votre compte a été créé avec succès.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(VerifyCodeActivity.this, "Problème de réseau, veuillez vous connecter à internet.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(VerifyCodeActivity.this, "Un compte existe déjà avec ce numéro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VerifyCodeActivity.this, "Une erreur est survenue, veuillez réessayer ultérieurement", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}