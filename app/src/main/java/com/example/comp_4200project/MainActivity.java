package com.example.comp_4200project;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    ArrayList<MyData> dataSets = new ArrayList<>();
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataSets.add(new MyData("Card 1", R.drawable.cat, "yellow"));
        dataSets.add(new MyData("Card 2", R.drawable.dog, "blue"));
        dataSets.add(new MyData("Card 3", R.drawable.frog, "red"));


        MyAdapter myAdapter = new MyAdapter(dataSets, MainActivity.this);
        recyclerView.setAdapter(myAdapter);


    }
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/comp-4200-project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yanoxD123!";

    public static void main(String[] args) {
        insertAndRetrieveData("Colin Moran");
    }

    public static void insertAndRetrieveData(String name) {
        Connection conn = null;
        PreparedStatement insertStatement = null;
        PreparedStatement retrieveStatement = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String insertQuery = "INSERT INTO name (firstName) VALUES (?)";
            insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setString(1, name);
            insertStatement.executeUpdate();

            String retrieveQuery = "SELECT * FROM name";
            retrieveStatement = conn.prepareStatement(retrieveQuery);
            resultSet = retrieveStatement.executeQuery();

            while (resultSet.next()) {
                String retrievedName = resultSet.getString("firstName");
                System.out.println("Retrieved name: " + retrievedName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (retrieveStatement != null) retrieveStatement.close();
                if (insertStatement != null) insertStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}