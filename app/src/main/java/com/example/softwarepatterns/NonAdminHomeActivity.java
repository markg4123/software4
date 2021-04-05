package com.example.softwarepatterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NonAdminHomeActivity extends AppCompatActivity {
        RecyclerView rcv3;
        DatabaseReference userRef;
        ArrayList<StockItem> stockItems = new ArrayList<>();
        RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_admin_home);


        rcv3 = findViewById(R.id.rcv3);
        rcv3.setHasFixedSize(true);
        rcv3.setLayoutManager(new LinearLayoutManager(this));
        userRef =  FirebaseDatabase.getInstance().getReference("StockItem");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {

                    StockItem stockItem = s.getValue(StockItem.class);


                    stockItems.add(stockItem);
                    adapter = new CustomerAdapter(stockItems);
                    rcv3.setAdapter(adapter);
                    adapter.notifyItemInserted(stockItems.size() - 1);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(NonAdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i = new Intent(NonAdminHomeActivity.this, UserInfoActivity.class);
                startActivity(i);
                return true;
            case R.id.item2:
                Intent ii = new Intent(NonAdminHomeActivity.this, CartActivity.class);
                startActivity(ii);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}