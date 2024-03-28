package com.example.group_project;

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

public class addBiodex extends AppCompatActivity {

    EditText et_studentID,et_LQM,et_RQM,et_LHM,et_RHM;
    Button btn_submit, btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_biodex);

        et_studentID = findViewById(R.id.editText_studentID);
        et_LQM = findViewById(R.id.editText_L_quad_max);
        et_RQM = findViewById(R.id.editText_R_quad_max);
        et_LHM = findViewById(R.id.editText_L_ham_max);
        et_RHM = findViewById(R.id.editText_R_ham_max);

        btn_submit = findViewById(R.id.button_submitBio);
        btn_return = findViewById(R.id.button_biodex_return);

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
                        Log.d("test_submit", "addBiodex: The given studentID does not exist, aborting.");
                        AlertDialog.Builder ad = new AlertDialog.Builder(addBiodex.this);
                        ad.setTitle("Bodpod Error");
                        ad.setMessage("ERROR: The given studentID does not exist!" + "\nStudentID: " + studentID);
                        ad.setCancelable(true);
                        ad.show();
                        // return;
                    }
                }

                if(flag) {
                    // Now add the values to the athlete table (8 values)
                    long row = dbh.addNewBiodexTest(studentID, lqm, rqm, lhm, rhm);

                    if (row < 0) {
                        // An error occurred
                        Log.d("test_submit", "Bodpod: New biodex test failed: " + studentID);
                        Toast.makeText(addBiodex.this, "ERROR: Unable to add biodex test...", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("test_submit", "Bodpod: New biodex test is successfully added: " + studentID);
                        Toast.makeText(addBiodex.this, "biodex test added for " + studentID + "!", Toast.LENGTH_LONG).show();
                    }

                    flag = false;   // Reset flag
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