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
        btn_viewBodpod = findViewById(R.id.button_view_team_bodpod);
        btn_viewBiodex = findViewById(R.id.button_view_team_biodex);
        btn_viewWingate = findViewById(R.id.button_view_team_wingate);
        btn_addAthlete = findViewById(R.id.button_add_athlete);
        btn_addAthlete = findViewById(R.id.button_add_athlete);
        btn_addBodpod = findViewById(R.id.button_add_bodpod);
        btn_addBiodex = findViewById(R.id.button_add_biodex);
        btn_addWingate = findViewById(R.id.button_add_wingate);
        btn_logout = findViewById(R.id.button_coach_logout);

        btn_viewRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoachViewRosterActivity.class);
                startActivity(intent);
            }
        });

        btn_viewBodpod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), bodpodActivity.class);
                startActivity(intent);
            }
        });

        btn_viewWingate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), wingateActivity.class);
                startActivity(intent);
            }
        });

        btn_viewBiodex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), biodexActivity.class);
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

        btn_addBodpod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addBodpod.class);
                startActivity(intent);
            }
        });


        btn_addWingate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addWingate.class);
                startActivity(intent);
            }
        });

        btn_addBiodex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addBiodex.class);
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