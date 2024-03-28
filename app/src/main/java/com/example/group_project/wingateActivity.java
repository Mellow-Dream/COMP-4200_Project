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

public class wingateActivity extends AppCompatActivity {

    TextView tv_title, tv_wingate_view;
    Button btn_showGraph, btn_return;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wingate);

        tv_title = findViewById(R.id.textView_wingate_title);
        tv_wingate_view = findViewById(R.id.textView_wingate_view);
        btn_return = findViewById(R.id.button_wingate_return);
        btn_showGraph = findViewById(R.id.button_generate_graph);
        barChart = findViewById(R.id.bar_chart);

        tv_title.setText("Men's Hockey Wingate");

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
        Cursor wingateCursor = dbh.displayAllWingateData();

        // Check that data exists
        if(wingateCursor == null){
            // Something went wrong...
            Log.d("test_wingateView", "Wingate: Error when setting wingateCursor, aborting.");
            AlertDialog.Builder ad = new AlertDialog.Builder(wingateActivity.this);
            ad.setTitle("ERROR");
            ad.setMessage("Could not get wingate data.");
            ad.setCancelable(true);
            ad.show();
        }
        else if(wingateCursor.getCount() == 0) {
            // There is no roster data available
            Log.d("test_wingateiew", "ViewWingate: No wingate available.");
            tv_wingate_view.setText("No wingate available!");
        }
        else {
            // Show some of the roster data, don't need all of it
            Log.d("test_wingateView", "View Wingate: Creating wingate output...");
            String data = "";
            while(wingateCursor.moveToNext()) {
                String temp = "StudentID: " + wingateCursor.getString(1)
                        + "\nMinimum Power: " + wingateCursor.getString(2)
                        + "\nPeak Power: " + wingateCursor.getString(3)
                        + "\nAverage Power:  " + wingateCursor.getString(4);
                data += temp + "\n\n";
            }

            tv_wingate_view.setText(data);
        }
    }

    public void displayGraph(){
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        Cursor cursor = dbh.displayAllWingateData();
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
            Toast.makeText(wingateActivity.this, "No data available", Toast.LENGTH_SHORT).show();
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
}