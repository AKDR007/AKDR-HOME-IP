package com.akdr.akdrhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.reporting.MessagingClientEvent;

public class LoginActivity extends AppCompatActivity {

    ConnectivityManager cm;
    NetworkInfo activeNetworkInfo ;


    private EditText Mail, passwordF;
    private Button LI;
    private FirebaseAuth Auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         activeNetworkInfo =  cm.getActiveNetworkInfo();

        Mail = (EditText) findViewById(R.id.Mail_data);
        passwordF = (EditText) findViewById(R.id.Passwd);

        LI = (Button) findViewById(R.id.LOGBTN);

        LI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private void chcknw(){

        if(activeNetworkInfo !=null && activeNetworkInfo.isConnectedOrConnecting()){
            get_data();
        }
        else{
            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
            Toast.makeText(this, "Turn ON Network !", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    private void get_data(){

        String m, p;

        m = Mail.getText().toString().trim();
        p = passwordF.getText().toString().trim();

        if(m.isEmpty() || p.isEmpty() || m.isEmpty() && p.isEmpty() ){

            Mail.setError("Enter Mail !");
            passwordF.setError("Enter Password !");
        }
        else{
            Auth.signInWithEmailAndPassword(m, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Logged in !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, IPActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Re-Login !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                        finish();
                    }

                }
            });
        }
    }

    private void Login(){chcknw();}


}