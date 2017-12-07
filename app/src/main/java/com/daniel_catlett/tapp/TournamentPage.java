package com.daniel_catlett.tapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TournamentPage extends AppCompatActivity
{
    TextView title;
    Button scheduleButton;
    Button eventsButton;
    Button attendeesButton;

    Tournament tournament;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_page);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewTournament);
        title.setTypeface(kelson);

        final String tournamentTitle = this.getIntent().getExtras().getString("title");
        title.setText(tournamentTitle);
        tournament = new Tournament(tournamentTitle);

        scheduleButton = (Button)findViewById(R.id.scheduleButton);
        scheduleButton.setTypeface(kelson);
        eventsButton = (Button)findViewById(R.id.eventsButton);
        eventsButton.setTypeface(kelson);
        attendeesButton = (Button)findViewById(R.id.attendeesButton);
        attendeesButton.setTypeface(kelson);

        scheduleButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TournamentPage.this, com.daniel_catlett.tapp.Schedule.class);
                intent.putExtra("tournamentTitle", tournamentTitle);
                startActivity(intent);
            }
        });
        eventsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TournamentPage.this, com.daniel_catlett.tapp.Events.class);
                intent.putExtra("tournamentTitle", tournamentTitle);
                startActivity(intent);
            }
        });
        attendeesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TournamentPage.this, AttendeesEventsPage.class);
                intent.putExtra("tournamentTitle", tournamentTitle);
                startActivity(intent);
            }
        });
    }
}
