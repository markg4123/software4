package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    TextView itemName, itemManufacturer,editTextTextMultiLine;
    Button submitButton;
    RatingBar ratingBar;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    ArrayList<Review> reviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Review").push();

        String title = getIntent().getExtras().getString("title");
        String manufacturer = getIntent().getExtras().getString("man");

        itemName = findViewById(R.id.itemName);
        itemManufacturer = findViewById(R.id.itemManufacturer);
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
        submitButton = findViewById(R.id.submitButton);
        ratingBar = findViewById(R.id.ratingBar);

        itemName.setText("Item: "+title);
        itemManufacturer.setText("Manufacturer: "+manufacturer);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = itemName.getText().toString();
                String man = itemManufacturer.getText().toString();
                String review = editTextTextMultiLine.getText().toString();
                float rating = ratingBar.getRating();


                if (review.equals("")) {
                    Toast.makeText(ReviewActivity.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
                } else {
                    Review rev = new Review(rating, review, title, man);

                    reviews.add(rev);


                    reference.setValue(rev).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ReviewActivity.this, "Your Review Has Been Submitted", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ReviewActivity.this, NonAdminHomeActivity.class);
                                startActivity(i);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ReviewActivity.this, "Submitting review failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });



                }
            }
        });


        }
    }