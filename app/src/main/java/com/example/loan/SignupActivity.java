package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupActivity extends AppCompatActivity {
    EditText Name,Username,Password;
    Button signupBtn;
    TextView login;
    FirebaseAuth firebaseAuth;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        loadAdds();


        Name=findViewById(R.id.name);
        Username=findViewById(R.id.username);
        Password=findViewById(R.id.passward);
        signupBtn=findViewById(R.id.signup);
        login=findViewById(R.id.signup_tv);
        firebaseAuth=FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString().trim();
                String username = Username.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (name.isEmpty()) {
                    Name.setError("Error");
                    return;
                }
                if (username.isEmpty()) {
                    Username.setError("Error");
                    return;
                }
                if (password.isEmpty()) {
                    Password.setError("Error");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    Username.setError("Invalid Email Format");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(SignupActivity.this, DrawerActivity2.class);
                                    startActivity(i);
                                    finish();
                                    Toast.makeText(SignupActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(SignupActivity.this, "Opps! Somthing went wrong" +task.getException() , Toast.LENGTH_SHORT).show();
                                    task.getException();
                                }
                            }
                        });
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignupActivity.this,DrawerActivity2.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


    }
   void loadAdds(){

       AdRequest adRequest = new AdRequest.Builder().build();

       InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
               new InterstitialAdLoadCallback() {
                   @Override
                   public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                       // The mInterstitialAd reference will be null until
                       // an ad is loaded.
                       mInterstitialAd = interstitialAd;

                   }

                   @Override
                   public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                       // Handle the error

                       mInterstitialAd = null;
                   }
               });
   }

    @Override
    public void onBackPressed() {


        if (mInterstitialAd != null) {
            mInterstitialAd.show(SignupActivity.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    mInterstitialAd=null;
                    SignupActivity.super.onBackPressed();
                }
            });
        } else {
           super.onBackPressed();
        }
    }
}