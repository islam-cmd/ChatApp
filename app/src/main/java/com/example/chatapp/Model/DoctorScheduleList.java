package com.example.chatapp.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatapp.R;

import java.util.List;

public class DoctorScheduleList extends ArrayAdapter<DoctorProfile> {
    private Activity context;
    private List<DoctorProfile> doctorList;


    public DoctorScheduleList(Activity context, List<DoctorProfile> doctorList){
        super(context, R.layout.doc_list, doctorList);
        this.context =context;
        this.doctorList=doctorList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.doc_list, null,true);
        TextView name =(TextView) listViewItem.findViewById(R.id.docListName);
        TextView time = (TextView) listViewItem.findViewById(R.id.docListTime);
        DoctorProfile doctor = doctorList.get(position);
        name.setText(doctor.getFirstName() +" "+ doctor.getLastName());
        time.setText(doctor.getStartTime()+"-"+doctor.getEndTime());
      return listViewItem;

    }
}
