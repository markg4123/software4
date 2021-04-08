package com.example.softwarepatterns;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    ArrayList<StockItem> stockItem;
    ArrayList<CartItem> cartItems = new ArrayList<>();
    Uri myUri;
    String uri;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    StorageReference mStorageRef;


    public CustomerAdapter(ArrayList<StockItem> stockItem){
        this.stockItem = stockItem;
    }
    @NonNull
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerlayout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder holder, int position) {
        final StockItem stockItems = stockItem.get(position);

        holder.stockTitleText.setText(stockItems.getTitle()+"\n");
        holder.stockCatText.setText("Category: "+stockItems.getCategory()+"\n");
        holder.stockManText.setText("Manufacturer: "+stockItems.getManufacturer()+"\n");
        holder.stockPriceText.setText("Price: €"+ stockItems.getPrice()+"\n");
        holder.numStockText.setText("Number in Stock: "+ stockItems.getNumStock()+"\n");


        uri = stockItems.getUrl();
        myUri = Uri.parse(uri);



        Picasso.get().load(myUri).into(holder.image);

        holder.reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = stockItems.getTitle();
                String man = stockItems.getManufacturer();

                Intent i = new Intent(v.getContext(), ReviewActivity.class);
                i.putExtra("title", title);
                i.putExtra("man", man);
                v.getContext().startActivity(i);


            }
        });

        holder.addtocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String title = stockItems.getTitle();
                int price = stockItems.getPrice();

                mAuth = FirebaseAuth.getInstance();
                reference = FirebaseDatabase.getInstance().getReference().child("CartItem").push();
                mStorageRef = FirebaseStorage.getInstance().getReference();

                CartItem cart = new CartItem(title,price);

                cartItems.add(cart);


                reference.setValue(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(v.getContext(), "Item added to basket", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(v.getContext(), CartActivity.class);
                            v.getContext().startActivity(i);
                        } else {
                            Toast.makeText(v.getContext(), "Error adding item", Toast.LENGTH_LONG).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "Error Due to: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return stockItem.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView stockTitleText, stockCatText, stockManText, stockPriceText, numStockText;
        public ImageView image;
        public Button addtocartButton,reviewButton;



        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            stockTitleText =(TextView) itemView.findViewById(R.id.stockTitleText);
            stockCatText =  (TextView)itemView.findViewById(R.id.stockCatText);
            stockManText =(TextView) itemView.findViewById(R.id.stockManText);
            stockPriceText = (TextView)itemView.findViewById(R.id.stockPriceText);
            image = (ImageView)itemView.findViewById(R.id.image);
            numStockText =(TextView) itemView.findViewById(R.id.numStockText);
            addtocartButton =(Button) itemView.findViewById(R.id.addtocartButton);
            reviewButton =(Button) itemView.findViewById(R.id.reviewButton);


        }

        @Override
        public void onClick(View v) {
            int position = this.getLayoutPosition();

        }
    }
}
