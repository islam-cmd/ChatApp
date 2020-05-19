package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Docopinion;
import com.example.chatapp.Model.OnlineConsultation;
import com.example.chatapp.OnlineConsultationdoc;
import com.example.chatapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Onlineconadapter extends RecyclerView.Adapter<Onlineconadapter.ViewHolder> {
    private Context context;
    private ArrayList<OnlineConsultation> mconsults;

    public Onlineconadapter(Context context, ArrayList<OnlineConsultation> mconsults) {
        this.context = context;
        this.mconsults = mconsults;
    }

    @NonNull
    @Override
    public Onlineconadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new Onlineconadapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Onlineconadapter.ViewHolder holder, int position) {

        final OnlineConsultation user = mconsults.get(position);

        holder.username.setText(user.getSubject());
//            if (user.getUri().equals("default")) {
        holder.profile_image.setImageResource(R.mipmap.ic_launcher);
//            } else {
//                Glide.with(mContext).load(user.getUri()).into(holder.profile_image);
//            }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "It works fine", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Docopinion.class);
                    intent.putExtra("userid", user.getPatientID());
                    intent.putExtra("subject", user.getSubject());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mconsults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}
