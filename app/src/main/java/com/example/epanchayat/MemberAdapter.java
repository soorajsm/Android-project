package com.example.epanchayat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;


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
    protected void onBindViewHolder(@NonNull myViewholder holder, int position, @NonNull Members model) {


        //fetching data and setting to the corresponding fields

        holder.name.setText(model.getName());
        holder.area.setText(model.getArea());
        holder.age.setText(model.getAge());
        holder.post.setText(model.getPost());
        holder.gender.setText(model.getGender());
        holder.phoneno.setText(model.getPhoneno());


        //To fetch the image we are using glid library

        Glide.with(holder.img.getContext()).load(model.image)
                .placeholder(com.google.firebase.database.R.drawable.notification_tile_bg)
               .error("https://www.pngitem.com/pimgs/m/537-5372558_flat-man-icon-png-transparent-png.png")
                .into(holder.img);


    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.membersmodel,parent,false);

        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name,gender,post,age,phoneno,area;

        public myViewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.memberimg);
            name=itemView.findViewById(R.id.inputname);
            gender=itemView.findViewById(R.id.inputGender);
            post=itemView.findViewById(R.id.inputpost);
            age=itemView.findViewById(R.id.inputage);
            phoneno=itemView.findViewById(R.id.inputnumber);
            area=itemView.findViewById(R.id.inputarea);
        }
    }
}
