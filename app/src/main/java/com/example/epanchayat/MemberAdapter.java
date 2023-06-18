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


public class MemberAdapter extends FirebaseRecyclerAdapter<Members, MemberAdapter.myViewholder> {
     /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */



    public MemberAdapter(@NonNull FirebaseRecyclerOptions<Members> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, final int position, @NonNull Members model) {
       int newposition=position;

        //fetching data and setting to the corresponding fields

        holder.name.setText(model.getName());
        holder.area.setText(model.getArea());
        holder.age.setText(model.getAge());
        holder.post.setText(model.getPost());
        holder.gender.setText(model.getGender());
        holder.phoneno.setText(model.getPhoneno());


        //To fetch the image we are using glid library

        Glide.with(holder.img.getContext()).load(model.image)
                .placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_text_light_focused)
               .error("https://www.pngitem.com/pimgs/m/537-5372558_flat-man-icon-png-transparent-png.png")
                .into(holder.img);


        // Performing delete operation

        holder.deleteDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure? ");
                builder.setMessage("Deleted data can't be retrieved!!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "deleted", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference().child("Members").child(getRef(newposition).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }
        });

    }
    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.membersmodel,parent,false);

        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{

        ShapeableImageView img;
        TextView name,gender,post,age,phoneno,area;
       FloatingActionButton deleteDatabtn;

        public myViewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.inputimg);
            name=itemView.findViewById(R.id.inputname);
            gender=itemView.findViewById(R.id.inputgender);
            post=itemView.findViewById(R.id.inputpost);
            age=itemView.findViewById(R.id.inputage);
            phoneno=itemView.findViewById(R.id.inputnumber);
            area=itemView.findViewById(R.id.inputarea);
            deleteDatabtn=itemView.findViewById(R.id.deletedatabtn);

        }
    }

}
