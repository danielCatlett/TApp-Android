package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    ArrayList<String> tournamentSlugs = new ArrayList<String>();
    SharedPreferences settings;
    public static final String MyPREFERENCES = "MyPrefs";
    TextView msg;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tournaments);

        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewMyTournaments);
        title.setTypeface(kelson);
        myTournamentsList = (ListView)findViewById(R.id.myTournamentsList);

        msg = (TextView)findViewById(R.id.textMsg);

        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int numTournaments = settings.getInt("numTournaments", -1);
        if(numTournaments != -1)
        {
            for(int i = 1; i <= numTournaments; i++)
            {
                //construct key for tournament name
                String key = "tournament";
                key = key.concat(Integer.toString(i));
                tournamentNames.add(settings.getString(key, ""));
                //construct key for tournament slug
                key = key.concat("slug");
                tournamentSlugs.add(settings.getString(key, ""));
            }
        }

        if(tournamentNames.size() == 0)
            msg.setVisibility(View.VISIBLE);

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
                intent.putExtra("slug", tournamentSlugs.get(position));
                intent.putExtra("tournamentPosition", position);
                startActivity(intent);
            }
        });
    }
}
