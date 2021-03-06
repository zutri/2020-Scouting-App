package com.example.a2020scoutingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ToggleButton;
import android.widget.Button;

import java.util.Scanner;

public class RecordAuto extends AppCompatActivity {
    EditText teamInput;
    ToggleButton blueRed;
    Button toTeleOp;
    NumberPicker cellsCollected;
    CheckBox preloadNum;
    NumberPicker rectScore;
    NumberPicker circScore;
    NumberPicker hexScore;
    CheckBox crossAuto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //don't mess with this
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //find IDs here
        teamInput = findViewById(R.id.teamInput);
        blueRed = findViewById(R.id.toggleButton);
        preloadNum = findViewById(R.id.hasPreload);
        crossAuto = findViewById(R.id.crossCheck);
        rectScore = findViewById(R.id.rectScore);
        circScore = findViewById(R.id.circScore);
        hexScore = findViewById(R.id.hexScore);
        toTeleOp = findViewById(R.id.teleButton);
        cellsCollected = findViewById(R.id.cellsCollected);
        String s= getSharedPreferences("Saved Data",MODE_PRIVATE).getString("AutoData","");
        if(!s.equals("")){
            Scanner sc= new Scanner(s);
            sc.useDelimiter(this.getString(R.string.delimeter));
            //  autoText+=teamInput.getText().toString()+delimeter+blueRed.getText()+delimeter+cellsCollected.getValue()+delimeter+preloadNum.isChecked()
            //                        +delimeter+ rectScore.getValue()+delimeter+circScore.getValue()+delimeter+hexScore.getValue()+delimeter+crossAuto.isChecked();

            teamInput.setText(sc.next());
            String r=sc.next();
            if(r.equals("red")){

                blueRed.setChecked(false);
            }else{ blueRed.setChecked(true);}
            cellsCollected.setValue(sc.nextInt());
            preloadNum.setChecked(sc.nextBoolean());
            rectScore.setValue(sc.nextInt());
            circScore.setValue(sc.nextInt());
            hexScore.setValue(sc.nextInt());
            crossAuto.setChecked(sc.nextBoolean());

        }
        //array of scores
        NumberPicker [] scores = {rectScore,circScore,hexScore};
        for (int i = 0; i < scores.length; i++) {
            scores[i].setWrapSelectorWheel(false);
            scores[i].setMaxValue(50);
            scores[i].setMinValue(0);
        }
        //preloadNum
        preloadNum.getResources().getColor(R.color.white);


        //cells collected
        cellsCollected.setMinValue(0);
        cellsCollected.setMaxValue(50);
        //crossAuto
        crossAuto.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        blueRed.setTextOff("Red");
        blueRed.setTextOn("Blue");
        blueRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    blueRed.setBackgroundColor(getResources().getColor(R.color.Blue));
                    blueRed.setTextColor(getResources().getColor(R.color.white));
                } else{
                    blueRed.setBackgroundColor(getResources().getColor(R.color.Red));
                }
            }
        });
        blueRed.setBackgroundColor(getResources().getColor(R.color.Red));
        blueRed.setText("Red");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        toTeleOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autoText="";
                String delimeter="#@;-;@#";
                autoText+=teamInput.getText().toString()+delimeter+blueRed.getText()+delimeter+cellsCollected.getValue()+delimeter+preloadNum.isChecked()
                        +delimeter+ rectScore.getValue()+delimeter+circScore.getValue()+delimeter+hexScore.getValue()+delimeter+crossAuto.isChecked();
                SharedPreferences sp= RecordAuto.this.getSharedPreferences("Saved Data",MODE_PRIVATE);
                SharedPreferences.Editor ed=sp.edit();
                ed.putString("AutoData",autoText);

                startActivity(new Intent(RecordAuto.this,RecordTeleOp.class));
            }
        });


    }
}
