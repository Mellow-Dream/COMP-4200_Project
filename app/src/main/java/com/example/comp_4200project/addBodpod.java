package com.example.comp_4200project;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class addBodpod extends AppCompatActivity {

    EditText  et_studentID, et_tee, et_ree, et_bodyFat, et_fatFree;
    Button  btn_submit, btn_getBiopod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bodpod_layout);

        et_studentID = findViewById(R.id.editText_studentID);
        et_tee = findViewById(R.id.editText_tee);
        et_ree = findViewById(R.id.editText_ree);
        et_bodyFat = findViewById(R.id.editText_bodyFat);
        et_fatFree = findViewById(R.id.editText_fatFree);

        btn_submit = findViewById(R.id.button_submitBiopod);
        btn_getBiopod = findViewById(R.id.button_getBiopod);

        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the new entry to the DB table
                Log.d("test_submit", "MainActivity: SUBMIT button has been initiated.");

                // First get all the values to be added (assumed not null)
                String studentID = et_studentID.getText().toString();

                int tee = Integer.parseInt(et_tee.getText().toString());
                int ree = Integer.parseInt(et_ree.getText().toString());
                Float bf = Float.parseFloat(et_bodyFat.getText().toString());
                Float ff = Float.parseFloat(et_fatFree.getText().toString());

                // Now add the values to the athlete table (8 values)
                long row = dbh.addBodpodData(studentID,tee,ree,bf,ff);

                if(row < 0){
                    // An error occurred
                    Log.d("test_submit", "Bodpod: New athlete failed: " + studentID);
                    Toast.makeText(addBodpod.this, "ERROR: Adding athlete to table unsuccessful", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("test_submit", "Bodpod: New athlete is successfully added: " + studentID);
                    Toast.makeText(addBodpod.this, "New athlete added!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_getBiopod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the table data
                Log.d("test_display", "Biodex: GetData button has been initiated.");

                Cursor cursor = dbh.displayBodpodData();
                String data = "";

                if(cursor == null){
                    // An error occurred
                    Log.d("test_display", "Bodpod: displayBodpod has returned null.");
                    Toast.makeText(addBodpod.this, "ERROR: Cannot get table data!", Toast.LENGTH_LONG).show();
                } else if(cursor.getCount() == 0) {
                    // There is no data to be shown
                    Log.d("test_display", "Bodpod: Athlete table contains no data.");
                    // Toast.makeText(MainActivity.this, "No Athlete data to display!", Toast.LENGTH_LONG).show();
                    data += "No data!";
                } else {
                    while(cursor.moveToNext()){     // Move to next row
                        Log.d("test_display", "Bodpob: Creating data output...");
                        String temp = "StudentID: " + cursor.getString(0) + "\nTee: " + cursor.getString(1)
                                + "\nRee: " + cursor.getString(2) + "\nBody Fat: " + cursor.getString(3)
                                + "\nFat Free: "+ cursor.getString(4);
                        data += temp + "\n\n";
                    }
                }

                Log.d("test_display", "Bodpod: displaying Bodpod data...");

                AlertDialog.Builder ad = new AlertDialog.Builder(addBodpod.this);
                ad.setTitle("bodpod Data");
                ad.setMessage(data);
                ad.setCancelable(true);
                ad.show();
            }
        });

    }
}
