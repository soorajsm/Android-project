package com.example.epanchayat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.MemoryLruGcSettings;

import java.nio.file.attribute.AclEntryFlag;


public class viewComplaintAdapter extends FirebaseRecyclerAdapter<Complaints, viewComplaintAdapter.myViewholder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    String flag,complaintrply;

    DatabaseReference databaseReference;


    public viewComplaintAdapter(@NonNull FirebaseRecyclerOptions<Complaints> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, final int position, @NonNull Complaints model) {
        int newposition=position;

        //fetching data and setting to the corresponding fields

        holder.comptitle.setText(model.getComptitle());
        holder.compdesc.setText(model.getCompdesc());
        holder.ctname.setText(model.getCtname());

        databaseReference=FirebaseDatabase.getInstance().getReference("Complaints");





        //To fetch the image we are using glid library

        Glide.with(holder.img.getContext()).load(model.image)
                .placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_text_light_focused)
                .error("https://www.pngitem.com/pimgs/m/537-5372558_flat-man-icon-png-transparent-png.png")
                .into(holder.img);




        holder.subcomprplybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.comptitle.getContext());
                builder.setTitle("Are you sure? ");
                builder.setMessage("This action can't be undone!");
                 complaintrply=holder.comprply.getText().toString();
                 holder.comprply.setText("");

                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.comptitle.getContext(), "solved", Toast.LENGTH_SHORT).show();
                        databaseReference.child(model.comptitle).child("flag").setValue("solved");
                        databaseReference.child(model.comptitle).child("compreply").setValue(complaintrply);


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

                    if(holder.pdorply.getText().toString().length()>=0)
                    {
                        holder.comprply.setVisibility(View.GONE);
                        holder.subcomprplybtn.setVisibility(View.GONE);
                        holder.comprply.setHint("");
                        holder.textlayout.setVisibility(View.GONE);
                    }
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








        flag= model.getFlag();
        if(flag!=null)
            holder.status.setText(model.getFlag());
        else
            holder.status.setText("Pending");
        }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcomplaintmodel,parent,false);

        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{

        ShapeableImageView img;
        TextView comptitle,compdesc,status,ctname,pdorply;

        TextInputLayout textlayout;
        EditText comprply;
        Button subcomprplybtn;
        FloatingActionButton seenCompbtn;


        public myViewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.setcompimg);
            comptitle=itemView.findViewById(R.id.setcomptitle);
            compdesc=itemView.findViewById(R.id.setcompdesc);
            status=itemView.findViewById(R.id.status);
            ctname=itemView.findViewById(R.id.cname);
            subcomprplybtn=itemView.findViewById(R.id.comprplysubmitbtn);
            comprply=itemView.findViewById(R.id.rplytocomplaint);
            pdorply=itemView.findViewById(R.id.pdorply);
            textlayout=itemView.findViewById(R.id.textlayout);




        }
    }

}
