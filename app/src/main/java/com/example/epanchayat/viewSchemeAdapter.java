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
import com.google.firebase.database.FirebaseDatabase;


public class viewSchemeAdapter extends FirebaseRecyclerAdapter<Schemes, viewSchemeAdapter.myViewholder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */



    public viewSchemeAdapter(@NonNull FirebaseRecyclerOptions<Schemes> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, final int position, @NonNull Schemes model) {
        int newposition=position;

        //fetching data and setting to the corresponding fields

        holder.scmtitle.setText(model.getScmtitle());
        holder.scmdesc.setText(model.getScmdesc());



        //To fetch the image we are using glid library

        Glide.with(holder.img.getContext()).load(model.image)
                .placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_text_light_focused)
                .error("https://www.pngitem.com/pimgs/m/537-5372558_flat-man-icon-png-transparent-png.png")
                .into(holder.img);


        // Performing delete operation

//        holder.deletescmbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder=new AlertDialog.Builder(holder.scmtitle.getContext());
//                builder.setTitle("Are you sure? ");
//                builder.setMessage("Deleted data can't be retrieved!!");
//
//                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(holder.scmtitle.getContext(), "deleted", Toast.LENGTH_SHORT).show();
//                        FirebaseDatabase.getInstance().getReference().child("Schemes").child(getRef(newposition).getKey()).removeValue();
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(holder.scmtitle.getContext(), "canceled", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.show();
//
//
//            }
//        });

    }
    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewschemesmodel,parent,false);

        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{

        ShapeableImageView img;
        TextView scmtitle,scmdesc;


        public myViewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.setscmimg);
            scmtitle=itemView.findViewById(R.id.setscmtitle);
            scmdesc=itemView.findViewById(R.id.setscmdesc);

        }
    }

}
