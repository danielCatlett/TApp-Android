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
 * Created by daniel on 11/30/2017.
 */

public class Phase
{
    private String phaseURL;
    private int phaseID;
    private String phaseName;
    private ArrayList<Integer> phaseGroupIDs = new ArrayList<Integer>();

    public Phase(int ID)
    {
        phaseID = ID;
        createURL();
    }

    private void createURL()
    {
        phaseURL = "https://api.smash.gg/phase/";
        phaseURL = phaseURL.concat(Integer.toString(phaseID));
        phaseURL = phaseURL.concat("?expand[0]=groups");
    }

    public String getPhaseName()
    {
        return phaseName;
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
                JSONObject phasie = new JSONObject(response); //Object obtained

                //save name of phase
                JSONObject entities = phasie.getJSONObject("entities");
                JSONObject phase = entities.getJSONObject("phase");
                phaseName = phase.getString("name");

                //save phaseGroup ids
                JSONArray groups = entities.getJSONArray("groups");
                for (int i = 0; i < groups.length(); i++)
                {
                    JSONObject group = groups.getJSONObject(i);
                    phaseGroupIDs.add(group.getInt("id"));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
