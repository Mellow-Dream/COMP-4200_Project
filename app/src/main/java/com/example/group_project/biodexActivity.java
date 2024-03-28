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

public class biodexActivity extends AppCompatActivity {

    TextView tv_title, tv_biodex_view;
    Button btn_showGraph, btn_return;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodex);

        tv_title = findViewById(R.id.textView_biodex_title);
        tv_biodex_view = findViewById(R.id.textView_biodex_view);
        btn_return = findViewById(R.id.button_biodex_return);
        btn_showGraph = findViewById(R.id.button_generate_graph);
        barChart = findViewById(R.id.bar_chart);

        tv_title.setText("Men's Hockey Biodex");

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
        Cursor biodexCursor = dbh.displayAllBiodexData();

        // Check that data exists
        if(biodexCursor == null){
            // Something went wrong...
            Log.d("test_biodexView", "Biodex: Error when setting biodexCursor, aborting.");
            AlertDialog.Builder ad = new AlertDialog.Builder(biodexActivity.this);
            ad.setTitle("ERROR");
            ad.setMessage("Could not get biodex data.");
            ad.setCancelable(true);
            ad.show();
        }
        else if(biodexCursor.getCount() == 0) {
            // There is no roster data available
            Log.d("test_biodexView", "ViewBiodex: No biodex available.");
            tv_biodex_view.setText("No biodex available!");
        }
        else {
            // Show some of the roster data, don't need all of it
            Log.d("test_biodexView", "View biodex: Creating biodex output...");
            String data = "";
            while(biodexCursor.moveToNext()) {
                String temp = "StudentID: " + biodexCursor.getString(1)
                        + "\nLeft Quad Max: " + biodexCursor.getString(2)
                        + "\nRight Quad Max: " + biodexCursor.getString(3)
                        + "\nLeft Hamstring Max: " + biodexCursor.getString(4)
                        + "\nRight Hamstring Max " + biodexCursor.getString(5);
                data += temp + "\n\n";
            }

            tv_biodex_view.setText(data);
        }
    }

    public void displayGraph(){
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        Cursor cursor = dbh.displayAllBiodexData();
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
            Toast.makeText(biodexActivity.this, "No data available", Toast.LENGTH_SHORT).show();
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
}