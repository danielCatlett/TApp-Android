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

public class PhaseGroups extends AppCompatActivity
{
    TextView title;
    ListView phaseGroupsList;
    ArrayList<Integer> phaseGroupIDs = new ArrayList<Integer>();
    ArrayList<String> phaseGroupNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phase_groups);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewPhaseGroups);
        title.setTypeface(kelson);

        //get info from intent
        String phaseName = this.getIntent().getExtras().getString("phaseName");
        String phaseID = Integer.toString(this.getIntent().getExtras().getInt("phaseID"));
        //set page title
        title.setText(phaseName);

        phaseGroupsList = (ListView)findViewById(R.id.phaseGroupsList);
        String url = createPhaseURL(phaseID);
        new RetrieveFeedTask(url, this).execute();
    }

    private String createPhaseURL(String phaseID)
    {
        String eventURL = "https://api.smash.gg/phase/";
        eventURL = eventURL.concat(phaseID);
        eventURL = eventURL.concat("?expand[0]=groups");
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
                JSONArray groups = entities.getJSONArray("groups");
                for (int i = 0; i < groups.length(); i++)
                {
                    JSONObject phase = groups.getJSONObject(i);
                    phaseGroupIDs.add(phase.getInt("id"));
                    phaseGroupNames.add(phase.getString("displayIdentifier"));
                }

                //fill the listview
                BasicAdapter adapter = new BasicAdapter(context, phaseGroupNames);
                phaseGroupsList.setAdapter(adapter);

                //set listener
                phaseGroupsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Intent intent = new Intent(context, Bracket.class);
                        startActivity(intent);
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
