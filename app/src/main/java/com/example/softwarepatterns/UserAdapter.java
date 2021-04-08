package com.example.softwarepatterns;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<User> users;


    public UserAdapter(ArrayList<User> users){
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlayout, parent, false);

        return new UserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user= users.get(position);

        holder.nameText.setText("Name: "+ user.getName() +"\n");
        holder.emailText.setText("Email Address: "+ user.getEmail() +"\n");
        holder.addressText.setText("Shipping Address: "+ user.getAddress()+"\n");
        holder.paymentText.setText("Payment Method: "+ user.getPaymentMethod() +"\n");

        holder.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), OrderHistoryActivity.class);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameText, emailText, paymentText, addressText;
        Button orderButton;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.nameText);
            emailText = (TextView) itemView.findViewById(R.id.emailText);
            paymentText = (TextView) itemView.findViewById(R.id.paymentText);
            addressText = (TextView) itemView.findViewById(R.id.addressText);
            orderButton = (Button) itemView.findViewById(R.id.orderButton);


        }

        @Override
        public void onClick(View v) {
            int position = this.getLayoutPosition();

        }
    }
}
