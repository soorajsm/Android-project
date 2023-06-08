package com.example.epanchayat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

   Context context;
    ArrayList<Members> list;

    public MyAdapter(Context context, ArrayList<Members> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.members,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
  Members members= (Members) list.get(position);
  holder.name.setText(members.getName());
  holder.gender.setText(members.getGender());
  holder.age.setText(members.getAge());
  holder.phone.setText(members.getPhoneno());
  holder.post.setText(members.getPost());
  holder.area.setText(members.getArea());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,gender,post,age,phone,area,image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.inputname);
            gender=itemView.findViewById(R.id.inputGender);
            post=itemView.findViewById(R.id.inputpost);
            age=itemView.findViewById(R.id.inputage);
            phone=itemView.findViewById(R.id.inputnumber);
        }
    }
}




























