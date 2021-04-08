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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    ArrayList<StockItem> stockItem;
    Uri myUri;
    String uri;

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
            public void onClick(View v) {
                String title = stockItems.getTitle();
                int price = stockItems.getPrice();

                Intent i = new Intent(v.getContext(), CartActivity.class);
                i.putExtra("title", title);
                i.putExtra("price", price);
                v.getContext().startActivity(i);

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
