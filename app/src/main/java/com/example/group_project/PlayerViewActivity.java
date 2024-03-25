package com.example.group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayerViewActivity extends AppCompatActivity {

    TextView tv_title;
    Button btn_return;
    String studentID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_view);

        tv_title = findViewById(R.id.textView_player_view);
        btn_return = findViewById(R.id.button_player_view_return);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayerDashboardActivity.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // First, get the student's ID (Should exist)
        Intent i = getIntent();
        studentID = i.getStringExtra("studentID");
        tv_title.setText("Displaying Testing Graphs for " + studentID);

        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);

        // Get the player's testing data
        Log.d("test_playerView", "PlayerView: Creating the graphs...");
        Cursor bodpodCursor, wingateCursor, biodexCursor;
        bodpodCursor = dbh.displayBodpodTest(studentID);
        wingateCursor = dbh.displayWingateTest(studentID);
        biodexCursor = dbh.displayBiodexTest(studentID);

        // Then, display their data graphs

        Log.d("test_playerView", "PlayerView: Displaying the graphs...");
    }
}