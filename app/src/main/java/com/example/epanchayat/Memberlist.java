package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityMemberlistBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Memberlist extends AppCompatActivity {

    RecyclerView recyclerView;
    MemberAdapter memberAdapter;

   ActivityMemberlistBinding binding;

    public Memberlist() {
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMemberlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView=binding.memrv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Members> options =
                new FirebaseRecyclerOptions.Builder<Members>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Members"), Members.class)
                        .build();

        memberAdapter=new MemberAdapter(options);
        recyclerView.setAdapter(memberAdapter);

        binding.flbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Memberlist.this,Addmembers.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       memberAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        memberAdapter.stopListening();
    }

}



























