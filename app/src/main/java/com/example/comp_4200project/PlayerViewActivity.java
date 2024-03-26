package com.example.comp_4200project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class PlayerViewActivity extends AppCompatActivity {

    TextView tv_title, tv_result;
    BarChart barChart;
    Button btn_return,btn_AthletebarChart,btn_bodpodBarChart,btn_wingateBarChart,btn_biodexBarChart;
    String studentID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_view);

        tv_title = findViewById(R.id.textView_player_view);
        tv_result = findViewById(R.id.textView_player_view_result);
        btn_return = findViewById(R.id.button_player_view_return);
        btn_AthletebarChart =findViewById(R.id.button_player_displayAthleteBarChart);
        btn_bodpodBarChart = findViewById(R.id.button_player_displayBodpodBarChart);
        btn_wingateBarChart = findViewById(R.id.button_player_displayWingateBarChart);
        btn_biodexBarChart = findViewById(R.id.button_player_displayBiodexBarChart);

        barChart = findViewById(R.id.bar_chart);
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);



        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayerDashboardActivity.class);
                intent.putExtra("studentID", studentID);
                startActivity(intent);
            }
        });
        btn_AthletebarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAthleteGraph();
            }
        });
        btn_bodpodBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayBodpodGraph();
            }
        });
        btn_wingateBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayWingateGraph();
            }
        });
        btn_biodexBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               displayBiodexGraph();
            }
        });
    }
    public void displayAthleteGraph(){
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        Cursor cursor = dbh.displayAthleteTest(studentID);
        List<BarEntry> entriesHeight = new ArrayList<>();
        List<BarEntry> entriesWeight = new ArrayList<>();
        List<BarEntry> entriesJersey = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                float height = Float.parseFloat(cursor.getString(5));
                float weight = Float.parseFloat(cursor.getString(6));
                int jerseyNumber = cursor.getInt(8);
                entriesHeight.add(new BarEntry(index, height));
                entriesWeight.add(new BarEntry(index + 0.3f, weight));
                entriesJersey.add(new BarEntry(index + 0.6f, jerseyNumber));
                labels.add(cursor.getString(1));
                index++;
            }
        } else {
            Toast.makeText(PlayerViewActivity.this, "No data available", Toast.LENGTH_SHORT).show();
        }
        BarDataSet dataSetHeight = new BarDataSet(entriesHeight, "Height");
        dataSetHeight.setColor(Color.BLUE);
        BarDataSet dataSetWeight = new BarDataSet(entriesWeight, "Weight");
        dataSetWeight.setColor(Color.RED);
        BarDataSet dataSetJersey = new BarDataSet(entriesJersey, "Jersey Number");
        dataSetJersey.setColor(Color.GREEN);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetHeight);
        dataSets.add(dataSetWeight);
        dataSets.add(dataSetJersey);
        BarData data = new BarData(dataSets);
        data.setBarWidth(0.2f);
        data.groupBars(-0.5f, 0.1f, 0.1f);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(labels.size());
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
    }
    public void displayBodpodGraph(){
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        Cursor cursor = dbh.displayBodpodTest(studentID);
        List<BarEntry> entriesTEE = new ArrayList<>();
        List<BarEntry> entriesREE = new ArrayList<>();
        List<BarEntry> entriesBodyFat = new ArrayList<>();
        List<BarEntry> entriesFatFree = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int tee = cursor.getInt(2);
                int ree = cursor.getInt(3);
                float bodyFat = cursor.getFloat(4);
                float fatFree = cursor.getFloat(5);
                entriesTEE.add(new BarEntry(index, tee));
                entriesREE.add(new BarEntry(index + 0.2f, ree));
                entriesBodyFat.add(new BarEntry(index + 0.4f, bodyFat));
                entriesFatFree.add(new BarEntry(index + 0.6f, fatFree));
                labels.add(cursor.getString(1));
                index++;
            }
        } else {
            Toast.makeText(PlayerViewActivity.this, "No data available", Toast.LENGTH_SHORT).show();
        }
        BarDataSet dataSetTEE = new BarDataSet(entriesTEE, "TEE");
        dataSetTEE.setColor(Color.BLUE);
        BarDataSet dataSetREE = new BarDataSet(entriesREE, "REE");
        dataSetREE.setColor(Color.RED);
        BarDataSet dataSetBodyFat = new BarDataSet(entriesBodyFat, "Body Fat");
        dataSetBodyFat.setColor(Color.GREEN);
        BarDataSet dataSetFatFree = new BarDataSet(entriesFatFree, "Fat Free");
        dataSetFatFree.setColor(Color.YELLOW);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetTEE);
        dataSets.add(dataSetREE);
        dataSets.add(dataSetBodyFat);
        dataSets.add(dataSetFatFree);
        BarData data = new BarData(dataSets);
        data.setBarWidth(0.15f);
        data.groupBars(-0.5f, 0.1f, 0.1f);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(labels.size());
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
    }
    public void displayWingateGraph(){
            DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
            Cursor cursor = dbh.displayWingateTest(studentID);
            List<BarEntry> entriesMinPower = new ArrayList<>();
            List<BarEntry> entriesPeakPower = new ArrayList<>();
            List<BarEntry> entriesAvgPower = new ArrayList<>();
            List<String> labels = new ArrayList<>();
            int index = 0;
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    float minPower = cursor.getFloat(2);
                    float peakPower = cursor.getFloat(3);
                    float avgPower = cursor.getFloat(4);
                    entriesMinPower.add(new BarEntry(index, minPower));
                    entriesPeakPower.add(new BarEntry(index + 0.3f, peakPower));
                    entriesAvgPower.add(new BarEntry(index + 0.6f, avgPower));
                    labels.add(cursor.getString(1));
                    index++;
                }
            } else {
                Toast.makeText(PlayerViewActivity.this, "No data available", Toast.LENGTH_SHORT).show();
            }
            BarDataSet dataSetMinPower = new BarDataSet(entriesMinPower, "Min Power");
            dataSetMinPower.setColor(Color.BLUE);
            BarDataSet dataSetPeakPower = new BarDataSet(entriesPeakPower, "Peak Power");
            dataSetPeakPower.setColor(Color.RED);
            BarDataSet dataSetAvgPower = new BarDataSet(entriesAvgPower, "Avg Power");
            dataSetAvgPower.setColor(Color.GREEN);
            List<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSetMinPower);
            dataSets.add(dataSetPeakPower);
            dataSets.add(dataSetAvgPower);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.2f);
            data.groupBars(-0.5f, 0.1f, 0.1f);
            barChart.setData(data);
            barChart.getDescription().setEnabled(false);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setLabelCount(labels.size());
            YAxis yAxis = barChart.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            barChart.getAxisRight().setEnabled(false);
            barChart.invalidate();
        }

    public void displayBiodexGraph(){
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        Cursor cursor = dbh.displayBiodexTest(studentID);
        List<BarEntry> entriesLQuadMax = new ArrayList<>();
        List<BarEntry> entriesRQuadMax = new ArrayList<>();
        List<BarEntry> entriesLHamMax = new ArrayList<>();
        List<BarEntry> entriesRHamMax = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                float lQuadMax = cursor.getFloat(2);
                float rQuadMax = cursor.getFloat(3);
                float lHamMax = cursor.getFloat(4);
                float rHamMax = cursor.getFloat(5);
                entriesLQuadMax.add(new BarEntry(index, lQuadMax));
                entriesRQuadMax.add(new BarEntry(index + 0.3f, rQuadMax));
                entriesLHamMax.add(new BarEntry(index + 0.6f, lHamMax));
                entriesRHamMax.add(new BarEntry(index + 0.9f, rHamMax));
                labels.add(cursor.getString(1));
                index++;
            }
        } else {
            Toast.makeText(PlayerViewActivity.this, "No data available", Toast.LENGTH_SHORT).show();
        }
        BarDataSet dataSetLQuadMax = new BarDataSet(entriesLQuadMax, "L Quad Max");
        dataSetLQuadMax.setColor(Color.BLUE);
        BarDataSet dataSetRQuadMax = new BarDataSet(entriesRQuadMax, "R Quad Max");
        dataSetRQuadMax.setColor(Color.RED);
        BarDataSet dataSetLHamMax = new BarDataSet(entriesLHamMax, "L Ham Max");
        dataSetLHamMax.setColor(Color.GREEN);
        BarDataSet dataSetRHamMax = new BarDataSet(entriesRHamMax, "R Ham Max");
        dataSetRHamMax.setColor(Color.YELLOW);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetLQuadMax);
        dataSets.add(dataSetRQuadMax);
        dataSets.add(dataSetLHamMax);
        dataSets.add(dataSetRHamMax);
        BarData data = new BarData(dataSets);
        data.setBarWidth(0.2f);
        data.groupBars(-0.5f, 0.1f, 0.1f);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(labels.size());
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // First, get the student's ID (Should exist)
        Intent i = getIntent();
        studentID = i.getStringExtra("studentID");
        tv_title.setText("Testing Graphs for: " + studentID);

        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);

        // Get the player's testing data
        Log.d("test_playerView", "PlayerView: Creating the graphs...");
        Cursor bodpodCursor, wingateCursor, biodexCursor;
        bodpodCursor = dbh.displayBodpodTest(studentID);
        wingateCursor = dbh.displayWingateTest(studentID);
        biodexCursor = dbh.displayBiodexTest(studentID);

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
                String temp = "StudentID: " + bodpodCursor.getString(1)
                        + "\nTEE: " + bodpodCursor.getString(2) + ", REE: " + bodpodCursor.getString(2)
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
                String temp = "StudentID: " + wingateCursor.getString(1)
                        + "\nMinPower: " + wingateCursor.getString(2)
                        + "\nPeakPower: " + wingateCursor.getString(3)
                        + "\nAvgPower: " + wingateCursor.getString(4);
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
                String temp = "StudentID: " + biodexCursor.getString(1)
                        + "\nTEE: " + biodexCursor.getString(2)
                        + "\nREE: " + biodexCursor.getString(3)
                        + "\nBodyFat: " + biodexCursor.getString(4)
                        + "\nFatFree: " + biodexCursor.getString(5);
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