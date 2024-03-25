package com.example.group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CoachDashboardActivity extends AppCompatActivity {

    Button btn_viewRoster, btn_viewBodpod, btn_viewWingate, btn_viewBiodex;
    Button btn_addAthlete, btn_addBodpod, btn_addWingate, btn_addBiodex, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_dashboard);

        btn_viewRoster = findViewById(R.id.button_view_roster);
        btn_addAthlete = findViewById(R.id.button_add_athlete);
        btn_logout = findViewById(R.id.button_coach_logout);

        btn_viewRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoachViewRosterActivity.class);
                startActivity(intent);
            }
        });



        btn_addAthlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddAthleteActivity.class);
                startActivity(intent);
            }
        });





        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}