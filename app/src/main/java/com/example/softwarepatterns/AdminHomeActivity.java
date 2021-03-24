package com.example.softwarepatterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    RecyclerView rcv2;
    DatabaseReference userRef;
    RecyclerView.Adapter adapter;
    ArrayList<StockItem> stockItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock_item);

        rcv2 = findViewById(R.id.rcv2);
        rcv2.setHasFixedSize(true);
        rcv2.setLayoutManager(new LinearLayoutManager(this));
        userRef =  FirebaseDatabase.getInstance().getReference("StockItem");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {

                    StockItem stockItem = s.getValue(StockItem.class);


                    stockItems.add(stockItem);
                    adapter = new EditAdapter(stockItems);
                    rcv2.setAdapter(adapter);
                    adapter.notifyItemInserted(stockItems.size() - 1);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(AdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i = new Intent(AdminHomeActivity.this, ViewCustomerActivity.class);
                startActivity(i);
                return true;
            case R.id.item2:
                Intent ii = new Intent(AdminHomeActivity.this, AddItemActivity.class);
                startActivity(ii);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}