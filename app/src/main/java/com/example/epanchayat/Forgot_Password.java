package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    TextView forg;

    FirebaseAuth mAuth;

    EditText txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forg=findViewById(R.id.backToLogin);
        txtEmail=findViewById(R.id.ForgotPass);
        mAuth=FirebaseAuth.getInstance();

        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Forgot_Password.this,UserLog.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void forgotPass(View view){
        String email=txtEmail.getText().toString();
         if(email.isEmpty()){
             txtEmail.setError("Required");
         }else{
             mAuth.sendPasswordResetEmail(email)
                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(Forgot_Password.this,"Check Your Email",Toast.LENGTH_LONG).show();
                                 Intent intent=new Intent(Forgot_Password.this,UserLog.class);
                                 startActivity(intent);
                                 finish();
                             }
                             else {
                                 Toast.makeText(Forgot_Password.this,"Error",Toast.LENGTH_LONG).show();
                             }
                         }
                     });
         }
    }
}