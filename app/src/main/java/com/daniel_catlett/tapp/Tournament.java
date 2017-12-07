package com.daniel_catlett.tapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by daniel on 11/28/2017.
 */

public class Tournament
{
    private String tournamentURL;

    String tournamentName;
    int tournamentID;
    String eventName;
    ArrayList<Integer> eventIDs = new ArrayList<Integer>();

    public Tournament(String inputName)
    {
        createTournamentUrl(inputName);
        new RetrieveFeedTask(tournamentURL).execute();
    }

    private void createTournamentUrl(String inputName)
    {
        tournamentURL = "https://api.smash.gg/tournament/";
        inputName = inputName.toLowerCase();
        inputName = inputName.replaceAll(" ", "-");
        tournamentURL = tournamentURL.concat(inputName);
        tournamentURL = tournamentURL.concat("?expand[0]=event");
    }

    public String getTournamentName()
    {
        return tournamentName;
    }

    public int getTournamentID()
    {
        return tournamentID;
    }

    public int getNumEvents()
    {
        return eventIDs.size();
    }

    public int getEventID(int index)
    {
        return eventIDs.get(index);
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String>
    {

        private Exception exception;
        private String currentURL;

        //constructor. Passes the url that the JSON will be grabbed from
        public RetrieveFeedTask(String theCurrentURL)
        {
            currentURL = theCurrentURL;
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
                JSONObject tourney = new JSONObject(response); //Object obtained

                //save name
                JSONObject entities = tourney.getJSONObject("entities");
                JSONObject tournament = entities.getJSONObject("tournament");
                tournamentName = tournament.getString("name");

                //save tournament id
                tournamentID = Integer.parseInt(tournament.getString("id"));

                //save event ids
                JSONArray events = entities.getJSONArray("event");
                for (int i = 0; i < events.length(); i++)
                {
                    JSONObject event = events.getJSONObject(i);
                    eventIDs.add(event.getInt("id"));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
