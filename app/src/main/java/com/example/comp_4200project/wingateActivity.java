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

public class wingateActivity extends AppCompatActivity {

    TextView tv_title, tv_wingate_view;
    Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wingate_layout);

        tv_title = findViewById(R.id.textView_wingate_title);
        tv_wingate_view = findViewById(R.id.textView_wingate_view);
        btn_return = findViewById(R.id.button_wingate_return);

        tv_title.setText("Men's Hockey Wingate");

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
        Cursor wingateCursor = dbh.displayAllWingateData();

        // Check that data exists
        if(wingateCursor == null){
            // Something went wrong...
            Log.d("test_wingateView", "Wingate: Error when setting wingateCursor, aborting.");
            AlertDialog.Builder ad = new AlertDialog.Builder(wingateActivity.this);
            ad.setTitle("ERROR");
            ad.setMessage("Could not get wingate data.");
            ad.setCancelable(true);
            ad.show();
        }
        else if(wingateCursor.getCount() == 0) {
            // There is no roster data available
            Log.d("test_wingateiew", "ViewWingate: No wingate available.");
            tv_wingate_view.setText("No wingate available!");
        }
        else {
            // Show some of the roster data, don't need all of it
            Log.d("test_wingateView", "View Wingate: Creating wingate output...");
            String data = "";
            while(wingateCursor.moveToNext()) {
                String temp = "StudentID: " + wingateCursor.getString(1)
                        + "\nMinimum Power: " + wingateCursor.getString(2)
                        + "\nPeak Power: " + wingateCursor.getString(3)
                        + "\nAverage Power:  " + wingateCursor.getString(4);
                data += temp + "\n\n";
            }

            tv_wingate_view.setText(data);
        }
    }
}

