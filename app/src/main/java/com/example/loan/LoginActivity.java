package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText et1,et2;
    Button btn1;
    TextView tv1,tv2;
    CheckBox checkBox;
    private FirebaseAuth mAuth;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et1=findViewById(R.id.username);
         et2=findViewById(R.id.passward);
         btn1=findViewById(R.id.login_btn);
         tv1=findViewById(R.id.signup_tv);
         tv2=findViewById(R.id.terms);
         checkBox=findViewById(R.id.checkbox);
        mAuth = FirebaseAuth.getInstance();

         tv2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent iv=new Intent(LoginActivity.this,TermsActivity.class);
                 startActivity(iv);

             }
         });
         tv1 .setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent iv=new Intent(LoginActivity.this, SignupActivity.class);
                 startActivity(iv);
             }
         });


         btn1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String username=et1.getText().toString().trim();
                 String Password=et2.getText().toString().trim();
                  if (username.isEmpty()){
                     et1.setError("Error");
                     return;
                 }
                 if (Password.isEmpty()){
                     et1.setError("Error");
                     return;

                 }
                 if (! Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                     et1.setError("Invalid Email Format");
                     return;
                 }



                 if (!checkBox.isChecked()){
                     tv2.setError("Accept");
                     return;
                 }

                 mAuth.signInWithEmailAndPassword(username,Password)
                         .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent iv=new Intent(LoginActivity.this,DrawerActivity2.class);
                                    startActivity(iv);
                                    finish();

                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Email or Password in incorrect", Toast.LENGTH_SHORT).show();
                                }
                             }
                         });
             }
         });
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
       }
   }

