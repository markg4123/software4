package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartRcv;
    TextView textView;
    Button backButton, orderButton;
    RecyclerView.Adapter adapter;

    String userId;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    ArrayList<CartItem> items = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRcv = findViewById(R.id.cartRcv);
        cartRcv.setHasFixedSize(true);
        cartRcv.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();

        textView = findViewById(R.id.textView);
        backButton = findViewById(R.id.backButton);
        orderButton = findViewById(R.id.orderButton);

        String title = getIntent().getExtras().getString("title");
        int price = getIntent().getExtras().getInt("price");

        final CartItem item = new CartItem(title, price);
        items.add(item);

        adapter = new CartAdapter(items);
        cartRcv.setAdapter(adapter);
        adapter.notifyItemInserted(items.size() - 1);

        final int total = item.getPrice();

        textView.setText("Total: €"+ total+"\n"+ "Number of Items: "+ items.size());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, NonAdminHomeActivity.class);
                startActivity(i);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Order").push();


        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total = item.getPrice();
                int number = items.size();

                Order order = new Order(total, number, userId);
                orders.add(order);


                reference.setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CartActivity.this, "Your Order Has Been Made", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CartActivity.this, NonAdminHomeActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(CartActivity.this, "Your Order Has Not Been Made", Toast.LENGTH_LONG).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CartActivity.this, "Order Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });



            }
        });

    }
}