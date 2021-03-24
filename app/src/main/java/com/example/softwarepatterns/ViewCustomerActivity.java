package com.example.softwarepatterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCustomerActivity extends AppCompatActivity {
    RecyclerView userRcv;
    DatabaseReference userRef;
    RecyclerView.Adapter adapter;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);


        userRcv = findViewById(R.id.userRcv);
        userRcv.setHasFixedSize(true);
        userRcv.setLayoutManager(new LinearLayoutManager(this));
        userRef =  FirebaseDatabase.getInstance().getReference("User");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {

                    User user = s.getValue(User.class);


                    users.add(user);
                    adapter = new UserAdapter(users);
                    userRcv.setAdapter(adapter);
                    adapter.notifyItemInserted(users.size() - 1);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(ViewCustomerActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }
}