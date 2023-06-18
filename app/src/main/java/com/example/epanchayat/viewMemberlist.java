package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityMemberlistBinding;
import com.example.epanchayat.databinding.ActivityViewMemberlistBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewMemberlist extends AppCompatActivity {

    RecyclerView recyclerView;
    viewMemberAdapter viewmemberAdapter;

    Members members;

    String phno;

    ActivityViewMemberlistBinding binding;

    FloatingActionButton clbtn;

    public viewMemberlist() {
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewMemberlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//
//        clbtn=findViewById(R.id.calbtn);
        recyclerView=binding.memrv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Members> options =
                new FirebaseRecyclerOptions.Builder<Members>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Members"), Members.class)
                        .build();

        viewmemberAdapter=new viewMemberAdapter(options);
        recyclerView.setAdapter(viewmemberAdapter);


        binding.flbuttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(viewMemberlist.this, Userhome.class);
                startActivity(intent);
                finish();
            }
        });


//        clbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                phno=members.getPhoneno();
//                AlertDialog.Builder builder=new AlertDialog.Builder(viewMemberlist.this);
//                builder.setTitle("Are you sure? ");
//                builder.setMessage("This call is going to connect with "+members.getName());
//
//                builder.setPositiveButton("Okay! connect", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//
////                        Toast.makeText(viewMemberlist.this, phno, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(viewMemberlist.this, "Canceled", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.show();
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewmemberAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmemberAdapter.stopListening();
    }



}



























