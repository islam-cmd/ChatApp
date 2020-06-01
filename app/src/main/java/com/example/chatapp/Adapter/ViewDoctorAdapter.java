package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.DocMessageActivity;
import com.example.chatapp.GpsActivity;
import com.example.chatapp.Model.Doctor;
import com.example.chatapp.R;
import com.example.chatapp.ViewDoctorInfoActivity;

import java.util.List;

public class ViewDoctorAdapter extends RecyclerView.Adapter<ViewDoctorAdapter.ViewHolder> {
    public static String doctor_Id;
    private Context mContext;
    private List<Doctor> mUsers;
    private static final String TAG = "ViewDoctorAdapter";

    public ViewDoctorAdapter(Context mContext, List<Doctor> mUsers) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }
    public ViewDoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new ViewDoctorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDoctorAdapter.ViewHolder holder, int position) {
        final Doctor doctor = mUsers.get(position);
        holder.username.setText(doctor.getUsername());
        if (doctor.getImageURL().equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {

            Glide.with(mContext).load(doctor.getImageURL()).into(holder.profile_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "doctor clicked with user id" + doctor.getId());
                Intent intent = new Intent(mContext, ViewDoctorInfoActivity.class);
                intent.putExtra("userid", doctor.getId());
                doctor_Id = doctor.getId();

//                intent.putExtra(" userto", doctor.getUsername());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);

        }


    }
}