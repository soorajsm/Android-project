package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLog extends AppCompatActivity {
    static boolean flag;
    EditText LogEmail,LogPass;


    TextView tv1,tv2;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            checkUserAccessLevel(currentUser.getUid());
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        LogEmail=findViewById(R.id.LogEmail);
        LogPass=findViewById(R.id.LogPass);
        mAuth=FirebaseAuth.getInstance();
        tv1=findViewById(R.id.toReg);
        tv2=findViewById(R.id.ForgotPass);

        //Firebase Objects
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserLog.this,UserReg.class);
                startActivity(intent);
                finish();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserLog.this,Forgot_Password.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void onClick2(View view){
        String em,pass;
        em=String.valueOf(LogEmail.getText());
        pass=String.valueOf(LogPass.getText());
        if(TextUtils.isEmpty(em)){
            Toast.makeText(UserLog.this,"Email Shouldn't be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(UserLog.this,"Password Shouldn't be empty",Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(em, pass)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                            checkUserAccessLevel(user.getUid());

                        } else {
                            // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(UserLog.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                        }
                    }
                });
    }


    private void checkUserAccessLevel(String uid) {
        DocumentReference df=fStore.collection("Users").document(uid);
        //Extract data from document

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(" ","onSuccess" + documentSnapshot.getData());

                //identify Access Level
                if(flag=documentSnapshot.getString("isAdmin")!=null){
                    //user is admin
                    Intent intent=new Intent(UserLog.this,HomePage.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent=new Intent(UserLog.this, Userhome.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}