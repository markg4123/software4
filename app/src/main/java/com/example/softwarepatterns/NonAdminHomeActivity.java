package com.example.softwarepatterns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NonAdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_admin_home);
    }
}