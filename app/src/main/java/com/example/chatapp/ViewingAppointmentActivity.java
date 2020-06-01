package com.example.chatapp;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.viewpager.widget.ViewPager;

        import android.content.Intent;
        import android.os.Bundle;
        import android.provider.ContactsContract;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TableRow;
        import android.widget.TextView;

        import com.example.chatapp.Fragments.UsersFragment;
        import com.example.chatapp.Model.Appointment;
        import com.example.chatapp.Model.Doctor;
        import com.example.chatapp.Model.User;
        import com.example.chatapp.Model.ViewAppointmentList;
        import com.google.android.material.tabs.TabLayout;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.List;


public class ViewingAppointmentActivity extends AppCompatActivity {

    Button returnbtn;
    Button cancelbtn;
    Button Schedule_btn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseDatabase firebaseDatabase;
    FirebaseDatabase getDatabase;
    DatabaseReference databaseRef;
    ListView viewapp;
    TextView username;
    TabLayout doc_tab;
    List<Appointment> viewappList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_appointment);
        returnbtn = findViewById(R.id.dashboard);
        username = findViewById(R.id.username);
        viewapp = findViewById(R.id.view_appointment);
        Schedule_btn = findViewById(R.id.Schedule_appointment_btn);
        viewappList = new ArrayList<>();
        databaseRef = FirebaseDatabase.getInstance().getReference("Appointment");
        cancelbtn = findViewById(R.id.cancel_appointment);


//        Schedule_btn.setOnClickListener(new View.OnClickListener() {
   //         @Override
     //       public void onClick(View view) {
       //         startActivity(new Intent(ViewingAppointmentActivity.this, RequestConsultationActivity.class));
         //   }
        //});

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viewappList.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Appointment appointment = dataSnapshot1.getValue(Appointment.class);
                    viewappList.add(appointment);
                }
                ViewAppointmentList adapter = new ViewAppointmentList(ViewingAppointmentActivity.this, viewappList);
                viewapp.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

       // cancelbtn.setOnClickListener(view -> databaseRef.addValueEventListener(new ValueEventListener() {
         //   @Override
           // public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             //   for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
               //     dataSnapshot1.getRef().removeValue();
                //}
            //}
           /// @Override
            //public void onCancelled(@NonNull DatabaseError databaseError) {

            //}
       // }));

    }
}
