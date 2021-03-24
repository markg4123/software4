package com.example.softwarepatterns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText emailText, passwordText;
    Button loginButton1;
    FirebaseAuth mAuth;
    String email, password, userID;
    DatabaseReference userRef;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordText = findViewById(R.id.passwordText2);
        emailText = findViewById(R.id.emailText2);
        loginButton1 = findViewById(R.id.loginButton1);
        mAuth = FirebaseAuth.getInstance();

        userRef =  FirebaseDatabase.getInstance().getReference("User");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                if (mUser == null) {

                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, EditActivity.class);
                    startActivity(i);
                } else {

                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }

        };

        loginButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailText.getText().toString();
                password = passwordText.getText().toString();

                if (email.isEmpty() && password.isEmpty())
                    Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
                else if (email.isEmpty()) {
                    emailText.setError("Please enter your email address");
                    emailText.requestFocus();
                } else if (password.isEmpty()) {
                    passwordText.setError("Please enter your password");
                    passwordText.requestFocus();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Error Please Try Again", Toast.LENGTH_LONG).show();
                            }
                            userRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot u : dataSnapshot.getChildren()) {
                                        userID = mAuth.getCurrentUser().getUid();

                                        User user = u.getValue(User.class);
                                        if(userID.equals(user.getId())) {
                                            if (user.getAdmin().equals("Admin User")) {
                                                Intent intent1 = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                                startActivity(intent1);
                                            }
                                            if (user.getAdmin().equals("Standard User")) {
                                                Intent intent1 = new Intent(LoginActivity.this, NonAdminHomeActivity.class);
                                                startActivity(intent1);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    });


                }
            }
        });




    }
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}