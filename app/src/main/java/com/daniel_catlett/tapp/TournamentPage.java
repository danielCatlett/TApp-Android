package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TournamentPage extends AppCompatActivity
{
    TextView title;
    Button scheduleButton;
    Button eventsButton;
    Button attendeesButton;
    Button removeButton;

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
        removeButton = (Button)findViewById(R.id.removeButton);
        removeButton.setTypeface(kelson);

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
        removeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(TournamentPage.this);
                builder.setTitle("Remove Tournament");
                builder.setMessage("Are you sure you want to remove this tournament from your saved tournaments?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        updatePreferences();
                        Toast.makeText(TournamentPage.this, title.getText() + " has been removed from your saved tournaments.", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void updatePreferences()
    {
        SharedPreferences settings = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        //decrease number of saved tournaments by one
        int numTournaments = settings.getInt("numTournaments", -1);
        editor.putInt("numTournaments", numTournaments - 1);

        //remove tournament name from preferences
        int tournamentNum = this.getIntent().getExtras().getInt("tournamentPosition") + 1;
        //cycle everything up one spot on the list
        for(int i = tournamentNum; i < numTournaments; i++)
        {
            String key = constructKey(i);
            String key2 = constructKey(i + 1);
            editor.putString(key, settings.getString(key2, ""));
        }
        //remove final value
        editor.remove(constructKey(numTournaments));
        editor.apply();

        //return to myTournaments page, with it hopefully refreshed
        startActivity(new Intent(TournamentPage .this, com.daniel_catlett.tapp.MyTournaments.class));
    }

    //returns the key used to access the appropriate tournament in preferences
    private String constructKey(int num)
    {
        String key = "tournament";
        key = key.concat(Integer.toString(num));
        return key;
    }
}
