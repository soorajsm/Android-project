package com.example.epanchayat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    TextView log;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void ctlogin(View V){
        Intent intent=new Intent(MainActivity.this, UserLog.class);
        startActivity(intent);
    }
    public void ctreg(View V){
        Intent intent=new Intent(MainActivity.this, UserReg.class);
        startActivity(intent);
    }

    public void adlogin(View V){
        Intent intent=new Intent(MainActivity.this, UserLog.class);
        startActivity(intent);
    }


}