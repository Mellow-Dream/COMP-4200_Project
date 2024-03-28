package com.example.group_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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

public class bodpodActivity extends AppCompatActivity {

    TextView tv_title, tv_bodpod_view;
    Button btn_showGraph, btn_return;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodpod);

        tv_title = findViewById(R.id.textView_bodpod_title);
        tv_bodpod_view = findViewById(R.id.textView_bodpod_view);
        btn_return = findViewById(R.id.button_bodpod_return);
        btn_showGraph = findViewById(R.id.button_generate_graph);
        barChart = findViewById(R.id.bar_chart);

        tv_title.setText("Men's Hockey Bodpod");

        btn_showGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayGraph();
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

    @Override
    protected void onStart() {
        super.onStart();

        // Get the team roster and display info in the main textView
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        Cursor bodpodCursor = dbh.displayAllBodpodData();

        // Check that data exists
        if(bodpodCursor == null){
            // Something went wrong...
            Log.d("test_rosterView", "ViewRoster: Error when setting athleteCursor, aborting.");
            AlertDialog.Builder ad = new AlertDialog.Builder(bodpodActivity.this);
            ad.setTitle("ERROR");
            ad.setMessage("Could not get bodpod data.");
            ad.setCancelable(true);
            ad.show();
        }
        else if(bodpodCursor.getCount() == 0) {
            // There is no roster data available
            Log.d("test_bodpodView", "ViewBodpod: No bodpod available.");
            tv_bodpod_view.setText("No bodpod available!");
        }
        else {
            // Show some of the roster data, don't need all of it
            Log.d("test_rosterView", "ViewRoster: Creating roster output...");
            String data = "";
            while(bodpodCursor.moveToNext()) {
                String temp = "StudentID: " + bodpodCursor.getString(1)
                        + "\nTee: " + bodpodCursor.getString(2)
                        + "\nRee: " + bodpodCursor.getString(3)
                        + "\nBody Fat: " + bodpodCursor.getString(4)
                        + "\nFat Free " + bodpodCursor.getString(5);
                data += temp + "\n\n";
            }

            tv_bodpod_view.setText(data);
        }
    }

    public void displayGraph(){
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        Cursor cursor = dbh.displayAllBodpodData();
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
            Toast.makeText(bodpodActivity.this, "No data available", Toast.LENGTH_SHORT).show();
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
}