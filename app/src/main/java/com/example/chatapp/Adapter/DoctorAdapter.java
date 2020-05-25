package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.DocMessageActivity;
import com.example.chatapp.MessageActivity;
import com.example.chatapp.Model.Doctor;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private Context mContext;
    private List<Doctor> mUsers;
    private static final String TAG = "DoctorAdapter";

    public DoctorAdapter(Context mContext, List<Doctor> mUsers) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new DoctorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {
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
                Intent intent = new Intent(mContext, DocMessageActivity.class);
                intent.putExtra("userid", doctor.getId());
//                intent.putExtra(" userto", doctor.getUsername());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);

        }


    }
}