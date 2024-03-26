package com.example.comp_4200project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class PlayerViewActivity extends AppCompatActivity {

    TextView tv_title, tv_result;
    Button btn_return;
    String studentID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_view);

        tv_title = findViewById(R.id.textView_player_view);
        tv_result = findViewById(R.id.textView_player_view_result);
        btn_return = findViewById(R.id.button_player_view_return);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayerDashboardActivity.class);
                intent.putExtra("studentID", studentID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // First, get the student's ID (Should exist)
        Intent i = getIntent();
        studentID = i.getStringExtra("_id");
        tv_title.setText("Testing Graphs for: " + studentID);

        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);

        // Get the player's testing data
        Log.d("test_playerView", "PlayerView: Creating the graphs...");
        Cursor bodpodCursor, wingateCursor, biodexCursor;
        bodpodCursor = dbh.displayAllBodpodData();
        wingateCursor = dbh.displayAllWingateData();
        biodexCursor = dbh.displayAllBiodexData();

        // Check for valid data
        if(bodpodCursor == null || wingateCursor == null || biodexCursor == null){
            // Something has gone wrong...
            Log.d("test_playerView", "PlayerView: Error when init Cursors, aborting.");
            AlertDialog.Builder ad = new AlertDialog.Builder(PlayerViewActivity.this);
            ad.setTitle("CURSOR ERROR");
            ad.setMessage("ERROR: Cursors were not initialized correctly, aborting.");
            ad.setCancelable(true);
            ad.show();
            return;
        }

        // See which graphs cannot be generated
        String err = "";
        String data = "";
        if(bodpodCursor.getCount() == 0 ){
            Log.d("test_playerView", "PlayerView: Bodpod does not have data for " + studentID);
            err += "No data from Bodpod testing!\n";
        } else {
            while(bodpodCursor.moveToNext()) {
                // Bodpod (studentID, TEE, REE, BodyFat, FatFree)
                // String format may be weird with multiple test results...
                String temp = "StudentID: " + bodpodCursor.getString(0)
                        + "\nTEE: " + bodpodCursor.getString(1) + ", REE: " + bodpodCursor.getString(2)
                        + "\nBodyFat: " + bodpodCursor.getString(3) + ", FatFree: " + bodpodCursor.getString(4);
                data += temp + "\n\n";
            }
        }

        if(wingateCursor.getCount() == 0 ){
            Log.d("test_playerView", "PlayerView: Wingate does not have data for " + studentID);
            err += "No data from Wingate testing!\n";
        } else {
            while(wingateCursor.moveToNext()) {
                // Wingate   (studentID, MinPower, PeakPower, AvgPower)
                // String format may be weird with multiple test results...
                String temp = "StudentID: " + wingateCursor.getString(0)
                        + "\nMinPower: " + wingateCursor.getString(1)
                        + "\nPeakPower: " + wingateCursor.getString(2)
                        + "\nAvgPower: " + wingateCursor.getString(3);
                data += temp + "\n\n";
            }
        }

        if(biodexCursor.getCount() == 0 ){
            Log.d("test_playerView", "PlayerView: Biodex does not have data for " + studentID);
            err += "No data from Biodex testing!\n";
        } else {
            while(biodexCursor.moveToNext()) {
                // Biodex    (studentID, LeftQuadMax, RightQuadMax, LeftHamMax, RightHamMax)
                // String format may be weird with multiple test results...
                String temp = "StudentID: " + biodexCursor.getString(0)
                        + "\nTEE: " + biodexCursor.getString(1)
                        + "\nREE: " + biodexCursor.getString(2)
                        + "\nBodyFat: " + biodexCursor.getString(3)
                        + "\nFatFree: " + biodexCursor.getString(4);
                data += temp + "\n\n";
            }
        }

        if(!err.isEmpty()) {
            // Display an error dialog
            AlertDialog.Builder ad = new AlertDialog.Builder(PlayerViewActivity.this);
            ad.setTitle("Table Data");
            ad.setMessage(err);
            ad.setCancelable(true);
            ad.show();
        }

        Log.d("test_playerView", "PlayerView: Displaying the graphs...");

        tv_result.setText(data);
        tv_result.setMovementMethod(new ScrollingMovementMethod());
    }
}