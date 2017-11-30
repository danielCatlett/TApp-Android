package com.daniel_catlett.tapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    TextView textView;
    Button button;
    Tournament tournament0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tournament0 = new Tournament("https://api.smash.gg/tournament/the-big-house-7");

        textView = (TextView) findViewById(R.id.textbox);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                textView.setText(tournament0.getTournamentName());
            }
        });
    }
}
