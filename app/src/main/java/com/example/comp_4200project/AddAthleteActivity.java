package com.example.comp_4200project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAthleteActivity extends AppCompatActivity {

    EditText et_studentID, et_studentName, et_dob, et_faculty, et_height, et_weight, et_team, et_jerseyNumber;
    Button btn_return, btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_athlete);

        // Init the EditText and Button
        et_studentID = findViewById(R.id.editText_studentID);
        et_studentName = findViewById(R.id.editText_studentName);
        et_dob = findViewById(R.id.editText_dob);
        et_faculty = findViewById(R.id.editText_faculty);
        et_height = findViewById(R.id.editText_height);
        et_weight = findViewById(R.id.editText_weight);
        et_team = findViewById(R.id.editText_team);
        et_jerseyNumber = findViewById(R.id.editText_jerseyNumber);

        btn_return = findViewById(R.id.button_add_athlete_return);
        btn_submit = findViewById(R.id.button_submit);

        // Create Database instance (context, DBName, CursorFactory, version)
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);

        // Create button operations
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoachDashboardActivity.class);
                startActivity(intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the new entry to the DB table
                Log.d("test_submit", "AddAthleteActivity: SUBMIT button has been initiated.");

                // Should do some data validation...

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
                    Log.d("test_submit", "AddAthleteActivity: New athlete failed: " + studentID);
                    Toast.makeText(AddAthleteActivity.this, "ERROR: Adding athlete to table unsuccessful", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("test_submit", "AddAthleteActivity: New athlete is successfully added: " + studentID);
                    Toast.makeText(AddAthleteActivity.this, "New athlete added!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}