package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NonAdminHomeActivity extends AppCompatActivity {
        RecyclerView rcv3;
        DatabaseReference userRef;
        ArrayList<StockItem> stockItems = new ArrayList<>();
        RecyclerView.Adapter adapter;

        EditText searchText2;
        String search, order;
        Button searchButton2, ascendingButton, descendingButton;
        Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_admin_home);


        rcv3 = findViewById(R.id.rcv3);
        rcv3.setHasFixedSize(true);
        rcv3.setLayoutManager(new LinearLayoutManager(this));

        searchText2 = findViewById(R.id.searchText2);
        searchButton2 = findViewById(R.id.searchButton2);
        ascendingButton = findViewById(R.id.accendingButton);
        descendingButton = findViewById(R.id.decendingButton);
        spinner = findViewById(R.id.spinner);

        userRef =  FirebaseDatabase.getInstance().getReference("StockItem");

        final String[] List={"Title", "Manufacturer", "Price"};


        spinner.setAdapter(new ArrayAdapter<>(NonAdminHomeActivity.this, android.R.layout
                .simple_spinner_dropdown_item, List));

        //ascending on click listener
        ascendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            order = spinner.getSelectedItem().toString();

            //ascending price
            if(order.equals("Price")){

                userRef.orderByChild("price").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        stockItems.clear();
                        for (DataSnapshot s : dataSnapshot.getChildren()) {

                            StockItem stockItem = s.getValue(StockItem.class);
                            stockItems.add(stockItem);
                        }
                        Collections.reverse(stockItems);
                        adapter = new CustomerAdapter(stockItems);
                        rcv3.setAdapter(adapter);
                        adapter.notifyItemInserted(stockItems.size() - 1);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(NonAdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
                //ascending title
                if(order.equals("Title")){

                    userRef.orderByChild("title").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            stockItems.clear();
                            for (DataSnapshot s : dataSnapshot.getChildren()) {

                                StockItem stockItem = s.getValue(StockItem.class);
                                stockItems.add(stockItem);
                            }
                            adapter = new CustomerAdapter(stockItems);
                            rcv3.setAdapter(adapter);
                            adapter.notifyItemInserted(stockItems.size() - 1);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(NonAdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //ascending manufacturer
                if(order.equals("Manufacturer")){

                    userRef.orderByChild("manufacturer").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            stockItems.clear();
                            for (DataSnapshot s : dataSnapshot.getChildren()) {

                                StockItem stockItem = s.getValue(StockItem.class);
                                stockItems.add(stockItem);
                            }
                            adapter = new CustomerAdapter(stockItems);
                            rcv3.setAdapter(adapter);
                            adapter.notifyItemInserted(stockItems.size() - 1);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(NonAdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });



        //descending on click listener
        ascendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                order = spinner.getSelectedItem().toString();

                //descending price
                if(order.equals("Price")){

                    userRef.orderByChild("price").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            stockItems.clear();
                            for (DataSnapshot s : dataSnapshot.getChildren()) {

                                StockItem stockItem = s.getValue(StockItem.class);
                                stockItems.add(stockItem);
                            }
                            adapter = new CustomerAdapter(stockItems);
                            rcv3.setAdapter(adapter);
                            adapter.notifyItemInserted(stockItems.size() - 1);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(NonAdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //descending title
                if(order.equals("Title")){

                    userRef.orderByChild("title").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            stockItems.clear();
                            for (DataSnapshot s : dataSnapshot.getChildren()) {

                                StockItem stockItem = s.getValue(StockItem.class);
                                stockItems.add(stockItem);
                            }
                            Collections.reverse(stockItems);
                            adapter = new CustomerAdapter(stockItems);
                            rcv3.setAdapter(adapter);
                            adapter.notifyItemInserted(stockItems.size() - 1);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(NonAdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //descending manufacturer
                if(order.equals("Manufacturer")){

                    userRef.orderByChild("manufacturer").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            stockItems.clear();
                            for (DataSnapshot s : dataSnapshot.getChildren()) {

                                StockItem stockItem = s.getValue(StockItem.class);
                                stockItems.add(stockItem);
                            }
                            Collections.reverse(stockItems);
                            adapter = new CustomerAdapter(stockItems);
                            rcv3.setAdapter(adapter);
                            adapter.notifyItemInserted(stockItems.size() - 1);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(NonAdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        searchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = searchText2.getText().toString();
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
                                rcv3.setAdapter(adapter);
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
                                rcv3.setAdapter(adapter);
                                adapter.notifyItemInserted(stockItems.size() - 1);
                            }


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(NonAdminHomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

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