package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserReg extends AppCompatActivity {

    EditText editTextEmail,editTextPassword,userName,userPhone,userAddress;
    Button buttonReg;

    TextView reg;

//    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        editTextEmail=findViewById(R.id.regEmail);
        editTextPassword=findViewById(R.id.regPass);
        userAddress=findViewById(R.id.regAddress);
        userName=findViewById(R.id.regName);
        userPhone=findViewById(R.id.regPhone);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Firebase Objects
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        buttonReg=findViewById(R.id.btn_reg);
//        progressBar=findViewById(R.id.progress);
        reg=findViewById(R.id.click_to_log);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserReg.this,UserLog.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    public void onClick1(View v){
//        progressBar.setVisibility(View.VISIBLE);
        String email,password,name,address,phone;
        email=String.valueOf(editTextEmail.getText());
        password=String.valueOf(editTextPassword.getText());
        name=String.valueOf(userName.getText());
        address=String.valueOf(userAddress.getText());
        phone=String.valueOf(userPhone.getText());
        if(TextUtils.isEmpty(email)){
            Toast.makeText(UserReg.this,"The Email should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(UserReg.this,"The Password should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(!(phone.length()==10)){
            Toast.makeText(UserReg.this,"The Phone Number should be within 10 digits",Toast.LENGTH_LONG).show();
            return;
        }
        if(!isValid(password)){
            Toast.makeText(UserReg.this,"Generate a Strong Password",Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            DocumentReference df=fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo=new HashMap<>();
                            userInfo.put("userEmail",email);
                            userInfo.put("userPassword",password);
                            userInfo.put("userName",name);
                            userInfo.put("userAddress",address);
                            userInfo.put("userPhone",phone);

                            // Spcify userrrr
                            userInfo.put("isUser","1");

                            //Upload to firebaseee
                            df.set(userInfo);

                            Toast.makeText(UserReg.this, "Registration Successfull.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(UserReg.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    Pattern lowercase=Pattern.compile("^.*[a-z].*$");
    Pattern uppercase=Pattern.compile("^.*[A-Z].*$");
    Pattern numerics=Pattern.compile("^.*[0-9].*$");
    Pattern spl=Pattern.compile("^.*[^A-Z0-9a-z].*$");
    //    Pattern lowercase=Pattern.compile("^.*[a-z].*$");
    public boolean isValid(String password){
        if(password.length()<8){
            return false;
        }
        if(!lowercase.matcher(password).matches())
            return false;
        if(!uppercase.matcher(password).matches())
            return false;
        if(!numerics.matcher(password).matches())
            return false;
        if(!spl.matcher(password).matches())
            return false;
        return true;
    }

//    public boolean isNum(String phone){
//        if()
//    }
}