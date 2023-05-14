package com.example.epanchayat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityMemberlistBinding;

public class Memberlist extends AppCompatActivity {

    ActivityMemberlistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMemberlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.flbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Memberlist.this,Addmembers.class);
                startActivity(intent);
            }
        });
    }
}