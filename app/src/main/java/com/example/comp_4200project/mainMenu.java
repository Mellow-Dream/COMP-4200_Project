package com.example.comp_4200project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class mainMenu extends AppCompatActivity {

    Button btn_athleteRoster,btn_bodpodTestView,btn_biodexTestView,btn_wingateTestView;
    Button btn_addAthlete,btn_addBodpod,btn_addBiodex,btn_addWingate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        btn_athleteRoster = findViewById(R.id.button_athleteRoster);
        btn_bodpodTestView = findViewById(R.id.button_bodpodTestView);
        btn_biodexTestView = findViewById(R.id.button_biodexTestView);
        btn_wingateTestView = findViewById(R.id.button_wingateTestView);

        btn_addAthlete = findViewById(R.id.button_addAthlete);
        btn_addBodpod = findViewById(R.id.button_addBodpod);
        btn_addBiodex = findViewById(R.id.button_addBiodex);
        btn_addWingate = findViewById(R.id.button_addWingate);

        btn_athleteRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, athleteRoster.class));
            }
        });
        btn_bodpodTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, bodpodActivity.class));
            }
        });

        btn_biodexTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, biodexActivity.class));
            }
        });
        btn_wingateTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, wingateActivity.class));
            }
        });
        btn_addBiodex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, addBiodex.class));
            }
        });
        btn_addBodpod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, addBodpod.class));
            }
        });
        btn_addWingate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, addWingate.class));
            }
        });


    }
    }




