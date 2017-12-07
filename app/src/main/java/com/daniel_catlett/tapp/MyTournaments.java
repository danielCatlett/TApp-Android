package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyTournaments extends AppCompatActivity
{
    TextView title;
    ListView myTournamentsList;
    ArrayList<String> tournamentNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tournaments);

        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewMyTournaments);
        title.setTypeface(kelson);
        myTournamentsList = (ListView)findViewById(R.id.myTournamentsList);

        tournamentNames.add("The Big House 7");
        tournamentNames.add("The Big House 6");
        tournamentNames.add("Genesis 5");
        tournamentNames.add("RLCS Season 2");

        BasicAdapter adapter = new BasicAdapter(this, tournamentNames);
        myTournamentsList.setAdapter(adapter);

        final Context context = this;
        myTournamentsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(context, TournamentPage.class);
                intent.putExtra("title", tournamentNames.get(position));
                startActivity(intent);
            }
        });
    }
}
