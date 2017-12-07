package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Phases extends AppCompatActivity
{
    TextView title;
    ListView phasesList;
    ArrayList<Integer> phaseIDs = new ArrayList<Integer>();
    ArrayList<String> phaseNames = new ArrayList<String>();
    ArrayList<Integer> numGroups = new ArrayList<Integer>(); //if there is only one group, it will have to go straight to the bracket screen

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phases);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView) findViewById(R.id.textViewPhases);
        title.setTypeface(kelson);

        //get info from intent
        String eventName = this.getIntent().getExtras().getString("eventName");
        String eventID = Integer.toString(this.getIntent().getExtras().getInt("eventID"));
        //set page title
        title.setText(eventName);

        phasesList = (ListView) findViewById(R.id.phasesList);
        String url = createEventURL(eventID);
        new RetrieveFeedTask(url,this).execute();
    }

    private String createEventURL(String eventID)
    {
        String eventURL = "https://api.smash.gg/event/";
        eventURL = eventURL.concat(eventID);
        eventURL = eventURL.concat("?expand[0]=phase");
        return eventURL;
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String>
    {

        private Exception exception;
        private String currentURL;
        Context context;

        //constructor. Passes the url that the JSON will be grabbed from
        public RetrieveFeedTask(String theCurrentURL, Context contextIsntAlwaysEverythingButItIsHere)
        {
            currentURL = theCurrentURL;
            context = contextIsntAlwaysEverythingButItIsHere;
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
                JSONObject eventy = new JSONObject(response); //Object obtained

                //save phase ids and names. also numgroups in phase
                JSONObject entities = eventy.getJSONObject("entities");
                JSONArray phases = entities.getJSONArray("phase");
                for (int i = 0; i < phases.length(); i++)
                {
                    JSONObject phase = phases.getJSONObject(i);
                    phaseIDs.add(phase.getInt("id"));
                    phaseNames.add(phase.getString("name"));
                    numGroups.add(phase.getInt("groupCount"));
                }

                //fill the listview
                BasicAdapter adapter = new BasicAdapter(context, phaseNames);
                phasesList.setAdapter(adapter);

                //set listener
                phasesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if(numGroups.get(position) != 1)
                        {
                            Intent intent = new Intent(context, PhaseGroups.class);
                            intent.putExtra("phaseName", phaseNames.get(position));
                            intent.putExtra("phaseID", phaseIDs.get(position));
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(context, Bracket.class);
                            startActivity(intent);
                        }
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
