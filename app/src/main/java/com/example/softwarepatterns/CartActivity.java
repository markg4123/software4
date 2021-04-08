package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartRcv;
    TextView textView;
    Button backButton, orderButton;
    RecyclerView.Adapter adapter;

    String userId;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference reference, cartRef, userRef;

    ArrayList<CartItem> items = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();
    ArrayList <String> orderdItems = new ArrayList<String>();

    int total = 0;

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

        cartRef =  FirebaseDatabase.getInstance().getReference("CartItem");
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {

                    CartItem cartItem = s.getValue(CartItem.class);


                    items.add(cartItem);
                    adapter = new CartAdapter(items);
                    cartRcv.setAdapter(adapter);
                    adapter.notifyItemInserted(items.size() - 1);


                    total = total + cartItem.getPrice();

                    textView.setText("Total: €"+ total+"\n"+ "Number of Items: "+ items.size());

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });





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
                checkUserDetails();
                cartRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                            CartItem cartItem = s.getValue(CartItem.class);

                            orderdItems.add(cartItem.getTitle());
                            final int number = items.size();
                            final String itemsOrdered = String.valueOf(orderdItems);

                            AlertDialog dialog = new AlertDialog.Builder(CartActivity.this).
                                    setTitle("You have selected "+ itemsOrdered)
                                    .setMessage("Do you wish to continue?")
                                    .setPositiveButton("Yes", null)
                                    .setNegativeButton("Cancel", null)
                                    .show();

                            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Order order = new Order(total, number, userId, itemsOrdered);
                                    orders.add(order);

                                    reference.setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CartActivity.this, "Your Order Has Been Made", Toast.LENGTH_LONG).show();

                                                Intent i = new Intent(CartActivity.this, NonAdminHomeActivity.class);
                                                startActivity(i);
                                                cartRef.removeValue();


                                            } else {
                                                Toast.makeText(CartActivity.this, "Your Order Has Not Been Made", Toast.LENGTH_LONG).show(); }
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
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void checkUserDetails() {
        userRef =  FirebaseDatabase.getInstance().getReference("User");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {

                    User user = s.getValue(User.class);

                    if (userId.equals(user.getId())) {

                        if(user.getPaymentMethod().equals("")||user.getAddress().equals("")){
                            Toast.makeText(CartActivity.this, "User Details Needed", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(CartActivity.this, UserInfoActivity.class);
                            startActivity(i);

                        }else {
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }
}