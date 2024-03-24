/*package com.example.comp_4200project;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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


        holder.buttView.setText(data.getButText());
        holder.buttView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAthleteToDatabase(data);
            }
        });
        }

        public void addAthleteToDatabase(MyData data){

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/comp-4200-project", "admin", "admin123!");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO athletes (studentID, studentName, firstName, lastName, birthdate, faculty, height, weight, team, jerseyNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                statement.setInt(1, data.getStudentID());
                // Set other parameters similarly
                // Execute the statement
                statement.executeUpdate();

                // Close the connections
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        Button buttView;
        TextView studentID_textView;
        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            buttView = itemView.findViewById(R.id.but1);
        }
    }

}*/

