package com.example.softwarepatterns;

import android.content.Intent;
import android.net.Uri;
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

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.ViewHolder>  {
    ArrayList<StockItem> stockItem;
    Uri myUri;
    String uri,title,manufacturer,category,key;
    int numStock,price;



    public EditAdapter(ArrayList<StockItem> stockItem){
        this.stockItem = stockItem;
    }

    @NonNull
    @Override
    public EditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.editlayout, parent, false);

        return new EditAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final EditAdapter.ViewHolder holder, final int position) {
        final StockItem stockItems = stockItem.get(position);

        holder.stockTitleText.setText(stockItems.getTitle()+"\n");
        holder.stockCatText.setText("Category: "+stockItems.getCategory()+"\n");
        holder.stockManText.setText("Manufacturer: "+stockItems.getManufacturer()+"\n");
        holder.stockPriceText.setText("Price: €"+ stockItems.getPrice()+"\n");
        holder.numStockText.setText("Number in Stock: "+ stockItems.getNumStock()+"\n");




        uri = stockItems.getUrl();
        myUri = Uri.parse(uri);



        Picasso.get().load(myUri).into(holder.image);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StockItem stockItems = stockItem.get(position);

                uri = stockItems.getUrl();
                myUri = Uri.parse(uri);
                title = stockItems.getTitle();
                category = stockItems.getCategory();
                manufacturer = stockItems.getManufacturer();
                price = stockItems.getPrice();
                numStock = stockItems.getNumStock();
                key = stockItems.getKey();

                Picasso.get().load(myUri).into(holder.image);

                Intent i = new Intent(v.getContext(), EditActivity.class);
                i.putExtra("title", title);
                i.putExtra("price", price);
                i.putExtra("numStock", numStock);
                i.putExtra("cat", category);
                i.putExtra("man", manufacturer);
                i.putExtra("uri", myUri);
                i.putExtra("key", key);


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
        public Button editButton;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            stockTitleText =(TextView) itemView.findViewById(R.id.stockTitleText);
            stockCatText =  (TextView)itemView.findViewById(R.id.stockCatText);
            stockManText =(TextView) itemView.findViewById(R.id.stockManText);
            stockPriceText = (TextView)itemView.findViewById(R.id.stockPriceText);
            image = (ImageView)itemView.findViewById(R.id.image);
            numStockText =(TextView) itemView.findViewById(R.id.numStockText);
            editButton = (Button) itemView.findViewById(R.id.editButton);

        }

        @Override
        public void onClick(View v) {
            int position = this.getLayoutPosition();

        }
    }
}
