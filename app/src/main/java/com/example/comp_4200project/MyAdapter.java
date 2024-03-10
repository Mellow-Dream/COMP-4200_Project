package com.example.comp_4200project;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<MyData> dataList;
    Context context;
    public MyAdapter(ArrayList<MyData> data, Context context){
        this.dataList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyData data = dataList.get(position);

        holder.imageView.setImageResource(data.getImage());
        holder.textView.setText(data.getText());
        holder.buttView.setText(data.getButText());
        holder.textView.setTextColor(Color.WHITE);

        holder.buttView.setOnClickListener(new View.OnClickListener() {
            boolean isWhite = true;

            @Override
            public void onClick(View v) {
                if (isWhite) {
                    holder.textView.setTextColor(Color.parseColor(data.getButText()));
                } else {
                    holder.textView.setTextColor(Color.WHITE);
                }
                isWhite = !isWhite;
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            boolean isCard = true;

            @Override
            public void onClick(View v) {
                String msg = "You clicked Card "+(position+1);
                Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                if (isCard) {
                    holder.textView.setText("Text " + (position + 1));
                } else {
                    holder.textView.setText("Card " + (position + 1));
                }
                isCard = !isCard;
            }
        });



        }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        CardView cardView;
        Button buttView;
        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.tv_card);
            cardView = itemView.findViewById(R.id.card_view);
            buttView = itemView.findViewById(R.id.but1);
        }
    }
}
