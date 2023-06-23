package com.example.epanchayat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ComplaintAdapter extends FirebaseRecyclerAdapter<Complaints, ComplaintAdapter.myViewholder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String flag;

    public ComplaintAdapter(@NonNull FirebaseRecyclerOptions<Complaints> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, final int position, @NonNull Complaints model) {
        int newposition=position;

        //fetching data and setting to the corresponding fields

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        String curUEmail=user.getEmail();


        holder.comptitle.setText(model.getComptitle());
        holder.compdesc.setText(model.getCompdesc());
        holder.cname.setText(model.getCtname());
        databaseReference=FirebaseDatabase.getInstance().getReference("Complaints");



        //To fetch the image we are using glid library

        Glide.with(holder.img.getContext()).load(model.image)
                .placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_text_light_focused)
                .error("https://www.pngitem.com/pimgs/m/537-5372558_flat-man-icon-png-transparent-png.png")
                .into(holder.img);


        // Performing delete operation

        holder.deleteCompbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.comptitle.getContext());
                builder.setTitle("Are you sure? ");
                builder.setMessage("Deleted data can't be retrieved!!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String oriUEmail=holder.cname.getText().toString();
                        if(oriUEmail.equals(curUEmail)){

                            Toast.makeText(holder.comptitle.getContext(), "deleted", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference().child("Complaints").child(getRef(newposition).getKey()).removeValue();

                        }
                        else {
                           builder.setTitle("Caught you!!");
                           builder.setMessage("You are not Authorised to delete this perticular complaint.");
                           builder.show();
                        }

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.comptitle.getContext(), "canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }
        });

        flag= model.getFlag();
        if(flag!=null)
            holder.status.setText(model.getFlag());
        else
            holder.status.setText("*pending");

        //fetching the complaint reply alone from realtime db to set to the textview
        DatabaseReference nodeReference = databaseReference.child(model.comptitle).child("compreply");
        nodeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Handle the retrieved data here
                if (dataSnapshot.exists()) {
                    // Data is available
                    String value = dataSnapshot.getValue(String.class);
                    holder.pdorply.setText(value);
                    // Do something with the retrieved value
                } else {

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
                Toast.makeText(holder.comptitle.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.complaintmodel,parent,false);

        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{

        ShapeableImageView img;
        TextView comptitle,compdesc,status,pdorply,cname;
        FloatingActionButton deleteCompbtn;



        public myViewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.setcompimg);
            comptitle=itemView.findViewById(R.id.setcomptitle);
            compdesc=itemView.findViewById(R.id.setcompdesc);

            deleteCompbtn=itemView.findViewById(R.id.deletecompbtn);
            status=itemView.findViewById(R.id.status);
            pdorply=itemView.findViewById(R.id.pdorply);
            cname=itemView.findViewById(R.id.cname);

        }
    }

}
