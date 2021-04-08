package com.example.softwarepatterns;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<CartItem> cartItems;

    public CartAdapter(ArrayList<CartItem> cartItems){
        this.cartItems = cartItems;
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlayout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        final CartItem cartItem = cartItems.get(position);

        holder.cartTitleText.setText("Item: " + cartItem.getTitle());
        holder.cartPriceText.setText("Price: €" + cartItem.getPrice());

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView cartTitleText,cartPriceText;



        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            cartTitleText =(TextView) itemView.findViewById(R.id.cartTitleText);
            cartPriceText =  (TextView)itemView.findViewById(R.id.cartPriceText);

        }

        @Override
        public void onClick(View v) {
            int position = this.getLayoutPosition();

        }
    }
}
