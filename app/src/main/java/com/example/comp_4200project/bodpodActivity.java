package com.example.comp_4200project;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class bodpodActivity extends AppCompatActivity {

    TextView tv_title, tv_bodpod_view;
    Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodpod_layout);

        tv_title = findViewById(R.id.textView_bodpod_title);
        tv_bodpod_view = findViewById(R.id.textView_bodpod_view);
        btn_return = findViewById(R.id.button_bodpod_return);

        tv_title.setText("Men's Hockey Bodpod");

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoachDashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get the team roster and display info in the main textView
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        Cursor bodpodCursor = dbh.displayAllBodpodData();

        // Check that data exists
        if(bodpodCursor == null){
            // Something went wrong...
            Log.d("test_rosterView", "ViewRoster: Error when setting athleteCursor, aborting.");
            AlertDialog.Builder ad = new AlertDialog.Builder(bodpodActivity.this);
            ad.setTitle("ERROR");
            ad.setMessage("Could not get bodpod data.");
            ad.setCancelable(true);
            ad.show();
        }
        else if(bodpodCursor.getCount() == 0) {
            // There is no roster data available
            Log.d("test_bodpodView", "ViewBodpod: No bodpod available.");
            tv_bodpod_view.setText("No bodpod available!");
        }
        else {
            // Show some of the roster data, don't need all of it
            Log.d("test_rosterView", "ViewRoster: Creating roster output...");
            String data = "";
            while(bodpodCursor.moveToNext()) {
                String temp = "StudentID: " + bodpodCursor.getString(1)
                        + "\nTee: " + bodpodCursor.getString(2)
                        + "\nRee: " + bodpodCursor.getString(3)
                        + "\nBody Fat: " + bodpodCursor.getString(4)
                        + "\nFat Free " + bodpodCursor.getString(5);
                data += temp + "\n\n";
            }

            tv_bodpod_view.setText(data);
        }
    }
}
