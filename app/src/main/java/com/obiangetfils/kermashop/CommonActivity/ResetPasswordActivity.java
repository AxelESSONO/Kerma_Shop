package com.obiangetfils.kermashop.CommonActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.Buyer.LoginActivity;
import com.obiangetfils.kermashop.Prevalent.Prevalent;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check = "";
    private TextView pageTitle, titleQuestions;
    private EditText phoneNumber, question1, question2;
    private Button verifyBtn;

    private ImageView homePage;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        check = getIntent().getStringExtra("check");
        homePage = (ImageView) findViewById(R.id.ret);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        phoneNumber = (EditText) findViewById(R.id.phone_number_question);
        question1 = (EditText) findViewById(R.id.prefered_question);
        question2 = (EditText) findViewById(R.id.color_question);
        verifyBtn = (Button) findViewById(R.id.verify_btn);
        toolbarTitle.setText("Réinitialisation du mot de passe");

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        phoneNumber.setVisibility(View.GONE);
        if (check.equals("settings")) {
            pageTitle.setText("Définir les questions de sécurité.");
            titleQuestions.setText("Définir les questions de sécurité suivantes?");
            verifyBtn.setText("Définir");
            displayPreviousAnswers();
            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswer();
                }
            });

        } else if (check.equals("login")) {
            phoneNumber.setVisibility(View.VISIBLE);

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });
        }
    }

    private void verifyUser() {
        final String phone = phoneNumber.getText().toString();
        final String answer1 = question1.getText().toString().toLowerCase();
        final String answer2 = question2.getText().toString().toLowerCase();

        if (!phone.equals("") && !answer1.equals("") && !answer2.equals("")) {


            final DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Users")
                    .child(phone);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String mPhone = dataSnapshot.child("phone").getValue().toString();

                        if (dataSnapshot.hasChild("Security Questions")) {
                            String answ1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String answ2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                            if (!answ1.equals(answer1)) {
                                Toast.makeText(ResetPasswordActivity.this, "La première réponse est incorrecte.", Toast.LENGTH_SHORT).show();
                            } else if (!answ2.equals(answer2)) {
                                Toast.makeText(ResetPasswordActivity.this, "La deuxième réponse est incorrecte.", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("Nouveau mot de passe.");

                                final EditText newPassWord = new EditText(ResetPasswordActivity.this);
                                newPassWord.setHint("Saisir le nouveau mot de passe ici...");
                                builder.setView(newPassWord);

                                builder.setPositiveButton("Modification", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (!newPassWord.getText().toString().equals("")) {
                                            reference.child("password")
                                                    .setValue(newPassWord.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(ResetPasswordActivity.this, "Votre mot de passe a été modifié avec succès.", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });

                                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                builder.show();
                            }
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Vous n'avez pas défini les questions de sécurité.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Ce numéro de téléphone n'existe pas.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(this, "Remplir entièrement le formulaire.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAnswer() {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();
        if (question1.equals("") && question2.equals("")) {
            Toast.makeText(ResetPasswordActivity.this, "Veuillez répondre aux deux questions.", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Users")
                    .child(Prevalent.currentOnLineUser.getUser_phone());
            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", answer1);
            userdataMap.put("answer2", answer2);
            reference.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "Vos questions de sécurité ont été définies avec succès.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this, BuyerHomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    public void displayPreviousAnswers() {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(Prevalent.currentOnLineUser.getUser_phone());

        reference.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String answ1 = dataSnapshot.child("answer1").getValue().toString();
                    String answ2 = dataSnapshot.child("answer2").getValue().toString();
                    question1.setText(answ1);
                    question2.setText(answ2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
