//package com.example.epanchayat;
//
//public class Complaintlist {
//}


package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityComplaintlistBinding;
import com.example.epanchayat.databinding.ActivityMemberlistBinding;
import com.example.epanchayat.databinding.ActivitySchemelistBinding;
import com.example.epanchayat.databinding.ActivityViewSchemelistBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewSchemelist extends AppCompatActivity {

    RecyclerView recyclerView;
    viewSchemeAdapter schemeAdapter;

    ActivityViewSchemelistBinding binding;

    public viewSchemelist() {
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewSchemelistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView=binding.scmrv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Schemes> options =
                new FirebaseRecyclerOptions.Builder<Schemes>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Schemes"), Schemes.class)
                        .build();

        schemeAdapter=new viewSchemeAdapter(options);
        recyclerView.setAdapter(schemeAdapter);


//        binding.flbuttonback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(), Userhome.class);
//                startActivity(intent);
//                finish();
//            }
//        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent=new Intent(getApplicationContext(), Userhome.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        schemeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        schemeAdapter.stopListening();
    }

}


