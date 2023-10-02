package com.prashant.mychat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText username, phoneNumber, code;
    private Button send;
    public static final String fstring = "com.prashant.mychat.Fragments.UserFragment";
    public static final String fcode = "code";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);

        userIsLoggedIn();

        String countryCode = getCountryISO();

        SharedPreferences sp = getSharedPreferences(fstring, Context.MODE_PRIVATE);
        sp.edit().putString(fcode,countryCode).commit();

        username = findViewById(R.id.username);
        phoneNumber = findViewById(R.id.phonenumber);
        code = findViewById(R.id.code);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificationId != null)
                    verifyPhoneNumberWithCode();
                else
                    startPhoneNumberVerification();
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String vId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(vId, forceResendingToken);

                verificationId = vId;
                phoneNumber.setVisibility(View.GONE);
                username.setVisibility(View.GONE);
                code.setVisibility(View.VISIBLE);
                send.setText("Verify Code");
            }
        };
    }

    private void startPhoneNumberVerification() {
        PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setActivity(this)
                        .setPhoneNumber(phoneNumber.getText().toString())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(callbacks)
                        .build());
    }

    private void verifyPhoneNumberWithCode() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code.getText().toString());
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final String USERNAME = username.getText().toString();
                    final FirebaseUser USERID = FirebaseAuth.getInstance().getCurrentUser();

                    if(USERID != null) {
                        final DatabaseReference USER_DB = FirebaseDatabase.getInstance().getReference().child("Users").child(USERID.getUid());
                        USER_DB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.exists()){
                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("phone", USERID.getPhoneNumber());
                                    userMap.put("username", USERNAME);
                                    userMap.put("id", USERID.getUid());
                                    userMap.put("imageURL", "default");
                                    userMap.put("status", "offline");
                                    USER_DB.updateChildren(userMap);
                                }
                                userIsLoggedIn();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("LoginActivity", "Database operation cancelled", error.toException());
                            }
                        });
                    }
                }
            }
        });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private String getCountryISO() {
        String iso = null;

        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkCountryIso()!=null)
            iso = telephonyManager.getNetworkCountryIso().toString();
        return CountryToPhonePrefix.getPhone(iso);
    }
}
