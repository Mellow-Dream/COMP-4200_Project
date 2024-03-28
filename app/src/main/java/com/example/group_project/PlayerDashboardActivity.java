package com.example.group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerDashboardActivity extends AppCompatActivity {

    TextView tv_welcome;
    EditText et_studentID;
    Button btn_viewTesting, btn_logout;
    String studentID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_dashboard);

        // Init the elements
        tv_welcome = findViewById(R.id.textView_welcome);
        et_studentID = findViewById(R.id.editText_playerView_studentID);
        btn_viewTesting = findViewById(R.id.button_view_player_testing);
        btn_logout = findViewById(R.id.button_player_logout);

        // Set the welcome message
        Intent i = getIntent();
        tv_welcome.setText("Welcome " + i.getStringExtra("username") + "!");

        // Direct to activity based on button
        btn_viewTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First check that a student ID has been given
                studentID = et_studentID.getText().toString();
                if(studentID.isEmpty()){
                    // Make this an alert dialog instead
                    Toast.makeText(PlayerDashboardActivity.this, "Enter StudentID first!", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d("test_player_dash", "PlayerDashboard: Switching to Bodpod view");
                // Intent intent = new Intent(getApplicationContext(), PlayerViewBodpodActivity.class);
                Intent intent = new Intent(getApplicationContext(), PlayerViewActivity.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("username", i.getStringExtra("username"));
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test_player_dash", "PlayerDashboard: Logging out...");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set the studentID editText
        Intent i = getIntent();
        et_studentID.setText(i.getStringExtra("studentID"));
        tv_welcome.setText("Welcome " + i.getStringExtra("username") + "!");
    }
}