package com.example.epanchayat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityHomePageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomePage extends AppCompatActivity {
    Button logout;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
 ActivityHomePageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding=ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            Intent intent = new Intent(HomePage.this, UserLog.class);
            startActivity(intent);
            finish();
     }
//
//
//        else {
//            Toast.makeText(HomePage.this, firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
//        }



        logout=findViewById(R.id.admlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),UserLog.class);
                startActivity(intent);
                finish();
            }
        });

//        binding.flbuttonback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    public void viewComplaints(View v)
    {
        Intent intent=new Intent(getApplicationContext(),viewComplaintlist.class);
        startActivity(intent);
        finish();
    }

    public void addMembers(View v)
    {
        Intent intent=new Intent(getApplicationContext(),Memberlist.class);
        startActivity(intent);
        finish();
    }


    public void addSchemes(View v)
    {
        Intent intent=new Intent(getApplicationContext(),Schemelist.class);
        startActivity(intent);
        finish();
    }

}