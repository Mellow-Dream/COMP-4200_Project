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

public class addWingate extends AppCompatActivity {

    EditText et_studentID,et_mp,et_pp,et_ap;
    Button btn_submit,btn_getWingateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wingate_layout);

        et_studentID = findViewById(R.id.editText_studentID);
        et_mp =  findViewById(R.id.editText_minPower);
        et_pp = findViewById(R.id.editText_peakPower);
        et_ap = findViewById(R.id.editText_avgPower);

        btn_submit = findViewById(R.id.button_submitWingate);
        btn_getWingateData = findViewById(R.id.button_getWingate);

        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the new entry to the DB table
                Log.d("test_submit", "Wingate: SUBMIT button has been initiated.");

                // First get all the values to be added (assumed not null)
                String studentID = et_studentID.getText().toString();


                Float mp  = Float.parseFloat(et_mp.getText().toString());
                Float pp = Float.parseFloat(et_pp.getText().toString());
                Float ap = Float.parseFloat(et_ap.getText().toString());

                // Now add the values to the athlete table (8 values)
                long row = dbh.addWingateData(studentID,mp,pp,ap);

                if(row < 0){
                    // An error occurred
                    Log.d("test_submit", "Wingate: New athlete failed: " + studentID);
                    Toast.makeText(addWingate.this, "ERROR: Adding athlete to table unsuccessful", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("test_submit", "Wingate: New athlete is successfully added: " + studentID);
                    Toast.makeText(addWingate.this, "New athlete added!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_getWingateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the table data
                Log.d("test_display", "Wingate: GetData button has been initiated.");

                Cursor cursor = dbh.displayWingateData();
                String data = "";

                if(cursor == null){
                    // An error occurred
                    Log.d("test_display", "Wingate: displayBodpod has returned null.");
                    Toast.makeText(addWingate.this, "ERROR: Cannot get table data!", Toast.LENGTH_LONG).show();
                } else if(cursor.getCount() == 0) {
                    // There is no data to be shown
                    Log.d("test_display", "Wingate: Athlete table contains no data.");
                    // Toast.makeText(MainActivity.this, "No Athlete data to display!", Toast.LENGTH_LONG).show();
                    data += "No data!";
                } else {
                    while(cursor.moveToNext()){     // Move to next row
                        Log.d("test_display", "Wingate: Creating data output...");
                        String temp = "StudentID: " + cursor.getString(0) + "\nMinimum Power: " + cursor.getString(1)
                                + "\nPeak Power: " + cursor.getString(2) + "\nAverage Power: " + cursor.getString(3);
                        data += temp + "\n\n";
                    }
                }

                Log.d("test_display", "Wingate: displaying Wingate data...");

                AlertDialog.Builder ad = new AlertDialog.Builder(addWingate.this);
                ad.setTitle("Wingate Data");
                ad.setMessage(data);
                ad.setCancelable(true);
                ad.show();
            }
        });

    }
}