package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfoActivity extends AppCompatActivity {
    TextView userNameText, userEmailText, paymentText, addressText;
    Button updateButton;
    String userId;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference userRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userNameText = findViewById(R.id.userNameText);
        userEmailText = findViewById(R.id.userEmailText);
        paymentText = findViewById(R.id.paymentText);
        addressText = findViewById(R.id.addressText);
        updateButton = findViewById(R.id.updateButton);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("User");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User users = userSnapshot.getValue(User.class);
                    if (userId.equals(users.getId())) {

                        userNameText.setText(users.getName());
                        userEmailText.setText((users.getEmail()));
                        paymentText.setText(users.getPaymentMethod());
                        addressText.setText(users.getAddress());


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String name = userNameText.getText().toString();
                String email = userEmailText.getText().toString();
                String payment = paymentText.getText().toString();
                String address = addressText.getText().toString();



                user = mAuth.getCurrentUser();


                DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("User").child(userId);

                fireDB.child("name").setValue(name);
                fireDB.child("email").setValue(email);
                fireDB.child("paymentMethod").setValue(payment);
                fireDB.child("address").setValue(address);

                Toast.makeText(UserInfoActivity.this, "User profile updated!", Toast.LENGTH_LONG).show();
            }

        });

    }
}