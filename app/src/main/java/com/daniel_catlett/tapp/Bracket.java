package com.daniel_catlett.tapp;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

public class Bracket extends AppCompatActivity
{
    GridLayout bracketLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bracket);

        bracketLayout = (GridLayout) findViewById(R.id.bracketLayout);

        bracketLayout.setRowCount(10);
        bracketLayout.setColumnCount(10);

        for(int i = 0; i < 100; i++)
        {
            Button tile = new Button(this, null, android.R.style.Widget_DeviceDefault_Button_Borderless);
            tile.setBackground(getResources().getDrawable(R.drawable.brackethorizontal));
            tile.setText("MVG | FOX | Mew2King");
            bracketLayout.addView(tile);
        }
    }
}
