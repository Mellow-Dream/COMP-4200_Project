package com.example.group_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_username;       // Only care about username
    Button btn_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init elements
        et_username = findViewById(R.id.editText_username);
        btn_login = findViewById(R.id.button_login);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.uowlogo);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Login the user to the corresponding activity (player or coach)
                String user = et_username.getText().toString();

                if(user.equals("coach")){
                    // Direct to the coach activity
                    Intent intent = new Intent(MainActivity.this, CoachDashboardActivity.class);
                    intent.putExtra("username", user);

                    startActivity(intent);
                }
                else if(user.equals("") || user.contains(" ")) {       // Empty or containing blank spaces not valid
                    AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                    ad.setTitle("Login Error");
                    ad.setMessage("Invalid Username!");
                    ad.setCancelable(true);
                    ad.show();

                    et_username.setText("");
                } else {
                    // Valid player name
                    Intent intent = new Intent(MainActivity.this, PlayerDashboardActivity.class);
                    intent.putExtra("username", user);

                    startActivity(intent);
                }
            }
        });
    }
}