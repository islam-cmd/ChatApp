package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.DocAcceptRequestActivity;
import com.example.chatapp.Model.Appointment;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private Context mContext;
    private List<Appointment> mappointment;
    FirebaseUser fuser;

    public AppointmentAdapter(Context mContext, List<Appointment> mappointment) {
        this.mappointment = mappointment;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {

    }

  public class ViewHolder extends RecyclerView.ViewHolder {

      public ViewHolder(@NonNull View itemView) {
          super(itemView);
      }
  }


    @Override
    public int getItemCount() {
        return 0;
    }
}
