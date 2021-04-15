package com.example.softwarepatterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText searchText;
    String search;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        rcv2 = findViewById(R.id.rcv2);
        rcv2.setHasFixedSize(true);
        rcv2.setLayoutManager(new LinearLayoutManager(this));

        searchText = findViewById(R.id.searchText);
        searchButton = findViewById(R.id.searchButton);

        search = searchText.getText().toString();
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
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = searchText.getText().toString();
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot s : dataSnapshot.getChildren()) {

                            StockItem stockItem = s.getValue(StockItem.class);

                            if (search.equalsIgnoreCase(stockItem.getCategory()) ||
                                    search.equalsIgnoreCase(stockItem.getManufacturer()) ||
                                    search.equalsIgnoreCase(stockItem.getTitle())) {

                                stockItems.clear();

                                stockItems.add(stockItem);
                                adapter = new EditAdapter(stockItems);
                                rcv2.setAdapter(adapter);
                                adapter.notifyItemInserted(stockItems.size() - 1);
                            }

                            search.toLowerCase();
                            String title = stockItem.getTitle().toLowerCase();
                            String category = stockItem.getCategory().toLowerCase();

                            int i = 0;

                            for (int index = 0 ; index < search.length(); index++) {
                                Character currChar = search.charAt(index);
                                if (title.length() <= i || category.length() <= i)
                                    break;
                                    if (currChar.equals(title.charAt(i))||currChar.equals(category.charAt(i)))
                                    i++;

                            }

                            if (i >= title.length()||i >= category.length()){
                                stockItems.clear();

                                stockItems.add(stockItem);
                                adapter = new EditAdapter(stockItems);
                                rcv2.setAdapter(adapter);
                                adapter.notifyItemInserted(stockItems.size() - 1);
                            }


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(AdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });

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