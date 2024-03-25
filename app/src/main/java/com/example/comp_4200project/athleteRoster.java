package com.example.comp_4200project;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class athleteRoster extends AppCompatActivity {

    Button btn_viewAthleteData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.athlete_layout);
        btn_viewAthleteData = findViewById(R.id.button_viewAthleteData);
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        btn_viewAthleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the table data
                Log.d("test_display", "MainActivity: GetData button has been initiated.");

                Cursor cursor = dbh.displayAthleteData();
                String data = "";

                if(cursor == null){
                    // An error occurred
                    Log.d("test_display", "MainActivity: displayAthleteData has returned null.");
                    Toast.makeText(athleteRoster.this, "ERROR: Cannot get table data!", Toast.LENGTH_LONG).show();
                } else if(cursor.getCount() == 0) {
                    // There is no data to be shown
                    Log.d("test_display", "MainActivity: Athlete table contains no data.");
                    // Toast.makeText(MainActivity.this, "No Athlete data to display!", Toast.LENGTH_LONG).show();
                    data += "No data!";
                } else {
                    while(cursor.moveToNext()){     // Move to next row
                        Log.d("test_display", "MainActivity: Creating data output...");
                        String temp = "StudentID: " + cursor.getString(1) + "\nStudent Name: " + cursor.getString(2)
                                + "\nDate of Birth: " + cursor.getString(3) + "\nFaculty: " + cursor.getString(4)
                                + "\nHeight(cm): " + cursor.getString(5) + ", Weight(kg): " + cursor.getString(6)
                                + "\nTeam: " + cursor.getString(7) + ", Jersey: " + cursor.getString(8);
                        data += temp + "\n\n";
                    }
                }

                Log.d("test_display", "MainActivity: displaying Athlete data...");

                AlertDialog.Builder ad = new AlertDialog.Builder(athleteRoster.this);
                ad.setTitle("Athlete Table Data");
                ad.setMessage(data);
                ad.setCancelable(true);
                ad.show();
            }
        });
    }
}