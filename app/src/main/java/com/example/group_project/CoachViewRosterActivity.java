package com.example.group_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CoachViewRosterActivity extends AppCompatActivity {

    TextView tv_title, tv_roster_view;
    Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_view_roster);

        tv_title = findViewById(R.id.textView_roster_title);
        tv_roster_view = findViewById(R.id.textView_roster_view);
        btn_return = findViewById(R.id.button_roster_return);

        tv_title.setText("Men's Hockey Roster");    // Temp

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
        Cursor athleteCursor = dbh.displayAllAthleteData();

        // Check that data exists
        if(athleteCursor == null){
            // Something went wrong...
            Log.d("test_rosterView", "ViewRoster: Error when setting athleteCursor, aborting.");
            AlertDialog.Builder ad = new AlertDialog.Builder(CoachViewRosterActivity.this);
            ad.setTitle("ERROR");
            ad.setMessage("Could not get roster data.");
            ad.setCancelable(true);
            ad.show();
        }
        else if(athleteCursor.getCount() == 0) {
            // There is no roster data available
            Log.d("test_rosterView", "ViewRoster: No roster available.");
            tv_roster_view.setText("No roster available!");
        }
        else {
            // Show some of the roster data, don't need all of it
            Log.d("test_rosterView", "ViewRoster: Creating roster output...");
            String data = "";
            while(athleteCursor.moveToNext()) {
                String temp = "StudentID: " + athleteCursor.getString(1)
                            + "\nStudent Name: " + athleteCursor.getString(2)
                            + "\nHeight(cm): " + athleteCursor.getString(5)
                            + ", Weight(kg): " + athleteCursor.getString(6)
                            + "\nJersey Number: " + athleteCursor.getString(8);
                data += temp + "\n\n";
            }

            tv_roster_view.setText(data);
        }
    }
}