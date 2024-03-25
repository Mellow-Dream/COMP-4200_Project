package com.example.comp_4200project;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_studentID, et_studentName, et_dob, et_faculty, et_height, et_weight, et_team, et_jerseyNumber;
    Button btn_submit, btn_getData, btn_barChart,btn_mainMenu;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init the EditText and Button
        et_studentID = findViewById(R.id.editText_studentID);
        et_studentName = findViewById(R.id.editText_studentName);
        et_dob = findViewById(R.id.editText_dob);
        et_faculty = findViewById(R.id.editText_faculty);
        et_height = findViewById(R.id.editText_height);
        et_weight = findViewById(R.id.editText_weight);
        et_team = findViewById(R.id.editText_team);
        et_jerseyNumber = findViewById(R.id.editText_jerseyNumber);

        btn_submit = findViewById(R.id.button_submit);
        btn_getData = findViewById(R.id.button_retrieve);
        btn_barChart = findViewById(R.id.button_barChart);
        btn_mainMenu = findViewById(R.id.button_mainMenu);


        barChart = findViewById(R.id.bar_chart);

        // Create Database instance (context, DBName, CursorFactory, version)
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);

        // Create button operations
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the new entry to the DB table
                Log.d("test_submit", "MainActivity: SUBMIT button has been initiated.");

                // First get all the values to be added (assumed not null)
                String studentID = et_studentID.getText().toString();
                String studentName = et_studentName.getText().toString();
                String dob = et_dob.getText().toString();
                String faculty = et_faculty.getText().toString();
                Float height = Float.parseFloat(et_height.getText().toString());
                Float weight = Float.parseFloat(et_weight.getText().toString());
                String team = et_team.getText().toString();
                int jerseyNumber = Integer.parseInt(et_jerseyNumber.getText().toString());

                // Now add the values to the athlete table (8 values)
                long row = dbh.addNewAthlete(studentID, studentName, dob, faculty, height, weight, team, jerseyNumber);

                if(row < 0){
                    // An error occurred
                    Log.d("test_submit", "MainActivity: New athlete failed: " + studentID);
                    Toast.makeText(MainActivity.this, "ERROR: Adding athlete to table unsuccessful", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("test_submit", "MainActivity: New athlete is successfully added: " + studentID);
                    Toast.makeText(MainActivity.this, "New athlete added!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the table data
                Log.d("test_display", "MainActivity: GetData button has been initiated.");

                Cursor cursor = dbh.displayAthleteData();
                String data = "";

                if(cursor == null){
                    // An error occurred
                    Log.d("test_display", "MainActivity: displayAthleteData has returned null.");
                    Toast.makeText(MainActivity.this, "ERROR: Cannot get table data!", Toast.LENGTH_LONG).show();
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

                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setTitle("Athlete Table Data");
                ad.setMessage(data);
                ad.setCancelable(true);
                ad.show();
            }
        });

        btn_barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, barChartActivity.class));
            }
        });
        btn_mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, mainMenu.class));
            }
        });


    }
}