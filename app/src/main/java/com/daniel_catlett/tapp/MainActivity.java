package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView title;
    Button myTournamentsButton;
    Button addTournamentButton;
    Button followedButton;
    Button settingsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find stuff
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewHome);
        myTournamentsButton = (Button)findViewById(R.id.myTournamentsButton);
        addTournamentButton = (Button)findViewById(R.id.addTournamentButton);
        addTournamentButton = (Button)findViewById(R.id.addTournamentButton);
        followedButton = (Button)findViewById(R.id.followedButton);
        settingsButton = (Button)findViewById(R.id.settingsButton);

        //set font for everything
        title.setTypeface(kelson);
        myTournamentsButton.setTypeface(kelson);
        addTournamentButton.setTypeface(kelson);
        followedButton.setTypeface(kelson);
        settingsButton.setTypeface(kelson);

        //listeners
        myTournamentsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, com.daniel_catlett.tapp.MyTournaments.class));
            }
        });
        addTournamentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, com.daniel_catlett.tapp.RecommendedTournaments.class));
            }
        });
        followedButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, com.daniel_catlett.tapp.FollowedPlayers.class));
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, com.daniel_catlett.tapp.Settings.class));
            }
        });
    }
}
