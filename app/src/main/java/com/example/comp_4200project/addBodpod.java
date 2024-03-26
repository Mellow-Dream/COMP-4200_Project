package com.example.comp_4200project;

import android.content.Intent;
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
    Button  btn_submit, btn_return;
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
        btn_return = findViewById(R.id.button_bodpod_return);

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
                long row = dbh.addNewBodpodTest(studentID,tee,ree,bf,ff);

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

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoachDashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
