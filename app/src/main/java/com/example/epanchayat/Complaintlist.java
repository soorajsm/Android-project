//package com.example.epanchayat;
//
//public class Complaintlist {
//}


package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityComplaintlistBinding;
import com.example.epanchayat.databinding.ActivityMemberlistBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Complaintlist extends AppCompatActivity {

    RecyclerView recyclerView;
    ComplaintAdapter complaintAdapter;

    ActivityComplaintlistBinding binding;

    public Complaintlist() {
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityComplaintlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView=binding.complaintrv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Complaints> options =
                new FirebaseRecyclerOptions.Builder<Complaints>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Complaints"), Complaints.class)
                        .build();

        complaintAdapter=new ComplaintAdapter(options);
        recyclerView.setAdapter(complaintAdapter);

        binding.flbuttoncomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Complaintlist.this, addComplaint.class);
                startActivity(intent);
            }
        });


        binding.flbuttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Userhome.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        complaintAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        complaintAdapter.stopListening();
    }

}



























