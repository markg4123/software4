package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {
    DatabaseReference orderRef;
    String id;
    ArrayList<Order> orders = new ArrayList<>();
    RecyclerView orderRcv;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        orderRcv = findViewById(R.id.orderRcv);
        orderRcv.setHasFixedSize(true);
        orderRcv.setLayoutManager(new LinearLayoutManager(this));

        id = getIntent().getExtras().getString("id");

        orderRef =  FirebaseDatabase.getInstance().getReference("Order");


        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    Order order = s.getValue(Order.class);

                    if (order.getUser().equals(id)) {
                        orders.add(order);
                        adapter = new OrderAdapter(orders);
                        orderRcv.setAdapter(adapter);
                        adapter.notifyItemInserted(orders.size() - 1);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }});
    }
}