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

/**
 * Created by daniel on 12/1/2017.
 */

public class Set
{
    private String setURL;
    private int setID;
    private String roundText;

    private int entrant1ID;
    private String entrant1Tag;
    private int entrant1Score;

    private int entrant2ID;
    private String entrant2Tag;
    private int entrant2Score;

    private int winnerID;
    private int loserID;

    public Set(int ID)
    {
        setID = ID;
        createSetURL();
    }

    private void createSetURL()
    {
        setURL = "https://api.smash.gg/set/";
        setURL = setURL.concat(Integer.toString(setID));
    }

    public String getRoundText()
    {
        return roundText;
    }

    public int getEntrant1ID()
    {
        return entrant1ID;
    }

    public String getEntrant1Tag()
    {
        return entrant1Tag;
    }

    public int getEntrant1Score()
    {
        return entrant1Score;
    }

    public int getEntrant2ID()
    {
        return entrant2ID;
    }

    public String getEntrant2Tag()
    {
        return entrant2Tag;
    }

    public int getEntrant2Score()
    {
        return entrant2Score;
    }

    public int getWinnerID()
    {
        return winnerID;
    }

    public int getLoserID()
    {
        return loserID;
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
                JSONObject sety = new JSONObject(response); //Object obtained

                //save round text
                JSONObject entities = sety.getJSONObject("entities");
                JSONObject sets = entities.getJSONObject("sets");
                roundText = sets.getString("fullRoundText");

                //save entrant 1 data
                entrant1ID = sets.getInt("entrant1ID");
                entrant1Score = sets.getInt("entrant1Score");

                //save entrant 2 data
                entrant2ID = sets.getInt("entrant2ID");
                entrant2Score = sets.getInt("entrant2Score");

                //winners and losers
                winnerID = sets.getInt("winnerID");
                loserID = sets.getInt("loserID");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
