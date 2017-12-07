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

    SharedPreferences sharedPreferences;
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

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        BasicAdapter adapter = new BasicAdapter(this, tournamentNames);
        recList.setAdapter(adapter);

        recList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecommendedTournaments.this);
                builder.setTitle("Add Tournament");
                builder.setMessage("Do you want to add this tournament to your saved tournaments?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Toast.makeText(RecommendedTournaments.this, tournamentNames.get(position) + " added to your saved tournaments.", Toast.LENGTH_LONG).show();
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
