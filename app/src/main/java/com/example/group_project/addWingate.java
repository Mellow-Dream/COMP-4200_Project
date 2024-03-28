package com.example.group_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class addWingate extends AppCompatActivity {

    EditText et_studentID,et_mp,et_pp,et_ap;
    Button btn_submit,btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wingate);

        et_studentID = findViewById(R.id.editText_studentID);
        et_mp =  findViewById(R.id.editText_minPower);
        et_pp = findViewById(R.id.editText_peakPower);
        et_ap = findViewById(R.id.editText_avgPower);

        btn_submit = findViewById(R.id.button_submitWingate);
        btn_return = findViewById(R.id.button_wingate_return);

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

                // Check that the given StudentID exists (validation)
                // May not be the best method in practice, but works here
                boolean flag = false;
                Cursor cursor = dbh.displayAllAthleteData();
                while(cursor.moveToNext()){
                    if(studentID.equals(cursor.getString(1))) {
                        flag = true;
                        break;      // StudentID exists
                    }

                    if(cursor.isLast()){
                        // The studentID does not exist
                        Log.d("test_submit", "addWingate: The given studentID does not exist, aborting.");
                        AlertDialog.Builder ad = new AlertDialog.Builder(addWingate.this);
                        ad.setTitle("Bodpod Error");
                        ad.setMessage("ERROR: The given studentID does not exist!" + "\nStudentID: " + studentID);
                        ad.setCancelable(true);
                        ad.show();
                        // return;
                    }
                }

                if(flag) {
                    // Now add the values to the athlete table (8 values)
                    long row = dbh.addNewWingateTest(studentID, mp, pp, ap);

                    if (row < 0) {
                        // An error occurred
                        Log.d("test_submit", "Bodpod: New wingate test failed: " + studentID);
                        Toast.makeText(addWingate.this, "ERROR: Unable to add wingate test...", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("test_submit", "Bodpod: New wingate test is successfully added: " + studentID);
                        Toast.makeText(addWingate.this, "wingate test added for " + studentID + "!", Toast.LENGTH_LONG).show();
                    }
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