package com.example.comp_4200project;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class barChartActivity extends AppCompatActivity {
    BarChart barChart;
    Button btn_barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_layout);
        barChart = findViewById(R.id.bar_chart);
        btn_barChart = findViewById(R.id.button_barChart2);
        DBHelper dbh = new DBHelper(getApplicationContext(), "bioinformatics", null, 1);
        btn_barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = dbh.displayAllAthleteData();
                List<BarEntry> entries = new ArrayList<>();
                List<String> labels = new ArrayList<>();
                int index = 0;

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        float height = Float.parseFloat(cursor.getString(5));
                        entries.add(new BarEntry(index, height));
                        labels.add(cursor.getString(1));
                        index++;
                    }
                } else {
                    Toast.makeText(barChartActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }

                // Create a dataset from the entries
                BarDataSet dataSet = new BarDataSet(entries, "Height");
                dataSet.setColor(Color.BLUE);

                // Create a data object from the dataset
                BarData data = new BarData(dataSet);
                barChart.setData(data);

                // Customize chart
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

                // Refresh chart
                barChart.invalidate();
            }
        });

    }
}
