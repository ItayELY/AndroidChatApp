package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button button1, button2;
        final RelativeLayout relativeLayout;
        final ConstraintLayout constraintLayout;
        //set button 1 with its id
        button1 = findViewById(R.id.btVar1);

        // set button 2 with its id
        button2 = findViewById(R.id.btVar2);

        // set relative layout with its id
        constraintLayout = findViewById(R.id.rlVar1);

        // onClick function for button 1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the color to relative layout
                //constraintLayout.setBackgroundResource(R.color.cool);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // onClick function for button 2
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // set the color to relative layout
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }
}
