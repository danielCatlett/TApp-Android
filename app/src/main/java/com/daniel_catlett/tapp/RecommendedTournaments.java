package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecommendedTournaments extends AppCompatActivity
{
    ListView recList;
    TextView title;
    ArrayList<String> tournamentNames = new ArrayList<String>();
    ArrayList<String> tournamentSlugs = new ArrayList<String>();
    ArrayList<String> tournamentsAlreadySaved = new ArrayList<String>();

    SharedPreferences settings;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_tournaments);

        recList = (ListView)findViewById(R.id.recList);
        title = (TextView)findViewById(R.id.textViewRec);
        title.setText("Add Tournaments");
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title.setTypeface(kelson);

        addTournamentNames();
        addTournamentSlugs();

        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //set up for preventing user from adding a tournament multiple times
        int numTournaments = settings.getInt("numTournaments", -1);
        for(int i = 1; i <= numTournaments; i++)
        {
            String key = "tournament";
            key = key.concat(Integer.toString(i));
            tournamentsAlreadySaved.add(settings.getString(key, ""));
        }

        BasicAdapter adapter = new BasicAdapter(this, tournamentNames);
        recList.setAdapter(adapter);

        recList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                //check to see if the tournament name is in the tournamentsAlreadySaved
                boolean notRepeat = true;
                for(int i = 0; i < tournamentsAlreadySaved.size(); i++)
                {
                    if(tournamentsAlreadySaved.get(i).equals(tournamentNames.get(position)))
                    {
                        notRepeat = false;
                        break;
                    }
                }

                if(notRepeat)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecommendedTournaments.this);
                    builder.setTitle("Add Tournament");
                    builder.setMessage("Do you want to add this tournament to your saved tournaments?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            updatePreferences(position);
                            Toast.makeText(RecommendedTournaments.this, tournamentNames.get(position) + " has been added to your saved tournaments.", Toast.LENGTH_LONG).show();
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
                else
                    Toast.makeText(RecommendedTournaments.this, tournamentNames.get(position) + " is already in your saved tournaments!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updatePreferences(int position)
    {
        SharedPreferences.Editor editor = settings.edit();
        //editor.clear();

        //keep track of the number of tournaments saved, so that we know how many to pull later
        int currentNum = settings.getInt("numTournaments", -1);
        //if the value already exists
        if(currentNum > 0)
        {
            currentNum++;
            editor.putInt("numTournaments", currentNum);
        }
        else
        {
            currentNum = 1;
            editor.putInt("numTournaments", 1);
        }

        //add the name of the tournament chosen to the preferences
        //start by constructing the key using currentNum
        String key = "tournament";
        key = key.concat(Integer.toString(currentNum));
        editor.putString(key, tournamentNames.get(position));

        //add the slug of the tournament chosen
        key = key.concat("slug");
        editor.putString(key, tournamentSlugs.get(position));

        editor.apply();
    }

    private void addTournamentNames()
    {
        tournamentNames.add("Save Net Neutrality!");
        tournamentNames.add("Injustice 2: Winter Clash");
        tournamentNames.add("Smash 4 Boot Camp");
        tournamentNames.add("Marvel vs. Capcom: Infinite – Battle for the Stones Finals");
        tournamentNames.add("Capcom Cup SFV Last Chance Qualifier");
        tournamentNames.add("Capcom Cup 2017");
        tournamentNames.add("War of the Gods: S2 Week #4");
        tournamentNames.add("Windjammers Flying Power League");
        tournamentNames.add("NEC 18");
        tournamentNames.add("Kumite in Tenneessee 2018");
        tournamentNames.add("Poi Poundaz");
        tournamentNames.add("StrongStyle European Qualifier");
        tournamentNames.add("Genesis 5");
        tournamentNames.add("Frosty Faustings X");
        tournamentNames.add("EVO Japan 2018");
    }

    private void addTournamentSlugs()
    {
        tournamentSlugs.add("save-net-neutrality-1");
        tournamentSlugs.add("injustice-2-winter-clash-1");
        tournamentSlugs.add("smash-4-boot-camp");
        tournamentSlugs.add("marvel-vs-capcom-infinite-battle-for-the-stones-finals");
        tournamentSlugs.add("capcom-cup-sfv-last-chance-qualifier");
        tournamentSlugs.add("capcom-cup-2017");
        tournamentSlugs.add("war-of-the-gods-s2-week-4");
        tournamentSlugs.add("windjammers-flying-power-league");
        tournamentSlugs.add("nec-18-1");
        tournamentSlugs.add("kumite-in-tennessee-2018");
        tournamentSlugs.add("poi-poundaz");
        tournamentSlugs.add("strongstyle-european-qualifier");
        tournamentSlugs.add("genesis-5");
        tournamentSlugs.add("frosty-faustings-x");
        tournamentSlugs.add("evojapan2018");
    }
}
