package com.example.comp_4200project;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ArrayList<MyData> dataSets = new ArrayList<>();
    private EditText StudentID_editText;
    private EditText StudentName_editText;
    private EditText firstNAme_editText;
    private EditText lastName_editText;
    private EditText dateofBirth_editText;
    private EditText faculty_editText;
    private EditText height_editText;
    private EditText weight_editText;
    private EditText team_editText;
    private EditText jerseyNumber_editText;

    Button insert_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StudentID_editText = findViewById(R.id.StudentID_editText);

        insert_button = findViewById(R.id.insert_button);
        insert_button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText studentIdEditText = findViewById(R.id.StudentID_editText);
                int studentId = Integer.parseInt(studentIdEditText.getText().toString());

                EditText studentNameEditText = findViewById(R.id.studentName_editText);
                String studentName = studentNameEditText.getText().toString();

                EditText firstNameEditText = findViewById(R.id.firstName_editText);
                String firstName = firstNameEditText.getText().toString();

                EditText lastNameEditText = findViewById(R.id.lastName_editText);
                String lastName = lastNameEditText.getText().toString();

                EditText dateofBirthEditText = findViewById(R.id.dateofBirth_editText);
                String dateofBirth = dateofBirthEditText.getText().toString();

                EditText facultyEditText = findViewById(R.id.facultyText_editText);
                String faculty = facultyEditText.getText().toString();

                EditText heightEditText = findViewById(R.id.height_editText);
                int height = Integer.parseInt(heightEditText.getText().toString());

                EditText weightEditText = findViewById(R.id.weight_editText);
                int weight = Integer.parseInt(weightEditText.getText().toString());

                EditText teamEditText = findViewById(R.id.team_editText);
                String team = teamEditText.getText().toString();

                EditText jerseyNumberText = findViewById(R.id.jerseyNumber_editText);
                int jerseyNumber = Integer.parseInt(jerseyNumberText.getText().toString());

                DatabasExecutor.insertAthleteDataAsync(studentId, studentName, firstName, lastName, dateofBirth, faculty, height, weight, team, jerseyNumber, new DatabasExecutor.OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        // Handle completion, e.g., show a toast message
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }));

}
}
