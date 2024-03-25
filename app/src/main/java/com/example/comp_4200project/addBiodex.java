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

public class addBiodex extends AppCompatActivity {

    EditText et_studentID,et_LQM,et_RQM,et_LHM,et_RHM;
    Button btn_submit, btn_getBio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_biodex_layout);

        et_studentID = findViewById(R.id.editText_studentID);
        et_LQM = findViewById(R.id.editText_L_quad_max);
        et_RQM = findViewById(R.id.editText_R_quad_max);
        et_LHM = findViewById(R.id.editText_L_ham_max);
        et_RHM = findViewById(R.id.editText_R_ham_max);

        btn_submit = findViewById(R.id.button_submitBio);
        btn_getBio = findViewById(R.id.button_getBiodex);
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the new entry to the DB table
                Log.d("test_submit", "MainActivity: SUBMIT button has been initiated.");

                // First get all the values to be added (assumed not null)
                String studentID = et_studentID.getText().toString();

                Float lqm = Float.parseFloat(et_LQM.getText().toString());
                Float rqm = Float.parseFloat(et_RQM.getText().toString());
                Float lhm = Float.parseFloat(et_LHM.getText().toString());
                Float rhm = Float.parseFloat(et_RHM.getText().toString());

                // Now add the values to the athlete table (8 values)
                long row = dbh.addBiodexData(studentID,lqm,rqm,lhm,rhm);

                if(row < 0){
                    // An error occurred
                    Log.d("test_submit", "MainActivity: New athlete failed: " + studentID);
                    Toast.makeText(addBiodex.this, "ERROR: Adding athlete to table unsuccessful", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("test_submit", "MainActivity: New athlete is successfully added: " + studentID);
                    Toast.makeText(addBiodex.this, "New athlete added!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_getBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the table data
                Log.d("test_display", "Biodex: GetData button has been initiated.");

                Cursor cursor = dbh.displayBiodexData();
                String data = "";

                if(cursor == null){
                    // An error occurred
                    Log.d("test_display", "Biodex: displayBiodex has returned null.");
                    Toast.makeText(addBiodex.this, "ERROR: Cannot get table data!", Toast.LENGTH_LONG).show();
                } else if(cursor.getCount() == 0) {
                    // There is no data to be shown
                    Log.d("test_display", "Biodex: Athlete table contains no data.");
                    // Toast.makeText(MainActivity.this, "No Athlete data to display!", Toast.LENGTH_LONG).show();
                    data += "No data!";
                } else {
                    while(cursor.moveToNext()){     // Move to next row
                        Log.d("test_display", "Biodex: Creating data output...");
                        String temp = "StudentID: " + cursor.getString(0) + "\nLeft Quad Max: " + cursor.getString(1)
                                + "\nRight Quad Max: " + cursor.getString(2) + "\nLeft Ham Max: " + cursor.getString(3)
                                + "\nRight Ham Max: "+ cursor.getString(4);
                        data += temp + "\n\n";
                    }
                }

                Log.d("test_display", "Biodex: displaying Biodex data...");

                AlertDialog.Builder ad = new AlertDialog.Builder(addBiodex.this);
                ad.setTitle("Biodex Data");
                ad.setMessage(data);
                ad.setCancelable(true);
                ad.show();
            }
        });



    }
}
