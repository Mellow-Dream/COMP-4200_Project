package com.example.comp_4200project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class biodexActivity extends AppCompatActivity {

    TextView tv_title, tv_biodex_view;
    Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biodex_layout);

        tv_title = findViewById(R.id.textView_biodex_title);
        tv_biodex_view = findViewById(R.id.textView_biodex_view);
        btn_return = findViewById(R.id.button_biodex_return);

        tv_title.setText("Men's Hockey Biodex");

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
}
