package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TournamentPage extends AppCompatActivity
{
    TextView title;
    TextView date;
    Button scheduleButton;
    Button eventsButton;
    Button attendeesButton;
    Button removeButton;

    String tournamentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_page);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewTournament);
        title.setTypeface(kelson);
        date = (TextView)findViewById(R.id.textDate);
        date.setTypeface(kelson);

        tournamentTitle = this.getIntent().getExtras().getString("title");
        final String tournamentSlug = this.getIntent().getExtras().getString("slug");
        title.setText(tournamentTitle);

        //set date
        final int tournamentPosition = this.getIntent().getExtras().getInt("tournamentPosition");
        SharedPreferences settings = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String keyDate = constructKey(tournamentPosition + 1, "date");
        date.setText(settings.getString(keyDate, ""));

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
                intent.putExtra("tournamentNum", tournamentPosition + 1);
                startActivity(intent);
            }
        });
        eventsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TournamentPage.this, com.daniel_catlett.tapp.Events.class);
                intent.putExtra("tournamentSlug", tournamentSlug);
                startActivity(intent);
            }
        });
        attendeesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TournamentPage.this, AttendeesEventsPage.class);
                intent.putExtra("tournamentSlug", tournamentSlug);
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
                        Toast.makeText(TournamentPage.this, title.getText() + " has been removed from your saved tournaments.", Toast.LENGTH_SHORT).show();
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

        int tournamentNum = this.getIntent().getExtras().getInt("tournamentPosition") + 1;

        //remove tournament name from preferences
        removeFromSaved(tournamentNum, numTournaments, "", editor, settings);
        //remove slug
        removeFromSaved(tournamentNum, numTournaments, "slug", editor, settings);
        //remove date
        removeFromSaved(tournamentNum, numTournaments, "date", editor, settings);
        //remove schedule
        removeFromSaved(tournamentNum, numTournaments, "schedule", editor, settings);
        //remove scheduleURL
        removeFromSaved(tournamentNum, numTournaments, "schedule", editor, settings);
        editor.apply();

        //return to myTournaments page, with it hopefully refreshed
        startActivity(new Intent(TournamentPage .this, com.daniel_catlett.tapp.MyTournaments.class));
    }

    private void removeFromSaved(int tournamentNum, int numTournaments, String extension, SharedPreferences.Editor editor, SharedPreferences settings)
    {
        //cycle everything up one
        for(int i = tournamentNum; i < numTournaments; i++)
        {
            String key = constructKey(tournamentNum, extension);
            String key2 = constructKey(tournamentNum + 1, extension);
            editor.putString(key, settings.getString(key2, ""));
        }
        //remove final value
        editor.remove(constructKey(numTournaments, extension));
    }

    //returns the key used to access the appropriate tournament in preferences
    private String constructKey(int num, String extension)
    {
        String key = "tournament";
        key = key.concat(Integer.toString(num));
        key = key.concat(extension);
        return key;
    }
}
