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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecommendedTournaments extends AppCompatActivity
{
    ListView recList;
    TextView title;
    ArrayList<String> tournamentNames = new ArrayList<String>();
    ArrayList<String> tournamentSlugs = new ArrayList<String>();
    ArrayList<String> tournamentDates = new ArrayList<String>();
    ArrayList<String> tournamentSchedules = new ArrayList<String>();
    ArrayList<String> tournamentScheduleURLs = new ArrayList<String>();
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

        new RetrieveFeedTask("https://danielcatlett.github.io/TAppServer/tappinfo.json", this).execute();

        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //set up for preventing user from adding a tournament multiple times
        int numTournaments = settings.getInt("numTournaments", -1);
        for(int i = 1; i <= numTournaments; i++)
        {
            String key = "tournament";
            key = key.concat(Integer.toString(i));
            tournamentsAlreadySaved.add(settings.getString(key, ""));
        }
    }

    private void updatePreferences(int position)
    {
        SharedPreferences.Editor editor = settings.edit();

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
        String keySlug = key.concat("slug");
        editor.putString(keySlug, tournamentSlugs.get(position));

        //add the date of the tournament chosen
        String keyDate = key.concat("date");
        editor.putString(keyDate, tournamentDates.get(position));

        //add the schedule of the tournament chosen
        String keySchedule = key.concat("schedule");
        editor.putString(keySchedule, tournamentSchedules.get(position));

        //add the scheduleURL of the tournament chosen
        String keyScheduleURL = key.concat("scheduleURL");
        editor.putString(keyScheduleURL, tournamentScheduleURLs.get(position));

        editor.apply();
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String>
    {

        private Exception exception;
        private String currentURL;
        private Context context;

        //constructor. Passes the url that the JSON will be grabbed from
        public RetrieveFeedTask(String theCurrentURL, Context theContext)
        {
            currentURL = theCurrentURL;
            context = theContext;
        }

        protected String doInBackground(Void... urls)
        {
            URL url = null;
            String results = null;
            boolean flag1 = false;

            try
            {
                url = new URL(currentURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try
                {
                    if (urlConnection.getInputStream() == null)
                    {
                        flag1 = true;
                    }
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    flag1 = true;
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally
                {
                    urlConnection.disconnect();
                }
            }
            catch (Exception e)
            {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response)
        {
            if (response == null)
                response = "THERE WAS AN ERROR";
            try
            {
                JSONObject feed = new JSONObject(response); //Object obtained

                //get events and details from server
                JSONArray tournaments = feed.getJSONArray("tournaments");
                for(int i = 0; i < tournaments.length(); i++)
                {
                    JSONObject tourney = tournaments.getJSONObject(i);
                    tournamentNames.add(tourney.getString("name"));
                    tournamentSlugs.add(tourney.getString("slug"));
                    tournamentDates.add(tourney.getString("date"));
                    tournamentSchedules.add(tourney.getString("schedule"));
                    tournamentScheduleURLs.add(tourney.getString("scheduleURL"));
                }
                //fill the listview
                BasicAdapter adapter = new BasicAdapter(context, tournamentNames);
                recList.setAdapter(adapter);

                //set listener
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
                                    tournamentsAlreadySaved.add(tournamentNames.get(position));
                                    Toast toast = Toast.makeText(RecommendedTournaments.this, tournamentNames.get(position) + " has been added to your saved tournaments.", Toast.LENGTH_SHORT);
                                    toast.show();
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
                            Toast.makeText(RecommendedTournaments.this, tournamentNames.get(position) + " is already in your saved tournaments!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
